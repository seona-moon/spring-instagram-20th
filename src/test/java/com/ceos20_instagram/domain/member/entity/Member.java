package com.ceos20_instagram.domain.member.entity;

import com.ceos20_instagram.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;
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
}
