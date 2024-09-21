package com.ceos20_instagram.domain.chat.entity;

import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomMember extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_member_id")
    private Long id;

    // Member와 다대일 관계 (한 명의 사용자가 여러 채팅방에 참여할 수 있음)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // ChatRoom과 다대일 관계 (한 명의 사용자가 여러 채팅방에 참여할 수 있음)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;
}
