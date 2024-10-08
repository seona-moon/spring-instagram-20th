package com.ceos20_instagram.domain.comment.repository;

import com.ceos20_instagram.domain.comment.entity.Comment;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByMember(Member member);
}