package com.cms.repository;

import com.cms.model.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, String> {
    
    Optional<Theme> findBySlug(String slug);
    
    List<Theme> findByIsInstalled(Boolean isInstalled);
    
    Optional<Theme> findByIsActive(Boolean isActive);
}
