package com.project.moneyj.codef.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardCredentialAddRequestDTO {

    private String organization;   // 카드사 기관코드 (예: 0300 계열 등, 기관코드는 CODEF 문서 참조)
    private String loginType;      // "1" = 아이디/비번
    private String id;             // 카드사 로그인 아이디
    private String password;       // 평문 비밀번호 (Controller에서 RSA 암호화 처리)
    private String birthDate;      // (선택) YYYYMMDD - 일부 카드사 필수
}
