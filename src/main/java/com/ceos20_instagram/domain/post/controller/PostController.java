package com.ceos20_instagram.domain.post.controller;


import com.ceos20_instagram.domain.post.dto.PostCreateRequestDto;
import com.ceos20_instagram.domain.post.entity.Post;
import com.ceos20_instagram.domain.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostCreateRequestDto dto) {
        Post createdPost = postService.createNewPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createdPost);
    }

    // 게시글 단일 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        try {
            Post post = postService.findPostById(postId);
            return ResponseEntity.ok(post);
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }

    // 특정 멤버의 게시글 전체 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Post>> getPostsByMemberId(@PathVariable Long memberId) throws Throwable {
        List<Post> posts = (List<Post>) postService.findPostById(memberId);
        return ResponseEntity.ok(posts);
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<Long> updatePost(@PathVariable Long postId, @RequestBody String content) {
        try {
            Long updatedPostId = postService.updatePost(postId, content);
            return ResponseEntity.ok(updatedPostId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @RequestParam Long memberId) {
        try {
            postService.deletePost(postId, memberId);
            return ResponseEntity.noContent()
                                 .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .build();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.findAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 게시글 수 조회
    @GetMapping("/count")
    public ResponseEntity<Long> countAllPosts() {
        long count = postService.countAllPosts();
        return ResponseEntity.ok(count);
    }
}

