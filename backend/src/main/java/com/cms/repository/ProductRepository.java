package com.cms.repository;

import com.cms.model.entity.Product;
import com.cms.model.entity.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findBySiteAndSlug(Site site, String slug);
    
    Page<Product> findBySite(Site site, Pageable pageable);
    
    Page<Product> findBySiteAndIsPublished(Site site, Boolean isPublished, Pageable pageable);
}
