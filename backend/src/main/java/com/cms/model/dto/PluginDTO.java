package com.cms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PluginDTO {
    private String slug;
    private String name;
    private String version;
    private String author;
    private String category;
    private String description;
    private String configJson;
    private Boolean isActive;
    private Boolean isInstalled;
    private LocalDateTime installedAt;
}
