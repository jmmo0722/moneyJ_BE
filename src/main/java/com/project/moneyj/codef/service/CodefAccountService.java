package com.project.moneyj.codef.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.moneyj.codef.config.CodefProperties;
import com.project.moneyj.codef.domain.CodefConnectedId;
import com.project.moneyj.codef.dto.AccountCreateRequest;
import com.project.moneyj.codef.dto.AccountCreateResponse;
import com.project.moneyj.codef.repository.CodefConnectedIdRepository;
import com.project.moneyj.codef.util.RsaEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodefAccountService {

    private final WebClient codefWebClient;
    private final CodefProperties props;
    private final CodefAuthService codefAuthService; // 토큰 서비스 (이미 구현됨)
    private final CodefConnectedIdRepository connectedIdRepository;

    /**
     * 최초 계정 등록 → Connected ID 발급
     * @param userId  moneyJ 사용자 ID
     * @param input   은행 로그인 정보(아이디/비번)
     */
    @Transactional
    public String createConnectedId(Long userId, AccountCreateRequest.AccountInput input) {
        // 0) 이미 CID 있으면 그대로 반환(선호 전략)
        var existing = connectedIdRepository.findByUserId(userId);
        if (existing.isPresent()) return existing.get().getConnectedId();

        // 1) 비밀번호 RSA 암호화 (loginType=1일 때)
        if ("1".equals(input.getLoginType()) && input.getPassword() != null) {
            String enc = RsaEncryptor.encryptWithPemPublicKey(input.getPassword(), props.getPublicKey());
            input.setPassword(enc);
        }

        // 2) 요청 바디 구성
        var req = AccountCreateRequest.builder()
                .accountList(java.util.List.of(input))
                .build();

        // ★★★★★ WebClient로 보내기 직전에 로그 추가 ★★★★★
        try {
            log.info("Request Body to CODEF: {}", new ObjectMapper().writeValueAsString(req));
        } catch (Exception e) {
            log.error("Failed to serialize request body", e);
        }


        // 3) CODEF 호출
        String token = codefAuthService.getValidAccessToken();
        String url = props.getBaseUrl() + "/v1/account/create";

        // 1. 응답을 String으로 먼저 받음.
        String rawResponseBody = codefWebClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBearerAuth(token))
                .bodyValue(req)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // 2. 받은 응답을 로그로 남겨서 실제 어떤 데이터가 오는지 확인. (매우 중요)
        log.info("Raw response from CODEF: {}", rawResponseBody);

        AccountCreateResponse res;
        try {
            // 3. URL 인코딩된 문자열을 디코딩합니다.
            String decodedBody = URLDecoder.decode(rawResponseBody, StandardCharsets.UTF_8);
            log.info("Decoded response body: {}", decodedBody); // 디코딩된 JSON 문자열 확인

            // 4. 깨끗한 JSON 문자열을 객체로 변환합니다.
            res = new ObjectMapper().readValue(decodedBody, AccountCreateResponse.class);

        } catch (Exception e) {
            log.error("Failed to process CODEF response. Raw body: {}", rawResponseBody, e);
            throw new IllegalStateException("CODEF 응답 처리 중 오류가 발생했습니다.", e);
        }

        if (res == null || res.getResult() == null) {
            throw new IllegalStateException("CODEF 응답이 비어있습니다.");
        }

        String code = res.getResult().getCode();
        String connectedId = res.getConnectedIdSafe();

        log.info("account/create result={}, txId={}, connectedId={}",
                code, res.getResult().getTransactionId(), connectedId);

        // 2-way 인증 요구(CF-03002 등)는 프론트로 그대로 전달하고 종료
        if (!"CF-00000".equals(code)) {
            // 필요 시 세부 처리 로직 추가 (2Way OTP 등)
            throw new IllegalStateException("CODEF 계정 등록 실패: " + code + " / " + res.getResult().getMessage());
        }

        if (connectedId == null) {
            throw new IllegalStateException("Connected ID 미수신");
        }

        // 4) DB 저장
        connectedIdRepository.save(
                CodefConnectedId.builder()
                        .userId(userId)
                        .connectedId(connectedId)
                        .status("ACTIVE")
                        .build()
        );

        return connectedId;
    }
}
