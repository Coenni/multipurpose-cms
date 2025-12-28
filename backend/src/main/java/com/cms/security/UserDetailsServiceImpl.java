package com.cms.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: Implement actual user loading from database
        // For now, return a placeholder user
        if ("admin".equals(username)) {
            return new User("admin", "admin123", new ArrayList<>());
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
