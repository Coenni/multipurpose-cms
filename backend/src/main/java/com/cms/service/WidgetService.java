package com.cms.service;

import com.cms.model.dto.WidgetInstanceDTO;
import com.cms.model.entity.Plugin;
import com.cms.model.entity.SiteThemeConfig;
import com.cms.model.entity.WidgetInstance;
import com.cms.repository.PluginRepository;
import com.cms.repository.SiteRepository;
import com.cms.repository.WidgetInstanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WidgetService {
    
    private final WidgetInstanceRepository widgetInstanceRepository;
    private final PluginRepository pluginRepository;
    private final SiteRepository siteRepository;
    
    public List<WidgetInstanceDTO> getWidgetsByArea(Long configId, String widgetAreaId) {
        SiteThemeConfig config = new SiteThemeConfig();
        config.setId(configId);
        
        return widgetInstanceRepository
                .findBySiteThemeConfigAndWidgetAreaIdOrderByPosition(config, widgetAreaId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<WidgetInstanceDTO> getAllWidgets(Long configId) {
        SiteThemeConfig config = new SiteThemeConfig();
        config.setId(configId);
        
        return widgetInstanceRepository.findBySiteThemeConfig(config).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public WidgetInstanceDTO createWidget(WidgetInstanceDTO dto) {
        WidgetInstance widget = new WidgetInstance();
        
        SiteThemeConfig config = new SiteThemeConfig();
        config.setId(dto.getSiteThemeConfigId());
        widget.setSiteThemeConfig(config);
        
        Plugin plugin = pluginRepository.findBySlug(dto.getPluginSlug())
                .orElseThrow(() -> new RuntimeException("Plugin not found"));
        widget.setPlugin(plugin);
        
        widget.setWidgetAreaId(dto.getWidgetAreaId());
        widget.setPosition(dto.getPosition() != null ? dto.getPosition() : 0);
        widget.setSettings(dto.getSettings());
        widget.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        
        WidgetInstance savedWidget = widgetInstanceRepository.save(widget);
        log.info("Created widget instance: {}", savedWidget.getId());
        return convertToDTO(savedWidget);
    }
    
    @Transactional
    public WidgetInstanceDTO updateWidget(Long id, WidgetInstanceDTO dto) {
        WidgetInstance widget = widgetInstanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Widget instance not found"));
        
        widget.setWidgetAreaId(dto.getWidgetAreaId());
        widget.setPosition(dto.getPosition());
        widget.setSettings(dto.getSettings());
        widget.setIsActive(dto.getIsActive());
        
        WidgetInstance savedWidget = widgetInstanceRepository.save(widget);
        log.info("Updated widget instance: {}", savedWidget.getId());
        return convertToDTO(savedWidget);
    }
    
    @Transactional
    public void deleteWidget(Long id) {
        WidgetInstance widget = widgetInstanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Widget instance not found"));
        widgetInstanceRepository.delete(widget);
        log.info("Deleted widget instance: {}", id);
    }
    
    private WidgetInstanceDTO convertToDTO(WidgetInstance widget) {
        return new WidgetInstanceDTO(
                widget.getId(),
                widget.getSiteThemeConfig() != null ? widget.getSiteThemeConfig().getId() : null,
                widget.getPlugin() != null ? widget.getPlugin().getSlug() : null,
                widget.getWidgetAreaId(),
                widget.getPosition(),
                widget.getSettings(),
                widget.getIsActive(),
                widget.getCreatedAt(),
                widget.getUpdatedAt()
        );
    }
}
