package com.ceos20_instagram.domain.comment.controller;

import com.ceos20_instagram.domain.comment.dto.CommentCreateRequestDto;
import com.ceos20_instagram.domain.comment.dto.CommentResponseDto;
import com.ceos20_instagram.domain.comment.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성 (대댓글 포함)
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentCreateRequestDto dto) {
        try {
            CommentResponseDto createdComment = commentService.createComment(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(createdComment);
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(null);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    // 댓글 단일 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long commentId) {
        try {
            CommentResponseDto comment = commentService.findCommentById(commentId);
            return ResponseEntity.ok(comment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }

    // 특정 포스트의 댓글 전체 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Long postId) {
        try {
            List<CommentResponseDto> comments = commentService.findPostCommentList(postId);
            return ResponseEntity.ok(comments);
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }

    // 특정 사용자의 댓글 전체 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByMemberId(@PathVariable Long memberId) {
        try {
            List<CommentResponseDto> comments = commentService.findAccountCommentList(memberId);
            return ResponseEntity.ok(comments);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }
}
