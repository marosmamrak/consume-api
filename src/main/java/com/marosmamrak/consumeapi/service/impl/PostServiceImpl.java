package com.marosmamrak.consumeapi.service.impl;

import com.marosmamrak.consumeapi.exceptions.PostNotFoundException;
import com.marosmamrak.consumeapi.model.PostCreateDTO;
import com.marosmamrak.consumeapi.model.PostDTO;
import com.marosmamrak.consumeapi.entity.Post;
import com.marosmamrak.consumeapi.mapper.CustomMapper;
import com.marosmamrak.consumeapi.repository.PostRepository;
import com.marosmamrak.consumeapi.service.ExternalApiService;
import com.marosmamrak.consumeapi.service.PostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CustomMapper customMapper;
    @Autowired
    private ExternalApiService externalApiService;

    @Override
    @Transactional
    public PostDTO createPost(PostCreateDTO postCreateDTO) {
        externalApiService.validateUser(postCreateDTO.getUserId());

        Post post = customMapper.toEntity(postCreateDTO);
        post = postRepository.save(post);
        return customMapper.toDTO(post);
    }

    @Override
    @Transactional
    public Optional<PostDTO> updatePost(Integer id, PostCreateDTO postCreateDTO) {
        return postRepository.findById(id).map(post -> {
            post.setTitle(postCreateDTO.getTitle());
            post.setBody(postCreateDTO.getBody());
            post.setUserId(postCreateDTO.getUserId());

            Post updatedPost = postRepository.save(post);
            return Optional.of(customMapper.toDTO(updatedPost));
        }).orElseThrow(() -> new PostNotFoundException("Post with id " + id + " not found."));
    }

    @Override
    public List<PostDTO> getPostsByUserId(Integer userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream()
                .map(customMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<PostDTO> getPostById(Integer id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            return Optional.of(customMapper.toDTO(postOptional.get()));
        } else {
            try {
                Post post = externalApiService.findPostById(id);
                if (post != null) {
                    post.setUserId(0);
                    post = postRepository.save(post);
                    return Optional.of(customMapper.toDTO(post));
                }
            } catch (Exception e) {
                throw new PostNotFoundException("Post with id " + id + " not found.");
            }
        }
        return Optional.empty();
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> {
                    PostDTO dto = new PostDTO();
                    dto.setId(post.getId());
                    dto.setUserId(post.getUserId());
                    dto.setTitle(post.getTitle());
                    dto.setBody(post.getBody());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public void deletePost(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post with id " + id + " not found."));
        postRepository.delete(post);
    }
}
