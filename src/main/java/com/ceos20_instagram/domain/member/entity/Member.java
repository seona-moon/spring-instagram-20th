package com.ceos20_instagram.domain.member.entity;

import com.ceos20_instagram.domain.chat.entity.ChatMessage;
import com.ceos20_instagram.domain.chat.entity.ChatRoomMember;
import com.ceos20_instagram.domain.comment.entity.Comment;
import com.ceos20_instagram.domain.comment.entity.CommentLike;
import com.ceos20_instagram.domain.follow.entity.Follow;
import com.ceos20_instagram.domain.post.entity.Post;
import com.ceos20_instagram.domain.post.entity.PostLike;
import com.ceos20_instagram.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 매개변수가 없는 생성자 자동 생성
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(length = 13, nullable = false)
    private String phone;

    @Column(length = 200, nullable = false)
    private String email;

    @Column(length = 200, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String nickname;

    @Column(length = 200)
    private String introduction;

    @Column
    private Timestamp birth;

    @Column(columnDefinition = "text")
    private String profileUrl;

    @Column(columnDefinition = "text")
    private String profile_image;

    // 게시글 연관관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    // 댓글 연관관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    // 댓글 좋아요 연관관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLikes;

    // 게시글 좋아요 연관관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes;

    // 팔로우 연관관계 (내가 팔로우하는 사람들)
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followings;

    // 팔로워 연관관계 (나를 팔로우하는 사람들)
    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followers;

    // 채팅방 참여 연관관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomMember> chatRooms;

    // 채팅 메시지 연관관계
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessages;

    // 필드 값을 설정할 수 있는 생성자 추가
    public Member(String phone, String email, String password, String name, String nickname) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }
}
