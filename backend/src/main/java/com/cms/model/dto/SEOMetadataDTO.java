package com.cms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SEOMetadataDTO {
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
    private String ogImage;
    private String canonicalUrl;
    private String schemaJson;
}
