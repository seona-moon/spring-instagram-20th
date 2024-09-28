package com.ceos20_instagram.domain.comment.service;

import com.ceos20_instagram.domain.comment.CommentRepository;
import com.ceos20_instagram.domain.comment.entity.Comment;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.member.service.MemberService;
import com.ceos20_instagram.domain.post.entity.Post;
import com.ceos20_instagram.domain.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final PostService postService;

    // 댓글 생성
    public Comment createComment(Long postId, Long memberId, String content) throws Throwable {
        // parentId 없이 호출할 경우 null로 설정 (메서드 오버로딩)
        return createComment(postId, memberId, content, null);
    }

    public Comment createComment(Long postId, Long memberId, String content, Long parentId) throws Throwable {
        Member writer = memberService.findMemberById(memberId);
        Post post = postService.findPostById(postId);

        // 부모 댓글이 있을 경우 찾기
        Comment parentComment = null;
        if (parentId != null) {
            parentComment = commentRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = Comment.builder()
                .content(content)
                .member(writer)
                .parent(parentComment) // 부모 댓글 설정 (없으면 null)
                .post(post)
                .build();
        commentRepository.save(comment);

        return comment;
    }

    // 댓글 삭제
    public void deleteComment(Long memberId, Long commentId) throws AccessDeniedException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 comment를 찾을 수 없습니다. id=" + commentId));
        if (!Objects.equals(memberId, comment.getMember().getId())) {
            throw new IllegalArgumentException("해당 댓글을 삭제할 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }

    // 댓글 단일 조회
    @Transactional(readOnly = true)
    public Comment findCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 comment를 찾을 수 없습니다. id=" + commentId));
        return comment;
    }

    // 작성자의 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<Comment> findAccountCommentList(Member writer) {
        return commentRepository.findAllByWriter(writer);
    }

    // 포스트의 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<Comment> findPostCommentList(Long postId) throws Throwable {
        Post post = postService.findPostById(postId);
        return commentRepository.findAllByPost(post);
    }
}
