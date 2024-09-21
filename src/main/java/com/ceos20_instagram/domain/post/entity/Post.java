package com.ceos20_instagram.domain.post.entity;

import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @Column(name = "post_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String content;

    @Column(length = 200)
    private String location;

    // Member와 다대일 관계 (여러 게시물이 하나의 사용자에 연결됨)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // PostImage와 일대다 관계 (하나의 게시물이 여러 이미지를 가질 수 있음)
    @OneToMany(mappedBy = "post")
    private Set<PostImage> postImages;

    // PostHashtag와 일대다 관계 (하나의 게시물이 여러 해시태그를 가질 수 있음)
    @OneToMany(mappedBy = "post")
    private Set<PostHashtag> postHashtags;

    // PostLike와 일대다 관계 (하나의 게시물이 여러 "좋아요"를 받을 수 있음)
    @OneToMany(mappedBy = "post")
    private Set<PostLike> postLikes;
}
