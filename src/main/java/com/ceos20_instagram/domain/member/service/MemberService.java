package com.ceos20_instagram.domain.member.service;

import com.ceos20_instagram.domain.member.dto.SignupRequestDto;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    public Long signUp(SignupRequestDto dto) {
        // 검증 로직
        if (existsMemberByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 email입니다." + dto.getEmail());
        }
        if (existsMemberByNickname(dto.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 nickname입니다." + dto.getNickname());
        }

        Member member = SignupRequestDto.toEntity(dto);

        memberRepository.save(member);
        return member.getId();
    }

    // 회원 여부 확인
    @Transactional(readOnly = true)
    public boolean existsMemberByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsMemberByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    // 회원 조회
    @Transactional(readOnly = true)
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                               .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Member를 찾을 수 없습니다. id=" + id));
    }

    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findMembersByIds(List<Long> memberIds) {
        return memberRepository.findMembersByIds(memberIds);
    }

    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email)
                               .orElseThrow(() -> new EntityNotFoundException(
                                       "해당 email을 가진 Member를 찾을 수 없습니다. email=" + email));
    }
}
