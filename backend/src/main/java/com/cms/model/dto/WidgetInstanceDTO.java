package com.cms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WidgetInstanceDTO {
    private Long id;
    private Long siteThemeConfigId;
    private String pluginSlug;
    private String widgetAreaId;
    private Integer position;
    private String settings;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
