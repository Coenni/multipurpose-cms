package com.cms.repository;

import com.cms.model.entity.Plugin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PluginRepository extends JpaRepository<Plugin, String> {
    
    Optional<Plugin> findBySlug(String slug);
    
    List<Plugin> findByIsInstalled(Boolean isInstalled);
    
    List<Plugin> findByIsActive(Boolean isActive);
    
    List<Plugin> findByCategory(String category);
}
