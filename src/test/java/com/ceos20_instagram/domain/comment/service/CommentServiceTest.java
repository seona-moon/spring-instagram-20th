package com.ceos20_instagram.domain.comment.service;

import com.ceos20_instagram.domain.comment.CommentRepository;
import com.ceos20_instagram.domain.comment.entity.Comment;
import com.ceos20_instagram.domain.member.entity.Gender;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.member.service.MemberService;
import com.ceos20_instagram.domain.post.entity.Post;
import com.ceos20_instagram.domain.post.service.PostService;
import java.nio.file.AccessDeniedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private PostService postService;

    private Member testMember;
    private Post testPost;
    private Comment testComment;

    @BeforeEach
    void setUp() {
        // Member 셋업
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

        // Post 셋업
        testPost = Post.builder()
                .id(1L)
                .content("Test content")
                .location("Seoul")
                .member(testMember)
                .build();

        // Comment 셋업
        testComment = Comment.builder()
                .id(1L)
                .content("Test comment")
                .member(testMember)
                .post(testPost)
                .build();
    }

    @Test
    public void 댓글_생성() throws Throwable {
        // Given
        Long postId = testPost.getId();
        Long memberId = testMember.getId();
        String content = "Test comment";

        when(memberService.findMemberById(memberId)).thenReturn(testMember);
        when(postService.findPostById(postId)).thenReturn(testPost);
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment);

        // When
        Comment comment = commentService.createComment(postId, memberId, content);

        // Then
        assertNotNull(comment);
        assertEquals(content, comment.getContent());
        assertEquals(testMember, comment.getMember());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    public void 대댓글_생성() throws Throwable {
        // Given
        Long postId = testPost.getId();
        Long memberId = testMember.getId();
        Long parentId = 2L;
        String content = "Test comment with parent";

        Comment parentComment = Comment.builder().id(parentId).content("Parent comment").build();

        when(memberService.findMemberById(memberId)).thenReturn(testMember);
        when(postService.findPostById(postId)).thenReturn(testPost);
        when(commentRepository.findById(parentId)).thenReturn(Optional.of(parentComment));
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment);

        // When
        Comment comment = commentService.createComment(postId, memberId, content, parentId);

        // Then
        assertNotNull(comment);
        assertEquals(content, comment.getContent());
        assertEquals(parentComment, comment.getParent());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    public void 댓글_삭제() throws AccessDeniedException {
        // Given
        Long commentId = testComment.getId();
        Long memberId = testMember.getId();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(testComment));

        // When
        commentService.deleteComment(memberId, commentId);

        // Then
        verify(commentRepository, times(1)).delete(testComment);
    }

    @Test
    public void 댓글_조회() {
        // Given
        Long commentId = testComment.getId();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(testComment));

        // When
        Comment comment = commentService.findCommentById(commentId);

        // Then
        assertNotNull(comment);
        assertEquals(testComment, comment);
    }

    @Test
    public void 계정의_댓글_전체_조회() {
        // Given
        when(commentRepository.findAllByMember(testMember)).thenReturn(List.of(testComment));

        // When
        List<Comment> comments = commentService.findAccountCommentList(testMember);

        // Then
        assertNotNull(comments);
        assertFalse(comments.isEmpty());
        assertEquals(testComment, comments.get(0));
    }

    @Test
    public void 게시글의_댓글_전체_조회() throws Throwable {
        // Given
        Long postId = testPost.getId();

        when(postService.findPostById(postId)).thenReturn(testPost);
        when(commentRepository.findAllByPost(testPost)).thenReturn(List.of(testComment));

        // When
        List<Comment> comments = commentService.findPostCommentList(postId);

        // Then
        assertNotNull(comments);
        assertFalse(comments.isEmpty());
        assertEquals(testComment, comments.get(0));
    }
}
