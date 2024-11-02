package com.ceos20_instagram.domain.comment.service;

import com.ceos20_instagram.domain.comment.dto.CommentCreateRequestDto;
import com.ceos20_instagram.domain.comment.dto.CommentResponseDto;
import com.ceos20_instagram.domain.comment.entity.Comment;
import com.ceos20_instagram.domain.comment.repository.CommentRepository;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.member.service.MemberService;
import com.ceos20_instagram.domain.post.entity.Post;
import com.ceos20_instagram.domain.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
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
    public CommentResponseDto createComment(CommentCreateRequestDto dto) throws Throwable {
        Member writer = memberService.findMemberById(dto.getMemberId());
        Post post = postService.findPostById(dto.getPostId());

        // 부모 댓글이 있을 경우 찾기
        Comment parentComment = null;
        if (dto.getParentId() != null) {
            parentComment = commentRepository.findById(dto.getParentId())
                                             .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = Comment.builder()
                                 .content(dto.getContent())
                                 .member(writer)
                                 .parent(parentComment)
                                 .post(post)
                                 .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentResponseDto.from(savedComment);
    }

    // 댓글 단일 조회
    @Transactional(readOnly = true)
    public CommentResponseDto findCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                                           .orElseThrow(() -> new EntityNotFoundException(
                                                   "해당 id를 가진 comment를 찾을 수 없습니다. id=" + commentId));
        return CommentResponseDto.from(comment);
    }

    // 작성자의 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAccountCommentList(Long memberId) {
        Member writer = memberService.findMemberById(memberId);
        List<Comment> comments = commentRepository.findAllByMember(writer);
        return comments.stream()
                       .map(CommentResponseDto::from)
                       .collect(Collectors.toList());
    }

    // 포스트의 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findPostCommentList(Long postId) throws Throwable {
        Post post = postService.findPostById(postId);
        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments.stream()
                       .map(CommentResponseDto::from)
                       .collect(Collectors.toList());
    }
}

