package com.ceos20_instagram.domain.member.entity;

import com.ceos20_instagram.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 매개변수가 없는 기본 생성자 (JPA의 프록시 객체를 위해)
@AllArgsConstructor // 모든 필드를 초기화해주는 생성자 (Builder를 위해 필요)
@Builder // 빌더 패턴을 통해 선택적으로 원하는 필드만 초기화해줄 수 있음. 이때 내부적으로 모든 필드 초기화가 필요
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @NonNull
    private String phone;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String name;

    @NonNull
    private String nickname;

    private String introduction;

    private Timestamp birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(columnDefinition = "text")
    private String profileUrl;

    @Column(columnDefinition = "text")
    private String profile_image;
}
