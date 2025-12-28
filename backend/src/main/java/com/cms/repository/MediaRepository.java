package com.cms.repository;

import com.cms.model.entity.Media;
import com.cms.model.entity.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    
    Page<Media> findBySite(Site site, Pageable pageable);
}
