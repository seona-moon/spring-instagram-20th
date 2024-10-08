package com.ceos20_instagram.domain.member.repository;

import com.ceos20_instagram.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);
}
