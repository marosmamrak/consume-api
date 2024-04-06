package com.marosmamrak.consumeapi.controller;

import com.marosmamrak.consumeapi.model.PostCreateDTO;
import com.marosmamrak.consumeapi.model.PostDTO;
import com.marosmamrak.consumeapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "Post API", description = "Post management operations")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/add")
    @Operation(summary = "Add new post")
    public ResponseEntity<PostDTO> addPost(@RequestBody PostCreateDTO postCreateDTO) {
        PostDTO createdPost = postService.createPost(postCreateDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }



    @GetMapping("/getPostById/{id}")
    @Operation(summary = "List post by id")
    public ResponseEntity<?> getPostById(@PathVariable Integer id) {
        Optional<PostDTO> postDTOOptional = postService.getPostById(id);
        return postDTOOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get posts by user ID",
            description = "Retrieve all posts associated with a given user ID.")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Integer userId) {
        List<PostDTO> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts")
    @Operation(summary = "Get all posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }


    @PutMapping("/update/{id}")
    @Operation(summary = "Update specific post by id")
    public ResponseEntity<?> updatePost(@PathVariable Integer id, @RequestBody PostCreateDTO postCreateDTO) {
        Optional<PostDTO> updatedPost = postService.updatePost(id, postCreateDTO);
        return updatedPost
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete specific post")
    public ResponseEntity<?> deletePost(@PathVariable Integer id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }






}
