package com.marosmamrak.consumeapi.service;

import com.marosmamrak.consumeapi.model.PostCreateDTO;
import com.marosmamrak.consumeapi.model.PostDTO;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostDTO createPost(PostCreateDTO postCreateDTO);

    Optional<PostDTO> updatePost(Integer id, PostCreateDTO postCreateDTO);

    List<PostDTO> getAllPosts();

    List<PostDTO> getPostsByUserId(Integer userId);

    Optional<PostDTO> getPostById(Integer id);

    void deletePost(Integer id);
}
