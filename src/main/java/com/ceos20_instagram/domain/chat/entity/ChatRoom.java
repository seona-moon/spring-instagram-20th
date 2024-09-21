package com.ceos20_instagram.domain.chat.entity;

import com.ceos20_instagram.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    // ChatRoom과 ChatMessage의 일대다 관계 (하나의 채팅방에 여러 메시지)
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages;

    // ChatRoom과 ChatRoomMember의 일대다 관계 (하나의 채팅방에 여러 참여자)
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomMember> members;

    // ChatRoom과 ChatMessage의 다대일 관계 (채팅방의 마지막 메시지)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_chat_message_id")
    private ChatMessage lastMessage;
}
