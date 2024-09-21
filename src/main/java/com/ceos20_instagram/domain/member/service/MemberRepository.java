package com.ceos20_instagram.domain.member.service;

import com.ceos20_instagram.domain.member.entity.Member;
import java.util.List;

// JPA Repository 없이 구현
public interface MemberRepository {
    Member findById(Long id);
    List<Member> findAll();
    void save(Member member);
    void delete(Member member);
}
