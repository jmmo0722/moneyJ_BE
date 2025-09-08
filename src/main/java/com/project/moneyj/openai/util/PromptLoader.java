package com.project.moneyj.openai.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class PromptLoader {
    private PromptLoader(){}

    public static String load(final String path) {
        try (InputStream inputStream = PromptLoader.class.getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("리소스 경로 읽기 실패: " + path);
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            throw new RuntimeException("프롬프트 파일 읽기 실패: " + path, e);
        }
    }
}
