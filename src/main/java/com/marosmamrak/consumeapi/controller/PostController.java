package com.marosmamrak.consumeapi.controller;

import com.marosmamrak.consumeapi.exceptions.PostNotFoundException;
import com.marosmamrak.consumeapi.model.PostCreateDTO;
import com.marosmamrak.consumeapi.model.PostDTO;
import com.marosmamrak.consumeapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "Post API", description = "Post management operations")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    @Operation(summary = "Add new post")
    public ResponseEntity<PostDTO> addPost(@RequestBody PostCreateDTO postCreateDTO) {
        return new ResponseEntity<>(postService.createPost(postCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{id}")
    @Operation(summary = "Get post by id")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id)
                .orElseThrow(() -> new PostNotFoundException("Post with id " + id + " not found.")));
    }

    @GetMapping("/posts")
    @Operation(summary = "Get all posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PutMapping("/posts/{id}")
    @Operation(summary = "Update specific post by id")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostCreateDTO postCreateDTO) {
        return ResponseEntity.ok(postService.updatePost(id, postCreateDTO));
    }

    @DeleteMapping("/posts/{id}")
    @Operation(summary = "Delete specific post")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/posts")
    @Operation(summary = "Get posts by user ID",
            description = "Retrieve all posts associated with a given user ID.")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

}
