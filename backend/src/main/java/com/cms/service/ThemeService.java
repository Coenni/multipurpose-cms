package com.cms.service;

import com.cms.exception.ThemeNotFoundException;
import com.cms.model.dto.ThemeDTO;
import com.cms.model.entity.Theme;
import com.cms.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThemeService {
    
    private final ThemeRepository themeRepository;
    
    public List<ThemeDTO> getAllThemes() {
        return themeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ThemeDTO getThemeBySlug(String slug) {
        Theme theme = themeRepository.findBySlug(slug)
                .orElseThrow(() -> new ThemeNotFoundException(slug));
        return convertToDTO(theme);
    }
    
    public List<ThemeDTO> getInstalledThemes() {
        return themeRepository.findByIsInstalled(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ThemeDTO getActiveTheme() {
        return themeRepository.findByIsActive(true)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    @Transactional
    public ThemeDTO createOrUpdateTheme(ThemeDTO themeDTO) {
        Theme theme = convertToEntity(themeDTO);
        Theme savedTheme = themeRepository.save(theme);
        log.info("Created/Updated theme: {}", savedTheme.getSlug());
        return convertToDTO(savedTheme);
    }
    
    @Transactional
    public ThemeDTO installTheme(String slug) {
        Theme theme = themeRepository.findBySlug(slug)
                .orElseThrow(() -> new ThemeNotFoundException(slug));
        
        theme.setIsInstalled(true);
        theme.setInstalledAt(LocalDateTime.now());
        Theme savedTheme = themeRepository.save(theme);
        log.info("Installed theme: {}", slug);
        return convertToDTO(savedTheme);
    }
    
    @Transactional
    public ThemeDTO activateTheme(String slug) {
        Theme theme = themeRepository.findBySlug(slug)
                .orElseThrow(() -> new ThemeNotFoundException(slug));
        
        if (!theme.getIsInstalled()) {
            throw new IllegalStateException("Theme must be installed before activation");
        }
        
        // Deactivate all other themes
        themeRepository.findByIsActive(true).ifPresent(activeTheme -> {
            activeTheme.setIsActive(false);
            themeRepository.save(activeTheme);
        });
        
        theme.setIsActive(true);
        Theme savedTheme = themeRepository.save(theme);
        log.info("Activated theme: {}", slug);
        return convertToDTO(savedTheme);
    }
    
    @Transactional
    public void deleteTheme(String slug) {
        Theme theme = themeRepository.findBySlug(slug)
                .orElseThrow(() -> new ThemeNotFoundException(slug));
        
        if (theme.getIsActive()) {
            throw new IllegalStateException("Cannot delete active theme");
        }
        
        themeRepository.delete(theme);
        log.info("Deleted theme: {}", slug);
    }
    
    public ThemeDTO uploadTheme(MultipartFile file) {
        // TODO: Implement theme ZIP upload and extraction logic
        // This would extract the ZIP, parse theme.json, and create Theme entity
        log.info("Theme upload functionality - to be implemented");
        throw new UnsupportedOperationException("Theme upload not yet implemented");
    }
    
    private ThemeDTO convertToDTO(Theme theme) {
        return new ThemeDTO(
                theme.getSlug(),
                theme.getName(),
                theme.getVersion(),
                theme.getAuthor(),
                theme.getDescription(),
                theme.getConfigJson(),
                theme.getScreenshotUrl(),
                theme.getDownloadUrl(),
                theme.getIsActive(),
                theme.getIsInstalled(),
                theme.getInstalledAt()
        );
    }
    
    private Theme convertToEntity(ThemeDTO dto) {
        return new Theme(
                dto.getSlug(),
                dto.getName(),
                dto.getVersion(),
                dto.getAuthor(),
                dto.getDescription(),
                dto.getConfigJson(),
                dto.getScreenshotUrl(),
                dto.getDownloadUrl(),
                dto.getIsActive() != null ? dto.getIsActive() : false,
                dto.getIsInstalled() != null ? dto.getIsInstalled() : false,
                dto.getInstalledAt()
        );
    }
}
