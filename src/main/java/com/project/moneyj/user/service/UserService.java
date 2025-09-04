package com.project.moneyj.user.service;

import com.project.moneyj.auth.util.SecurityUtil;
import com.project.moneyj.user.domain.User;
import com.project.moneyj.user.dto.UserCheckResponseDTO;
import com.project.moneyj.user.dto.UserResponseDTO;
import com.project.moneyj.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDTO getUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("로그인 필요");
        }

        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return UserResponseDTO.of(user);
    }

    public UserCheckResponseDTO existsByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(user -> UserCheckResponseDTO.of(true, user))
            .orElse(UserCheckResponseDTO.of(false, null));
    }

}
