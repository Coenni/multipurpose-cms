package com.cms.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "widget_instances")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WidgetInstance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "site_theme_config_id")
    private SiteThemeConfig siteThemeConfig;
    
    @ManyToOne
    @JoinColumn(name = "plugin_slug")
    private Plugin plugin;
    
    @Column(name = "widget_area_id", length = 100)
    private String widgetAreaId;
    
    @Column
    private Integer position = 0;
    
    @Column(columnDefinition = "TEXT")
    private String settings;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
