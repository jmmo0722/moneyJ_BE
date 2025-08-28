package com.project.moneyj.user.service;

import com.project.moneyj.auth.util.SecurityUtil;
import com.project.moneyj.user.domain.User;
import com.project.moneyj.user.dto.UserResponse;
import com.project.moneyj.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse getUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("로그인 필요");
        }

        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return UserResponse.of(user);
    }
}
