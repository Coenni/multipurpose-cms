package com.cms.controller;

import com.cms.model.dto.PageDTO;
import com.cms.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PageController {
    
    private final PageService pageService;
    
    @GetMapping
    public ResponseEntity<List<PageDTO>> getAllPages(@RequestParam Long siteId) {
        return ResponseEntity.ok(pageService.getAllPages(siteId));
    }
    
    @GetMapping("/published")
    public ResponseEntity<List<PageDTO>> getPublishedPages(@RequestParam Long siteId) {
        return ResponseEntity.ok(pageService.getPublishedPages(siteId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PageDTO> getPage(@PathVariable Long id) {
        return ResponseEntity.ok(pageService.getPageById(id));
    }
    
    @GetMapping("/slug/{slug}")
    public ResponseEntity<PageDTO> getPageBySlug(
            @PathVariable String slug,
            @RequestParam Long siteId) {
        return ResponseEntity.ok(pageService.getPageBySlug(siteId, slug));
    }
    
    @PostMapping
    public ResponseEntity<PageDTO> createPage(@RequestBody PageDTO pageDTO) {
        PageDTO created = pageService.createPage(pageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PageDTO> updatePage(
            @PathVariable Long id,
            @RequestBody PageDTO pageDTO) {
        PageDTO updated = pageService.updatePage(id, pageDTO);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePage(@PathVariable Long id) {
        pageService.deletePage(id);
        return ResponseEntity.noContent().build();
    }
}
