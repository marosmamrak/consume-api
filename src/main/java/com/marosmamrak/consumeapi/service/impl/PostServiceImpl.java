package com.marosmamrak.consumeapi.service.impl;

import com.marosmamrak.consumeapi.entity.Post;
import com.marosmamrak.consumeapi.exceptions.PostNotFoundException;
import com.marosmamrak.consumeapi.mapper.CustomMapper;
import com.marosmamrak.consumeapi.model.PostCreateDTO;
import com.marosmamrak.consumeapi.model.PostDTO;
import com.marosmamrak.consumeapi.repository.PostRepository;
import com.marosmamrak.consumeapi.service.ExternalApiService;
import com.marosmamrak.consumeapi.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;
    private final CustomMapper customMapper;
    private final ExternalApiService externalApiService;

    @Override
    @Transactional
    public PostDTO createPost(PostCreateDTO postCreateDTO) {
        externalApiService.validateUser(postCreateDTO.getUserId());

        var post = customMapper.toEntity(postCreateDTO);
        post.setId(postRepository.getMaxId() + 1);
        postRepository.save(post);

        return customMapper.toDTO(post);
    }

    @Override
    @Transactional
    public PostDTO updatePost(Long id, PostCreateDTO postCreateDTO) {
        var post = getByIdOrNotFound(id);
        post.setTitle(postCreateDTO.getTitle());
        post.setBody(postCreateDTO.getBody());
        post.setUserId(postCreateDTO.getUserId());

        return customMapper.toDTO(postRepository.save(post));
    }

    @Override
    public List<PostDTO> getPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId).stream()
                .map(customMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public Optional<PostDTO> getPostById(Long id) {
        Post post;
        try {
            post = getByIdOrNotFound(id);
        } catch (Exception e) {
            try {
                post = externalApiService.findPostById(id);
                postRepository.save(post);
            } catch (Exception ex) {
                throw new PostNotFoundException("Post with id " + id + " not found.");
            }
        }

        return Optional.of(customMapper.toDTO(post));
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(customMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        Post post = getByIdOrNotFound(id);
        postRepository.delete(post);
    }

    private Post getByIdOrNotFound(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post with id " + id + " not found."));
    }
}
