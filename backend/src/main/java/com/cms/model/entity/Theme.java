package com.cms.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "themes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theme {
    
    @Id
    @Column(length = 100)
    private String slug;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 50)
    private String version;
    
    private String author;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "config_json", columnDefinition = "TEXT")
    private String configJson;
    
    @Column(name = "screenshot_url", length = 500)
    private String screenshotUrl;
    
    @Column(name = "download_url", length = 500)
    private String downloadUrl;
    
    @Column(name = "is_active")
    private Boolean isActive = false;
    
    @Column(name = "is_installed")
    private Boolean isInstalled = false;
    
    @Column(name = "installed_at")
    private LocalDateTime installedAt;
}
