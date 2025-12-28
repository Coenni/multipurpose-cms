package com.cms.service;

import com.cms.model.entity.Media;
import com.cms.model.entity.Site;
import com.cms.repository.MediaRepository;
import com.cms.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaService {
    
    private final MediaRepository mediaRepository;
    private final SiteRepository siteRepository;
    
    @Value("${storage.local.path:./storage}")
    private String storagePath;
    
    public Page<Media> getAllMedia(Long siteId, Pageable pageable) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found"));
        return mediaRepository.findBySite(site, pageable);
    }
    
    public Media getMediaById(Long id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));
    }
    
    @Transactional
    public Media uploadMedia(Long siteId, MultipartFile file, Long uploadedBy) throws IOException {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found"));
        
        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".") 
                ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                : "";
        String filename = UUID.randomUUID().toString() + extension;
        
        // Create storage directory if it doesn't exist
        Path uploadPath = Paths.get(storagePath, "site-" + siteId);
        Files.createDirectories(uploadPath);
        
        // Save file
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        // Create media entity
        Media media = new Media();
        media.setSite(site);
        media.setFilename(originalFilename);
        media.setFilePath(filePath.toString());
        media.setFileUrl("/uploads/site-" + siteId + "/" + filename);
        media.setMimeType(file.getContentType());
        media.setFileSize(file.getSize());
        media.setUploadedBy(uploadedBy);
        
        Media savedMedia = mediaRepository.save(media);
        log.info("Uploaded media: {} - {}", savedMedia.getId(), savedMedia.getFilename());
        return savedMedia;
    }
    
    @Transactional
    public void deleteMedia(Long id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));
        
        // Delete physical file
        try {
            Path filePath = Paths.get(media.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("Failed to delete media file: {}", media.getFilePath(), e);
        }
        
        mediaRepository.delete(media);
        log.info("Deleted media: {}", id);
    }
}
