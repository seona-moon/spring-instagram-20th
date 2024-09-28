package com.ceos20_instagram.domain.chat.entity;

import com.ceos20_instagram.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    // ChatRoom과 ChatMessage의 다대일 관계 (채팅방의 마지막 메시지)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_chat_message_id")
    private ChatMessage lastMessage;
}
