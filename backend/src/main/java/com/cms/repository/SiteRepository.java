package com.cms.repository;

import com.cms.model.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    
    Optional<Site> findByDomain(String domain);
}
