package com.ceos20_instagram.domain.chat;

import com.ceos20_instagram.domain.chat.entity.ChatMessage;
import com.ceos20_instagram.domain.chat.entity.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findMessagesByChatRoomOrderByCreatedAtAsc(ChatRoom chatRoom);
}
