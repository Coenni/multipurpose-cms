package com.cms.controller;

import com.cms.model.dto.PluginDTO;
import com.cms.service.PluginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plugins")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PluginController {
    
    private final PluginService pluginService;
    
    @GetMapping
    public ResponseEntity<List<PluginDTO>> getAllPlugins() {
        return ResponseEntity.ok(pluginService.getAllPlugins());
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity<PluginDTO> getPlugin(@PathVariable String slug) {
        return ResponseEntity.ok(pluginService.getPluginBySlug(slug));
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<PluginDTO>> getActivePlugins() {
        return ResponseEntity.ok(pluginService.getActivePlugins());
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<PluginDTO>> getPluginsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(pluginService.getPluginsByCategory(category));
    }
    
    @PostMapping
    public ResponseEntity<PluginDTO> createPlugin(@RequestBody PluginDTO pluginDTO) {
        PluginDTO created = pluginService.createOrUpdatePlugin(pluginDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{slug}")
    public ResponseEntity<PluginDTO> updatePlugin(
            @PathVariable String slug, 
            @RequestBody PluginDTO pluginDTO) {
        pluginDTO.setSlug(slug);
        PluginDTO updated = pluginService.createOrUpdatePlugin(pluginDTO);
        return ResponseEntity.ok(updated);
    }
    
    @PostMapping("/{slug}/install")
    public ResponseEntity<PluginDTO> installPlugin(@PathVariable String slug) {
        PluginDTO installed = pluginService.installPlugin(slug);
        return ResponseEntity.ok(installed);
    }
    
    @PutMapping("/{slug}/activate")
    public ResponseEntity<PluginDTO> toggleActivation(@PathVariable String slug) {
        PluginDTO toggled = pluginService.togglePluginActivation(slug);
        return ResponseEntity.ok(toggled);
    }
    
    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deletePlugin(@PathVariable String slug) {
        pluginService.deletePlugin(slug);
        return ResponseEntity.noContent().build();
    }
}
