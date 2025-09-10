package com.project.moneyj.codef.domain;

import com.project.moneyj.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "codef_institution")
public class CodefInstitution {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codef_institution_id", nullable = false)
    private Long codefInstitutionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codef_connected_id")
    private CodefConnectedId codefConnectedId;

    @Column(name = "connected_id", length = 100)
    private String connectedId;

    @Column(name = "organization", length = 20)
    private String organization;

    @Column(name = "login_type", length = 10)
    private String loginType;

    @Column(name = "login_id_masked", length = 100)
    private String loginIdMasked;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "last_verified_at")
    private LocalDateTime lastVerifiedAt;

    // 최근 결과 코드
    @Column(name = "last_result_code", length = 20)
    private String lastResultCode;

    // 최근 결과 메시지
    @Column(name = "last_result_msg", length = 255)
    private String lastResultMsg;

    // 생성 일시
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 수정 일시
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void updateConnectionStatus(String loginType, String status, String lastResultCode, String lastResultMsg, String loginIdMasked) {
        this.loginType = loginType;
        this.status = status;
        this.lastResultCode = lastResultCode;
        this.lastResultMsg = lastResultMsg;
        this.lastVerifiedAt = LocalDateTime.now();
        this.loginIdMasked = loginIdMasked;
    }
}
