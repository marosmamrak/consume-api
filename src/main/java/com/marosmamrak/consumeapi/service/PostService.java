package com.marosmamrak.consumeapi.service;

import com.marosmamrak.consumeapi.model.PostCreateDTO;
import com.marosmamrak.consumeapi.model.PostDTO;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostDTO createPost(PostCreateDTO postCreateDTO);

    PostDTO updatePost(Long id, PostCreateDTO postCreateDTO);

    List<PostDTO> getAllPosts();

    List<PostDTO> getPostsByUserId(Long userId);
    Optional<PostDTO> getPostById(Long id);

    void deletePost(Long id);
}