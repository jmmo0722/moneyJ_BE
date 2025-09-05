package com.project.moneyj.codef.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequest {

    private List<AccountInput> accountList;

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AccountInput {
        private String countryCode;   // "KR"
        private String businessType;  // "BK" (은행)
        private String clientType;    // "P" (개인) / "B" (기업)
        private String organization;  // 예: "0004" (KB)
        private String loginType;     // "1"=아이디/비밀번호, "0"=인증서
        private String id;            // 은행 로그인 ID
        private String password;      // RSA로 암호화된 비밀번호
        // (인증서 방식이면 der/key/password 추가 필드가 들어감)
    }
}
