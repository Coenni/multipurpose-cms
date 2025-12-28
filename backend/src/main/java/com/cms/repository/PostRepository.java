package com.cms.repository;

import com.cms.model.entity.Post;
import com.cms.model.entity.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    Optional<Post> findBySiteAndSlug(Site site, String slug);
    
    Page<Post> findBySite(Site site, Pageable pageable);
    
    Page<Post> findBySiteAndStatus(Site site, String status, Pageable pageable);
}
