package com.cms.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SEOMetadata {
    
    @Column(name = "meta_title")
    private String metaTitle;
    
    @Column(name = "meta_description", columnDefinition = "TEXT")
    private String metaDescription;
    
    @Column(name = "meta_keywords")
    private String metaKeywords;
    
    @Column(name = "og_image")
    private String ogImage;
    
    @Column(name = "canonical_url")
    private String canonicalUrl;
    
    @Column(name = "schema_json", columnDefinition = "TEXT")
    private String schemaJson;
}
