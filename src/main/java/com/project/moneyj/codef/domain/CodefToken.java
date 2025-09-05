package com.project.moneyj.codef.domain;

import com.project.moneyj.codef.dto.TokenResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "codef_token")
public class CodefToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_token", nullable = false, length = 2048)
    private String accessToken;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    // 비즈니스 메소드
    public void getToken(TokenResponse tokenResponse){
        this.accessToken = tokenResponse.getAccessToken();
        this.expiresAt = LocalDateTime.now().plusSeconds(tokenResponse.getExpiresIn());
    }
}
