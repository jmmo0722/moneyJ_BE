package com.project.moneyj.user.dto;

import com.project.moneyj.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponseDTO {
    private Long id;
    private String nickname;
    private String email;
    private String profileImage;

    public static UserResponseDTO of(User user) {
        return new UserResponseDTO(
            user.getUserId(),
            user.getNickname(),
            user.getEmail(),
            user.getProfileImage()
        );
    }
}
