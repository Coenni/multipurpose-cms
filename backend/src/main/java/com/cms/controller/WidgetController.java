package com.cms.controller;

import com.cms.model.dto.WidgetInstanceDTO;
import com.cms.service.WidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/widgets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WidgetController {
    
    private final WidgetService widgetService;
    
    @GetMapping("/instances")
    public ResponseEntity<List<WidgetInstanceDTO>> getAllWidgets(@RequestParam Long configId) {
        return ResponseEntity.ok(widgetService.getAllWidgets(configId));
    }
    
    @GetMapping("/areas/{areaId}")
    public ResponseEntity<List<WidgetInstanceDTO>> getWidgetsByArea(
            @PathVariable String areaId,
            @RequestParam Long configId) {
        return ResponseEntity.ok(widgetService.getWidgetsByArea(configId, areaId));
    }
    
    @PostMapping("/instances")
    public ResponseEntity<WidgetInstanceDTO> createWidget(@RequestBody WidgetInstanceDTO dto) {
        WidgetInstanceDTO created = widgetService.createWidget(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/instances/{id}")
    public ResponseEntity<WidgetInstanceDTO> updateWidget(
            @PathVariable Long id,
            @RequestBody WidgetInstanceDTO dto) {
        WidgetInstanceDTO updated = widgetService.updateWidget(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/instances/{id}")
    public ResponseEntity<Void> deleteWidget(@PathVariable Long id) {
        widgetService.deleteWidget(id);
        return ResponseEntity.noContent().build();
    }
}
