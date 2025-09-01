package com.project.moneyj.auth.util;

import com.project.moneyj.auth.dto.CustomOAuth2User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor
public class SecurityUtil {

    public static Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        return ((CustomOAuth2User) auth.getPrincipal()).getUserId();
    }

}
