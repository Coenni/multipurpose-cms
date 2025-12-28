package com.cms.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "media")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;
    
    @Column(nullable = false, length = 500)
    private String filename;
    
    @Column(name = "file_path", nullable = false, length = 1000)
    private String filePath;
    
    @Column(name = "file_url", nullable = false, length = 1000)
    private String fileUrl;
    
    @Column(name = "mime_type", length = 100)
    private String mimeType;
    
    @Column(name = "file_size")
    private Long fileSize;
    
    @Column(name = "alt_text", length = 500)
    private String altText;
    
    @Column(name = "uploaded_by")
    private Long uploadedBy;
    
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
    
    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
    }
}
