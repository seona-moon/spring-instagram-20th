package com.ceos20_instagram.domain.chat.entity;

import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    // ChatRoom과 다대일 관계 (여러 메시지가 하나의 채팅방에 연결됨)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    // Member와 다대일 관계 (여러 메시지가 하나의 작성자에 연결됨)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

}
