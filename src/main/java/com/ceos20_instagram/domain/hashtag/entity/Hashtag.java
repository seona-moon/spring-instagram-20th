package com.ceos20_instagram.domain.hashtag.entity;


import com.ceos20_instagram.domain.post.entity.PostHashtag;
import com.ceos20_instagram.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column(nullable = false)
    private String hashtag;

    // PostHashtag와 일대다 관계 (하나의 해시태그가 여러 게시물에 연결될 수 있음)
    @OneToMany(mappedBy = "hashtag")
    private Set<PostHashtag> postHashtags;
}
