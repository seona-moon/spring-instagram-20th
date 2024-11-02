package com.ceos20_instagram.domain.comment.controller;

import com.ceos20_instagram.domain.comment.entity.Comment;
import com.ceos20_instagram.domain.comment.service.CommentService;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.member.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final MemberService memberService;

    // 댓글 작성 (대댓글 포함)
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestParam Long postId,
                                                 @RequestParam Long memberId,
                                                 @RequestParam String content,
                                                 @RequestParam(required = false) Long parentId) {
        try {
            Comment comment;
            if (parentId != null) {
                comment = commentService.createComment(postId, memberId, content, parentId);
            } else {
                comment = commentService.createComment(postId, memberId, content);
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(comment);
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(null);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @RequestParam Long memberId) {
        try {
            commentService.deleteComment(memberId, commentId);
            return ResponseEntity.noContent()
                                 .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .build();
        }
    }

    // 댓글 단일 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        try {
            Comment comment = commentService.findCommentById(commentId);
            return ResponseEntity.ok(comment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }

    // 특정 포스트의 댓글 전체 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        try {
            List<Comment> comments = commentService.findPostCommentList(postId);
            return ResponseEntity.ok(comments);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    // 특정 사용자의 댓글 전체 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Comment>> getCommentsByMemberId(@PathVariable Long memberId) {
        try {
            Member writer = memberService.findMemberById(memberId);
            List<Comment> comments = commentService.findAccountCommentList(writer);
            return ResponseEntity.ok(comments);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }
}
