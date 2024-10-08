package com.ceos20_instagram.domain.chat.repository;

import com.ceos20_instagram.domain.chat.entity.ChatRoom;
import com.ceos20_instagram.domain.chat.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
