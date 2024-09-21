package com.ceos20_instagram.domain.post.service;

import com.ceos20_instagram.domain.post.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PostRepositoryImpl implements PostRepository{
    @PersistenceContext
    private EntityManager entityManager;

    // ID로 Post 조회
    @Override
    public Post findById(Long id) {
        return entityManager.find(Post.class, id);
    }

    // 모든 Post 조회
    @Override
    public List<Post> findAll() {
        return entityManager.createQuery("SELECT p FROM Post p", Post.class).getResultList();
    }

    // Post 저장 (새로 추가 또는 수정)
    @Override
    @Transactional
    public void save(Post post) {
        if (post.getId() == null) {
            entityManager.persist(post); // 새로운 엔티티 추가
        } else {
            entityManager.merge(post); // 기존 엔티티 수정
        }
    }

    // Post 삭제
    @Override
    @Transactional
    public void delete(Post post) {
        if (entityManager.contains(post)) {
            entityManager.remove(post); // 엔티티가 관리 중이면 제거
        } else {
            Post managedPost = entityManager.merge(post); // 엔티티를 병합한 후 제거
            entityManager.remove(managedPost);
        }
    }
}