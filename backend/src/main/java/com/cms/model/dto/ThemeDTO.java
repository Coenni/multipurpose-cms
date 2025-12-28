package com.cms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDTO {
    private String slug;
    private String name;
    private String version;
    private String author;
    private String description;
    private String configJson;
    private String screenshotUrl;
    private String downloadUrl;
    private Boolean isActive;
    private Boolean isInstalled;
    private LocalDateTime installedAt;
}
