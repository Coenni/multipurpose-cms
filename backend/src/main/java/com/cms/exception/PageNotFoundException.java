package com.cms.exception;

public class PageNotFoundException extends RuntimeException {
    
    public PageNotFoundException(Long id) {
        super("Page not found with id: " + id);
    }
    
    public PageNotFoundException(String message) {
        super(message);
    }
}
