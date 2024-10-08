package com.ceos20_instagram.domain.member.dto;

import com.ceos20_instagram.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequestDto {
    @NotBlank(message = "전화번호는 필수입니다.")
    private String phone;

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;
    // 추후 카카오 로그인의 경우 회원가입 시 해당 필드 설정 가능. 지금은 굳이 필요없다고 판단해 주석 처리
//    private Timestamp birth;
//    private Gender gender;
//    private String profileImage;

    public static Member toEntity(SignupRequestDto dto) {
        return Member.builder()
                     .phone(dto.getPhone())
                     .email(dto.getEmail())
                     .password(dto.getPassword())
                     .name(dto.getName())
                     .nickname(dto.getNickname())
                     .build();
    }
}
