package com.ceos20_instagram.domain.post.service;

import com.ceos20_instagram.domain.member.MemberRepository;
import com.ceos20_instagram.domain.member.entity.Member;
import com.ceos20_instagram.domain.post.PostRepository;
import com.ceos20_instagram.domain.post.entity.Post;
import jakarta.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.swing.text.AbstractDocument.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 게시글 등록
    public Post createNewPost(Long memberId, String content, Optional<String> location) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 Member를 찾을 수 없습니다. id=" + memberId));
        Post post = Post.builder().member(member).content(content).location(location.orElse(null)).build();
        Post savedPost = (Post) postRepository.save(post);  // post 정보를 DB에 저장
        return savedPost; // post 반환
    }

    // 게시글 수정
    public Long updatePost(Long postId, String content) throws Throwable {
        Post post = findPostById(postId);
        post.update(content);
        return post.getId();
    }

    // 게시글 삭제
    public void deletePost(Long postId, Long memberId) throws Throwable {
        Post post = findPostById(postId);
        if (!Objects.equals(memberId, post.getMember().getId())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        postRepository.delete(post);
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<Post> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    // 게시글 수 조회
    @Transactional(readOnly = true)
    public long countAllPosts() {
        return postRepository.count();
    }

    // 단일 게시글 조회
    @Transactional(readOnly = true)
    public Post findPostById(Long postId) throws Throwable {
        Post post = (Post) postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Post를 찾을 수 없습니다.id=" + postId));
        return post;
    }
}
