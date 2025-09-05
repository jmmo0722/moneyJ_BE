package com.project.moneyj.codef.service;

import com.project.moneyj.codef.config.CodefProperties;
import com.project.moneyj.codef.dto.AccountAddRequest;
import com.project.moneyj.codef.dto.AccountCreateRequest;
import com.project.moneyj.codef.repository.CodefConnectedIdRepository;
import com.project.moneyj.codef.util.RsaEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodefCredentialService {

    private final WebClient codefWebClient;
    private final CodefProperties props;
    private final CodefAuthService authService;
    private final CodefConnectedIdRepository cidRepo;

    // 2-1) 계정(자격) 추가
    @Transactional
    public String addCredential(Long userId, AccountCreateRequest.AccountInput input) throws Exception {
        var cid = cidRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Connected ID 없음")).getConnectedId();

        // 암호화
        if ("1".equals(input.getLoginType()) && input.getPassword()!=null) {
            input.setPassword(RsaEncryptor.encryptWithPemPublicKey(input.getPassword(), props.getPublicKey()));
        }

        var req = AccountAddRequest.builder()
                .connectedId(cid)
                .accountList(List.of(input))
                .build();

        String token = authService.getValidAccessToken();
        String url = props.getBaseUrl() + "/v1/account/add";

        String raw = codefWebClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBearerAuth(token))
                .bodyValue(req)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("account/add raw={}", raw);
        // 간단 검증만(실서비스면 DTO로 매핑해서 코드 체크)
        return raw;
    }

    // 2-2) 계정(자격) 목록
    @Transactional(readOnly = true)
    public String listCredentials(Long userId) {
        var cid = cidRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Connected ID 없음")).getConnectedId();

        Map<String, Object> body = Map.of("connectedId", cid);
        String token = authService.getValidAccessToken();
        String url = props.getBaseUrl() + "/v1/account/list";

        String raw = codefWebClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBearerAuth(token))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("account/list raw={}", raw);
        return raw;
    }
}
