package com.cms.controller;

import com.cms.model.entity.Post;
import com.cms.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostController {
    
    private final PostService postService;
    
    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(
            @RequestParam Long siteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getAllPosts(siteId, pageable));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Post>> getPostsByStatus(
            @PathVariable String status,
            @RequestParam Long siteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getPostsByStatus(siteId, status, pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }
    
    @GetMapping("/slug/{slug}")
    public ResponseEntity<Post> getPostBySlug(
            @PathVariable String slug,
            @RequestParam Long siteId) {
        return ResponseEntity.ok(postService.getPostBySlug(siteId, slug));
    }
    
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post created = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @RequestBody Post post) {
        Post updated = postService.updatePost(id, post);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
