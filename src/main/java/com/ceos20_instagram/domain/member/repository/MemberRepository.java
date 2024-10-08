package com.ceos20_instagram.domain.member.repository;

import com.ceos20_instagram.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    @Query("SELECT m FROM Member m WHERE m.id IN :memberIds")
    List<Member> findMembersByIds(@Param("memberIds") List<Long> memberIds);

    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);
}
