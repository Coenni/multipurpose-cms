package com.cms.controller;

import com.cms.model.entity.Media;
import com.cms.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MediaController {
    
    private final MediaService mediaService;
    
    @GetMapping
    public ResponseEntity<Page<Media>> getAllMedia(
            @RequestParam Long siteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(mediaService.getAllMedia(siteId, pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Media> getMedia(@PathVariable Long id) {
        return ResponseEntity.ok(mediaService.getMediaById(id));
    }
    
    @PostMapping("/upload")
    public ResponseEntity<Media> uploadMedia(
            @RequestParam Long siteId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) Long uploadedBy) {
        try {
            Media media = mediaService.uploadMedia(siteId, file, uploadedBy);
            return ResponseEntity.status(HttpStatus.CREATED).body(media);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }
}
