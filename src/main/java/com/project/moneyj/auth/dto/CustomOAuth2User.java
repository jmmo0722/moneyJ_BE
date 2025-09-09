package com.project.moneyj.auth.dto;

import com.project.moneyj.user.domain.User;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {
    private final User user;
    private final Map<String, Object> attributes;
    private final boolean isFirstLogin;

    public CustomOAuth2User(User user, Map<String, Object> attributes, boolean isFirstLogin) {
        this.user = user;
        this.attributes = attributes;
        this.isFirstLogin = isFirstLogin;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getName() {
        return user.getUserId().toString();
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getNickname() {
        return user.getNickname();
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

}
