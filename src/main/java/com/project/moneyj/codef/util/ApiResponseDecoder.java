package com.project.moneyj.codef.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;


public class ApiResponseDecoder {

    // ObjectMapper는 생성 비용이 비싸므로 static으로 만들어 재사용하는 것이 좋습니다.
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * URL 인코딩된 JSON 응답 문자열을 디코딩하고 Map<String, Object> 형태로 변환합니다.
     *
     * @param encodedResponse API로부터 받은 원본 응답 문자열
     * @return 파싱된 데이터가 담긴 Map 객체. 실패 시 빈 Map 반환.
     */
    public static Map<String, Object> decode(String encodedResponse) {
        if (encodedResponse == null || encodedResponse.isEmpty()) {
            return Collections.emptyMap();
        }

        try {
            // 1. URL 디코딩 (UTF-8 인코딩 사용)
            String decodedJson = URLDecoder.decode(encodedResponse, StandardCharsets.UTF_8.name());

            // 2. JSON 문자열을 Map<String, Object>로 파싱
            // TypeReference를 사용하면 중첩된 JSON 구조도 Map으로 깔끔하게 변환할 수 있습니다.
            return objectMapper.readValue(decodedJson, new TypeReference<Map<String, Object>>() {});

        } catch (Exception e) {
            // 실제 프로덕션 코드에서는 로깅 라이브러리(slf4j 등)를 사용해 에러를 기록하는 것이 좋습니다.
            System.err.println("API 응답 디코딩 또는 파싱 실패: " + e.getMessage());
            return Collections.emptyMap(); // 에러 발생 시 빈 Map 반환
        }
    }

}
