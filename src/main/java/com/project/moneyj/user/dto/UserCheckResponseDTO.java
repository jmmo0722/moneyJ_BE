package com.project.moneyj.user.dto;

import com.project.moneyj.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserCheckResponseDTO {
    private boolean exists;
    private String email;
    private String nickname;

    public static UserCheckResponseDTO of(boolean exists, User user) {
        return new UserCheckResponseDTO(
            exists,
            user != null ? user.getEmail() : null,
            user != null ? user.getNickname() : null
        );
    }

    public static UserCheckResponseDTO of(boolean exists, String email) {
        return new UserCheckResponseDTO(exists, email, null);
    }

}
