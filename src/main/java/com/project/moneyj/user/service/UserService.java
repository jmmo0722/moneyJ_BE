package com.project.moneyj.user.service;

import com.project.moneyj.auth.util.SecurityUtil;
import com.project.moneyj.user.domain.User;
import com.project.moneyj.user.dto.UserCheckRequestDTO;
import com.project.moneyj.user.dto.UserCheckResponseDTO;
import com.project.moneyj.user.dto.UserResponseDTO;
import com.project.moneyj.user.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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

    public List<UserCheckResponseDTO> existsByEmail(UserCheckRequestDTO request) {
        List<String> emails = request.getEmails();

        // DB에서 존재하는 유저 조회
        List<User> existingUsers = userRepository.findAllByEmailIn(emails);

        // Map<email, User> 생성
        Map<String, User> emailToUser = existingUsers.stream()
            .collect(Collectors.toMap(User::getEmail, Function.identity()));

        // 입력 이메일 순서대로 DTO 생성, 존재하지 않으면 null 채우기
        return emails.stream()
            .map(email -> {
                User user = emailToUser.get(email);
                return user != null
                    ? UserCheckResponseDTO.of(true, user)
                    : UserCheckResponseDTO.of(false, email);
            })
            .toList();
    }

}
