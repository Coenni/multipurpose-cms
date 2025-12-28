package com.cms.service;

import com.cms.exception.PluginNotFoundException;
import com.cms.model.dto.PluginDTO;
import com.cms.model.entity.Plugin;
import com.cms.repository.PluginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PluginService {
    
    private final PluginRepository pluginRepository;
    
    public List<PluginDTO> getAllPlugins() {
        return pluginRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public PluginDTO getPluginBySlug(String slug) {
        Plugin plugin = pluginRepository.findBySlug(slug)
                .orElseThrow(() -> new PluginNotFoundException(slug));
        return convertToDTO(plugin);
    }
    
    public List<PluginDTO> getActivePlugins() {
        return pluginRepository.findByIsActive(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<PluginDTO> getPluginsByCategory(String category) {
        return pluginRepository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PluginDTO createOrUpdatePlugin(PluginDTO pluginDTO) {
        Plugin plugin = convertToEntity(pluginDTO);
        Plugin savedPlugin = pluginRepository.save(plugin);
        log.info("Created/Updated plugin: {}", savedPlugin.getSlug());
        return convertToDTO(savedPlugin);
    }
    
    @Transactional
    public PluginDTO installPlugin(String slug) {
        Plugin plugin = pluginRepository.findBySlug(slug)
                .orElseThrow(() -> new PluginNotFoundException(slug));
        
        plugin.setIsInstalled(true);
        plugin.setInstalledAt(LocalDateTime.now());
        Plugin savedPlugin = pluginRepository.save(plugin);
        log.info("Installed plugin: {}", slug);
        return convertToDTO(savedPlugin);
    }
    
    @Transactional
    public PluginDTO togglePluginActivation(String slug) {
        Plugin plugin = pluginRepository.findBySlug(slug)
                .orElseThrow(() -> new PluginNotFoundException(slug));
        
        if (!plugin.getIsInstalled()) {
            throw new IllegalStateException("Plugin must be installed before activation");
        }
        
        plugin.setIsActive(!plugin.getIsActive());
        Plugin savedPlugin = pluginRepository.save(plugin);
        log.info("Toggled plugin activation: {} - now {}", slug, savedPlugin.getIsActive());
        return convertToDTO(savedPlugin);
    }
    
    @Transactional
    public void deletePlugin(String slug) {
        Plugin plugin = pluginRepository.findBySlug(slug)
                .orElseThrow(() -> new PluginNotFoundException(slug));
        
        pluginRepository.delete(plugin);
        log.info("Deleted plugin: {}", slug);
    }
    
    private PluginDTO convertToDTO(Plugin plugin) {
        return new PluginDTO(
                plugin.getSlug(),
                plugin.getName(),
                plugin.getVersion(),
                plugin.getAuthor(),
                plugin.getCategory(),
                plugin.getDescription(),
                plugin.getConfigJson(),
                plugin.getIsActive(),
                plugin.getIsInstalled(),
                plugin.getInstalledAt()
        );
    }
    
    private Plugin convertToEntity(PluginDTO dto) {
        return new Plugin(
                dto.getSlug(),
                dto.getName(),
                dto.getVersion(),
                dto.getAuthor(),
                dto.getCategory(),
                dto.getDescription(),
                dto.getConfigJson(),
                dto.getIsActive() != null ? dto.getIsActive() : false,
                dto.getIsInstalled() != null ? dto.getIsInstalled() : false,
                dto.getInstalledAt()
        );
    }
}
