package com.cms.repository;

import com.cms.model.entity.BasePage;
import com.cms.model.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<BasePage, Long> {
    
    Optional<BasePage> findBySiteAndSlug(Site site, String slug);
    
    List<BasePage> findBySite(Site site);
    
    List<BasePage> findBySiteAndIsPublished(Site site, Boolean isPublished);
}
