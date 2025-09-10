package com.project.moneyj.codef.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * CODEF 에서 발급받은 key 또는 기본 경로 필드 저장
 * 데모 버전 호출 100번 제한으로 인해 application.yml 에 각자의 key 로 변경 바람.
 */
@Data
@Component
public class CodefProperties {

    @Value("${codef.api-base-url}")
    private String baseUrl;

    @Value("${codef.public-key}")
    private String publicKey;

    @Value("${codef.client-id}")
    private String clientId;

    @Value("${codef.client-secret}")
    private String clientSecret;

    @Value("${codef.refresh-margin-sec:60}")
    private int refreshMarginSec; // 60초

}
