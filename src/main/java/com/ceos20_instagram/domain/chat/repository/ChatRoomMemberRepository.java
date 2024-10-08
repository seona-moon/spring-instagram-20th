package com.ceos20_instagram.domain.chat.repository;

import com.ceos20_instagram.domain.chat.entity.ChatRoom;
import com.ceos20_instagram.domain.chat.entity.ChatRoomMember;
import com.ceos20_instagram.domain.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    List<ChatRoomMember> findChatRoomByMember(Member member);

    List<ChatRoomMember> findMemberByChatRoom(ChatRoom chatRoom);
}
