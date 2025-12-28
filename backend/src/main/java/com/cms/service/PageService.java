package com.cms.service;

import com.cms.exception.PageNotFoundException;
import com.cms.model.dto.PageDTO;
import com.cms.model.dto.SEOMetadataDTO;
import com.cms.model.entity.*;
import com.cms.repository.PageRepository;
import com.cms.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageService {
    
    private final PageRepository pageRepository;
    private final SiteRepository siteRepository;
    
    public List<PageDTO> getAllPages(Long siteId) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found"));
        
        return pageRepository.findBySite(site).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<PageDTO> getPublishedPages(Long siteId) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found"));
        
        return pageRepository.findBySiteAndIsPublished(site, true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public PageDTO getPageById(Long id) {
        BasePage page = pageRepository.findById(id)
                .orElseThrow(() -> new PageNotFoundException(id));
        return convertToDTO(page);
    }
    
    public PageDTO getPageBySlug(Long siteId, String slug) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found"));
        
        BasePage page = pageRepository.findBySiteAndSlug(site, slug)
                .orElseThrow(() -> new PageNotFoundException("Page not found with slug: " + slug));
        return convertToDTO(page);
    }
    
    @Transactional
    public PageDTO createPage(PageDTO pageDTO) {
        Site site = siteRepository.findById(pageDTO.getSiteId())
                .orElseThrow(() -> new RuntimeException("Site not found"));
        
        // Create appropriate page type based on pageType
        BasePage page = createPageByType(pageDTO, site);
        BasePage savedPage = pageRepository.save(page);
        log.info("Created page: {} - {}", savedPage.getId(), savedPage.getTitle());
        return convertToDTO(savedPage);
    }
    
    @Transactional
    public PageDTO updatePage(Long id, PageDTO pageDTO) {
        BasePage existingPage = pageRepository.findById(id)
                .orElseThrow(() -> new PageNotFoundException(id));
        
        updatePageFields(existingPage, pageDTO);
        BasePage savedPage = pageRepository.save(existingPage);
        log.info("Updated page: {} - {}", savedPage.getId(), savedPage.getTitle());
        return convertToDTO(savedPage);
    }
    
    @Transactional
    public void deletePage(Long id) {
        BasePage page = pageRepository.findById(id)
                .orElseThrow(() -> new PageNotFoundException(id));
        pageRepository.delete(page);
        log.info("Deleted page: {}", id);
    }
    
    private BasePage createPageByType(PageDTO dto, Site site) {
        BasePage page;
        String pageType = dto.getPageType() != null ? dto.getPageType() : "HOME";
        
        switch (pageType) {
            case "BLOG":
                page = new BlogPage();
                break;
            case "HOME":
            default:
                page = new HomePage();
                break;
        }
        
        page.setSite(site);
        updatePageFields(page, dto);
        return page;
    }
    
    private void updatePageFields(BasePage page, PageDTO dto) {
        page.setSlug(dto.getSlug());
        page.setTitle(dto.getTitle());
        page.setContent(dto.getContent());
        page.setLayout(dto.getLayout());
        page.setIsPublished(dto.getIsPublished());
        
        if (dto.getSeo() != null) {
            SEOMetadata seo = new SEOMetadata();
            seo.setMetaTitle(dto.getSeo().getMetaTitle());
            seo.setMetaDescription(dto.getSeo().getMetaDescription());
            seo.setMetaKeywords(dto.getSeo().getMetaKeywords());
            seo.setOgImage(dto.getSeo().getOgImage());
            seo.setCanonicalUrl(dto.getSeo().getCanonicalUrl());
            seo.setSchemaJson(dto.getSeo().getSchemaJson());
            page.setSeo(seo);
        }
    }
    
    private PageDTO convertToDTO(BasePage page) {
        SEOMetadataDTO seoDTO = null;
        if (page.getSeo() != null) {
            seoDTO = new SEOMetadataDTO(
                    page.getSeo().getMetaTitle(),
                    page.getSeo().getMetaDescription(),
                    page.getSeo().getMetaKeywords(),
                    page.getSeo().getOgImage(),
                    page.getSeo().getCanonicalUrl(),
                    page.getSeo().getSchemaJson()
            );
        }
        
        String pageType = "HOME";
        if (page instanceof BlogPage) {
            pageType = "BLOG";
        }
        
        return new PageDTO(
                page.getId(),
                pageType,
                page.getSite() != null ? page.getSite().getId() : null,
                page.getSlug(),
                page.getTitle(),
                page.getContent(),
                page.getLayout(),
                page.getIsPublished(),
                seoDTO,
                page.getCreatedAt(),
                page.getUpdatedAt()
        );
    }
}
