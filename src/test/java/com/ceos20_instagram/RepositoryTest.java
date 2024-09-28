package com.ceos20_instagram;
import com.ceos20_instagram.domain.member.MemberRepository;
import com.ceos20_instagram.domain.member.entity.Gender;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.post.PostRepository;
import com.ceos20_instagram.domain.post.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Member member1;

    @BeforeEach
    void setUp() {
        // 테스트 데이터를 위한 엔티티 생성 및 저장
        member1 = Member.builder()
                .phone("123456789")
                .email("user1@example.com")
                .password("password")
                .name("User1")
                .nickname("user1")
                .introduction("Hello!")
                .birth(Timestamp.valueOf(LocalDateTime.now()))
                .gender(Gender.MALE)
                .profileUrl("http://example.com/profile1")
                .profile_image("http://example.com/image1")
                .build();

        Member member2 = Member.builder()
                .phone("987654321")
                .email("user2@example.com")
                .password("password")
                .name("User2")
                .nickname("user2")
                .introduction("Hi!")
                .birth(Timestamp.valueOf(LocalDateTime.now()))
                .gender(Gender.FEMALE)
                .profileUrl("http://example.com/profile2")
                .profile_image("http://example.com/image2")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        Post post1 = Post.builder()
                .content("Post 1 content")
                .location("Seoul")
                .member(member1)
                .build();

        Post post2 = Post.builder()
                .content("Post 2 content")
                .location("Busan")
                .member(member1)
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        // EntityManager를 플러시하여 강제로 저장
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("멤버가 작성한 포스트 목록 조회 - 연관관계 매핑 확인")
    void testFindPostsByMember() {
        // Member를 기준으로 Post 조회
        Member member = memberRepository.findMemberByEmail("user1@example.com")
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        List<Post> posts = postRepository.findByMember(member);

        // 연관관계 매핑 확인
        assertThat(posts).hasSize(2);
        assertThat(posts.get(0).getMember().getEmail()).isEqualTo("user1@example.com");
    }

    @Test
    @DisplayName("포스트를 가져올 때 멤버까지 함께 로딩 - N+1 문제 테스트")
    void testFetchPostsWithMember() {
        // Post를 모두 조회하면서 Member를 함께 로딩
        List<Post> posts = postRepository.findAll();

        // 각 Post마다 Member가 Lazy 로딩 되어있어야 함
        assertThat(posts).isNotEmpty();
        posts.forEach(post -> {
            // Member 정보가 Lazy 로딩으로 초기화되어 있음
            assertThat(post.getMember().getEmail()).isNotBlank();
        });
    }

    @Test
    @DisplayName("N+1 문제를 방지하는 페치 조인 테스트")
    void testFetchJoinPostsWithMember() {
        // 페치 조인을 통해 N+1 문제를 방지하여 Post와 Member를 함께 로딩
        List<Post> posts = postRepository.findAllFetchJoin();

        // 각 Post마다 Member가 즉시 로딩되어 있어야 함
        assertThat(posts).isNotEmpty();
        posts.forEach(post -> {
            // Member 정보가 초기화되어 있어야 함
            assertThat(post.getMember().getEmail()).isNotBlank();
        });
    }
}
