package com.ceos20_instagram.domain.post.service;

import com.ceos20_instagram.domain.post.entity.Post;
import java.util.List;

public interface PostRepository {
    Post findById(Long id);
    List<Post> findAll();
    void save(Post post);
    void delete(Post post);
}
