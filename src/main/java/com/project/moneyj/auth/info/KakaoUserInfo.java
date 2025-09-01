package com.project.moneyj.auth.info;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getEmail() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        return (String) account.get("email");
    }

    @Override
    public String getNickname() {
        Map<String, Object> props = (Map<String, Object>) attributes.get("properties");
        return (String) props.get("nickname");
    }

    @Override
    public String getProfileImage() {
        Map<String, Object> props = (Map<String, Object>) attributes.get("properties");
        return (String) props.get("profile_image");
    }
}