package com.example.SocialNetWork.models.aboutUser;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    User;
    @Override
    public String getAuthority() {
        return name();
    }
}
