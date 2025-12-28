package com.cms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {
    
    @Value("${storage.type:local}")
    private String storageType;
    
    @Value("${storage.local.path:./storage}")
    private String localPath;
    
    @Value("${storage.s3.bucket:}")
    private String s3Bucket;
    
    @Value("${storage.s3.region:}")
    private String s3Region;
    
    public String getStorageType() {
        return storageType;
    }
    
    public String getLocalPath() {
        return localPath;
    }
    
    public String getS3Bucket() {
        return s3Bucket;
    }
    
    public String getS3Region() {
        return s3Region;
    }
}
