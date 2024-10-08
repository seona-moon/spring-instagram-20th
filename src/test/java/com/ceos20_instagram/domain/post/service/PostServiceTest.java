package com.ceos20_instagram.domain.post.service;

import com.ceos20_instagram.domain.member.entity.Gender;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.member.service.MemberService;
import com.ceos20_instagram.domain.post.repository.PostRepository;
import com.ceos20_instagram.domain.post.entity.Post;
import jakarta.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private PostService postService;

    private Member testMember;
    private Post testPost;

    @BeforeEach
    void setUp() {
        testMember = Member.builder()
                .id(1L)
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
        testPost = Post.builder()
                .id(1L)
                .content("Test content")
                .location("Seoul")
                .member(testMember)
                .build();
    }

    @Test
    void 게시글_등록() {
        // given
        given(memberService.findMemberById(1L)).willReturn(testMember);
        given(postRepository.save(any(Post.class))).willReturn(testPost);

        // when
        Post savedPost = postService.createNewPost(1L, "Test content", Optional.empty());

        // then
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getContent()).isEqualTo("Test content");
        assertThat(savedPost.getMember().getId()).isEqualTo(1L);

        verify(postRepository, times(1)).save(any(Post.class));
        verify(memberService, times(1)).findMemberById(1L);
    }

    @Test
    void 게시글_수정() throws Throwable {
        // given
        given(postRepository.findById(1L)).willReturn(Optional.of(testPost));

        // when
        Long updatedPostId = postService.updatePost(1L, "Updated content");

        // then
        assertThat(updatedPostId).isEqualTo(1L);
        assertThat(testPost.getContent()).isEqualTo("Updated content");

        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void 게시글_삭제() throws Throwable {
        // given
        given(postRepository.findById(1L)).willReturn(Optional.of(testPost));

        // when
        postService.deletePost(1L, 1L);

        // then
        verify(postRepository, times(1)).delete(testPost);
    }

    @Test
    void 게시글_삭제_권한_없음() throws Throwable {
        // given
        given(postRepository.findById(1L)).willReturn(Optional.of(testPost));

        // when & then
        assertThatThrownBy(() -> postService.deletePost(1L, 2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 게시글을 삭제할 권한이 없습니다.");

        verify(postRepository, never()).delete(testPost);
    }

    @Test
    void 단일_게시글_조회() throws Throwable {
        // given
        given(postRepository.findById(1L)).willReturn(Optional.of(testPost));

        // when
        Post foundPost = postService.findPostById(1L);

        // then
        assertThat(foundPost).isNotNull();
        assertThat(foundPost.getId()).isEqualTo(1L);
    }

    @Test
    void 단일_게시글_조회_실패() {
        // given
        given(postRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postService.findPostById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("해당 id를 가진 Post를 찾을 수 없습니다.id=1");
    }
}
