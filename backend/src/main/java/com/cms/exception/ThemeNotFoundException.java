package com.cms.exception;

public class ThemeNotFoundException extends RuntimeException {
    
    public ThemeNotFoundException(String slug) {
        super("Theme not found with slug: " + slug);
    }
    
    public ThemeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
