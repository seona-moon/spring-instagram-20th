package com.ceos20_instagram.domain.chat.service;

import com.ceos20_instagram.domain.chat.ChatMessageRepository;
import com.ceos20_instagram.domain.chat.ChatRoomRepository;
import com.ceos20_instagram.domain.chat.entity.ChatMessage;
import com.ceos20_instagram.domain.chat.entity.ChatRoom;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;

    // 메시지 전송
    public ChatMessage sendMessage(Long chatRoomId, Long senderId, String content) throws Throwable {
        // 채팅방과 보낸 사람 조회
        ChatRoom chatRoom = (ChatRoom) chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
        Member sender = memberService.findMemberById(senderId);

        // 메시지 생성 및 저장
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .author(sender)
                .content(content)
                .build();
        chatMessageRepository.save(chatMessage);

        // 채팅방의 마지막 메시지 업데이트
        chatRoom.updateLastMessage(chatMessage);
        chatRoomRepository.save(chatRoom);

        return chatMessage;
    }

    // 특정 채팅방의 모든 메시지 조회
    public List<ChatMessage> getMessagesForChatRoom(Long chatRoomId) throws Throwable {
        ChatRoom chatRoom = (ChatRoom) chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        return chatMessageRepository.findMessagesByChatRoomOrderByCreatedAtAsc(chatRoom);
    }

    // 메시지 삭제
    public void deleteMessage(Long messageId, Long memberId) throws Throwable {
        ChatMessage chatMessage = (ChatMessage) chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));

        if (!chatMessage.getAuthor().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 메시지를 삭제할 권한이 없습니다.");
        }

        chatMessageRepository.delete(chatMessage);
    }
}
