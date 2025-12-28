package com.cms.controller;

import com.cms.model.dto.ThemeDTO;
import com.cms.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ThemeController {
    
    private final ThemeService themeService;
    
    @GetMapping
    public ResponseEntity<List<ThemeDTO>> getAllThemes() {
        return ResponseEntity.ok(themeService.getAllThemes());
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity<ThemeDTO> getTheme(@PathVariable String slug) {
        return ResponseEntity.ok(themeService.getThemeBySlug(slug));
    }
    
    @GetMapping("/installed")
    public ResponseEntity<List<ThemeDTO>> getInstalledThemes() {
        return ResponseEntity.ok(themeService.getInstalledThemes());
    }
    
    @GetMapping("/active")
    public ResponseEntity<ThemeDTO> getActiveTheme() {
        ThemeDTO activeTheme = themeService.getActiveTheme();
        if (activeTheme != null) {
            return ResponseEntity.ok(activeTheme);
        }
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping
    public ResponseEntity<ThemeDTO> createTheme(@RequestBody ThemeDTO themeDTO) {
        ThemeDTO created = themeService.createOrUpdateTheme(themeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{slug}")
    public ResponseEntity<ThemeDTO> updateTheme(
            @PathVariable String slug, 
            @RequestBody ThemeDTO themeDTO) {
        themeDTO.setSlug(slug);
        ThemeDTO updated = themeService.createOrUpdateTheme(themeDTO);
        return ResponseEntity.ok(updated);
    }
    
    @PostMapping("/upload")
    public ResponseEntity<ThemeDTO> uploadTheme(@RequestParam("file") MultipartFile file) {
        try {
            ThemeDTO theme = themeService.uploadTheme(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(theme);
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }
    
    @PostMapping("/{slug}/install")
    public ResponseEntity<ThemeDTO> installTheme(@PathVariable String slug) {
        ThemeDTO installed = themeService.installTheme(slug);
        return ResponseEntity.ok(installed);
    }
    
    @PutMapping("/{slug}/activate")
    public ResponseEntity<ThemeDTO> activateTheme(@PathVariable String slug) {
        ThemeDTO activated = themeService.activateTheme(slug);
        return ResponseEntity.ok(activated);
    }
    
    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteTheme(@PathVariable String slug) {
        themeService.deleteTheme(slug);
        return ResponseEntity.noContent().build();
    }
}
