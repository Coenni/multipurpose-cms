package com.cms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
    private Long id;
    private String pageType;
    private Long siteId;
    private String slug;
    private String title;
    private String content;
    private String layout;
    private Boolean isPublished;
    private SEOMetadataDTO seo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
