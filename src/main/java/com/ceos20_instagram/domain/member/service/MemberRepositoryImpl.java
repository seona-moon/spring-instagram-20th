package com.ceos20_instagram.domain.member.service;

import com.ceos20_instagram.domain.member.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    // 데이터베이스에 대한 CRUD 작업을 수동으로 처리
    @PersistenceContext
    private EntityManager entityManager;

    // ID로 Member 조회
    @Override
    public Member findById(Long id) {
        return entityManager.find(Member.class, id);
    }

    // 모든 Member 조회
    @Override
    public List<Member> findAll() {
        return entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    // Member 저장 (새로 추가 또는 수정)
    @Override
    @Transactional
    public void save(Member member) {
        if (member.getId() == null) {
            entityManager.persist(member); // 새로운 엔티티 추가
        } else {
            entityManager.merge(member); // 기존 엔티티 수정
        }
    }

    // Member 삭제
    @Override
    @Transactional
    public void delete(Member member) {
        if (entityManager.contains(member)) {
            entityManager.remove(member); // 엔티티가 관리 중이면 제거
        } else {
            Member managedMember = entityManager.merge(member); // 엔티티를 병합한 후 제거
            entityManager.remove(managedMember);
        }
    }
}
