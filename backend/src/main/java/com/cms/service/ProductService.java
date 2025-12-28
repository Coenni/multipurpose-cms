package com.cms.service;

import com.cms.model.entity.Product;
import com.cms.model.entity.Site;
import com.cms.repository.ProductRepository;
import com.cms.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    
    private final ProductRepository productRepository;
    private final SiteRepository siteRepository;
    
    public Page<Product> getAllProducts(Long siteId, Pageable pageable) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found"));
        return productRepository.findBySite(site, pageable);
    }
    
    public Page<Product> getPublishedProducts(Long siteId, Pageable pageable) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found"));
        return productRepository.findBySiteAndIsPublished(site, true, pageable);
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    
    @Transactional
    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        log.info("Created product: {} - {}", savedProduct.getId(), savedProduct.getName());
        return savedProduct;
    }
    
    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setSlug(updatedProduct.getSlug());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setSalePrice(updatedProduct.getSalePrice());
        existingProduct.setSku(updatedProduct.getSku());
        existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
        existingProduct.setImages(updatedProduct.getImages());
        existingProduct.setIsPublished(updatedProduct.getIsPublished());
        
        Product savedProduct = productRepository.save(existingProduct);
        log.info("Updated product: {} - {}", savedProduct.getId(), savedProduct.getName());
        return savedProduct;
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
        log.info("Deleted product: {}", id);
    }
}
