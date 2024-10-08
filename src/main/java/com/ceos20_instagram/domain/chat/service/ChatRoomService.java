package com.ceos20_instagram.domain.chat.service;

import com.ceos20_instagram.domain.chat.repository.ChatRoomMemberRepository;
import com.ceos20_instagram.domain.chat.repository.ChatRoomRepository;
import com.ceos20_instagram.domain.chat.entity.ChatRoom;
import com.ceos20_instagram.domain.chat.entity.ChatRoomMember;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.member.service.MemberService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository; // 채팅방 멤버 저장
    private final MemberService memberService; // 멤버 정보 조회

    // 채팅방 생성 및 멤버 추가
    public ChatRoom createChatRoom(List<Long> memberIds) {
        if (memberIds.size() < 2) {
            throw new IllegalArgumentException("채팅방에는 최소 2명 이상의 멤버가 필요합니다.");
        }
        // 채팅방 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .build();
        chatRoomRepository.save(chatRoom);
        // 멤버 추가
        addMemberToChatRoom(chatRoom, memberIds);
        return chatRoom;
    }

    // 채팅방 멤버 추가
    public void addMemberToChatRoom(ChatRoom chatRoom, List<Long> memberIds) {
        for (Long memberId : memberIds) {
            Member member = memberService.findMemberById(memberId);
            ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                    .chatRoom(chatRoom)
                    .member(member)
                    .build();

            chatRoomMemberRepository.save(chatRoomMember);
        }
    }

    // 채팅방 삭제
    public void deleteChatRoom(Long chatRoomId) throws Throwable {
        ChatRoom chatRoom = (ChatRoom) chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        chatRoomRepository.delete(chatRoom);
    }

    // 채팅방의 모든 멤버 조회
    public List<Member> getMembersInChatRoom(Long chatRoomId) throws Throwable {
        ChatRoom chatRoom = (ChatRoom) chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findMemberByChatRoom(chatRoom);
        return chatRoomMembers.stream()
                .map(ChatRoomMember::getMember)
                .collect(Collectors.toList());
    }


    // 멤버가 참여한 모든 채팅방 조회
    public List<ChatRoom> getChatRoomsForMember(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findChatRoomByMember(member);

        // 멤버가 참여한 모든 채팅방 리스트 반환
        return chatRoomMembers.stream()
                .map(ChatRoomMember::getChatRoom)
                .collect(Collectors.toList());
    }

}
