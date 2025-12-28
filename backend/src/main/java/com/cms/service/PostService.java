package com.cms.service;

import com.cms.model.entity.Post;
import com.cms.model.entity.Site;
import com.cms.repository.PostRepository;
import com.cms.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    
    private final PostRepository postRepository;
    private final SiteRepository siteRepository;
    
    public Page<Post> getAllPosts(Long siteId, Pageable pageable) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found"));
        return postRepository.findBySite(site, pageable);
    }
    
    public Page<Post> getPostsByStatus(Long siteId, String status, Pageable pageable) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found"));
        return postRepository.findBySiteAndStatus(site, status, pageable);
    }
    
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }
    
    public Post getPostBySlug(Long siteId, String slug) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found"));
        return postRepository.findBySiteAndSlug(site, slug)
                .orElseThrow(() -> new RuntimeException("Post not found with slug: " + slug));
    }
    
    @Transactional
    public Post createPost(Post post) {
        Post savedPost = postRepository.save(post);
        log.info("Created post: {} - {}", savedPost.getId(), savedPost.getTitle());
        return savedPost;
    }
    
    @Transactional
    public Post updatePost(Long id, Post updatedPost) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        
        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setSlug(updatedPost.getSlug());
        existingPost.setContent(updatedPost.getContent());
        existingPost.setExcerpt(updatedPost.getExcerpt());
        existingPost.setFeaturedImage(updatedPost.getFeaturedImage());
        existingPost.setStatus(updatedPost.getStatus());
        existingPost.setPublishDate(updatedPost.getPublishDate());
        existingPost.setTags(updatedPost.getTags());
        existingPost.setCategories(updatedPost.getCategories());
        existingPost.setSeo(updatedPost.getSeo());
        
        Post savedPost = postRepository.save(existingPost);
        log.info("Updated post: {} - {}", savedPost.getId(), savedPost.getTitle());
        return savedPost;
    }
    
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
        log.info("Deleted post: {}", id);
    }
}
