package com.marosmamrak.consumeapi.controller;

import com.marosmamrak.consumeapi.service.ExternalApiService;
import com.marosmamrak.consumeapi.model.Post;
import com.marosmamrak.consumeapi.repository.PostRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Post API", description = "Post management operations")
public class PostController {

    private final PostRepository postRepository;
    private final ExternalApiService externalApiService;

    public PostController(PostRepository postRepository, ExternalApiService externalApiService) {
        this.postRepository = postRepository;
        this.externalApiService = externalApiService;
    }


    @PostMapping("/add")
    @Operation(summary = "Add new post")
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        try {
            // Assuming this method validates the userId and throws an exception if invalid
            externalApiService.validateUser(post.getUserId());
            Post savedPost = postRepository.save(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
        } catch (Exception e) {
            // Log the error or handle it as appropriate
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getPostById/{id}")
    @Operation(summary = "List post by id")
    public ResponseEntity<Post> getPostById(@PathVariable Integer id) {
        return postRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    try {
                        Post post = externalApiService.findPostById(id);
                        return ResponseEntity.ok(post);
                    } catch (Exception e) {
                        // Log the error or handle it as appropriate
                        return ResponseEntity.notFound().build();
                    }
                });
    }


    @GetMapping("/getPostByUserId/{userId}")
    @Operation(summary = "List post user by id")
    public ResponseEntity<?> getPostsByUserId(@PathVariable Integer userId) {
        return new ResponseEntity<>(postRepository.findByUserId(userId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete specific post")
    public ResponseEntity<?> deletePost(@PathVariable Integer id) {
        try {
            postRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update specific post by id")
    public ResponseEntity<Post> updatePost(@PathVariable Integer id, @RequestBody Post postUpdateRequest) {
        return postRepository.findById(id)
                .map(existingPost -> {
                    existingPost.setTitle(postUpdateRequest.getTitle());
                    existingPost.setBody(postUpdateRequest.getBody());
                    postRepository.save(existingPost);
                    return ResponseEntity.ok().body(existingPost);
                })
                .orElseThrow();
    }



}
