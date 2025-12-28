package com.cms.exception;

public class PluginNotFoundException extends RuntimeException {
    
    public PluginNotFoundException(String slug) {
        super("Plugin not found with slug: " + slug);
    }
    
    public PluginNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
