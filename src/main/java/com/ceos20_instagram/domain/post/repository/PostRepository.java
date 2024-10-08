package com.ceos20_instagram.domain.post.repository;

import com.ceos20_instagram.domain.chat.entity.ChatRoomMember;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByMember(Member member);

    // N+1 문제를 방지하기 위해 페치 조인 사용
    @Query("SELECT p FROM Post p JOIN FETCH p.member")
    List<Post> findAllFetchJoin();
}
