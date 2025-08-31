package com.project.moneyj.openai.service;

import com.project.moneyj.openai.dto.TravelRequestDTO;
import com.project.moneyj.openai.dto.TravelResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TravelService {

    private final ChatClient chatClient;

    /**
     * 여행 경비 계산 관련 Prompt
     */
    public TravelResponseDTO getTravelBudget(TravelRequestDTO request) {

        String promptText = String.format(
                "여행지: %s, 기간: %d일, 출발일: %s, 도착일: %s "
                        + "이 여행의 예상 경비를 JSON 형식으로 반환해. "
                        + "필드는 flightCost, accommodationCost, foodCost, otherCost, totalCost 이어야 한다."
                        + "flightCost 에서 출발지는 인천 국제 공항을 기준으로 계산"
                        + "flightCost 는 실제 인천 국제 공항에서 여행지의 대표 공항을 기준으로 왕복 항공권 예상비용 (원 단위, 정수 숫자만)"
                        + "단, 항공권은 이코노미 클래스, 저가항공 평균가를 기준으로 하며 성수기 요금을 피한 일반적인 경우로 추정해라"
                        + "accommodationCost 는 실제 해당 기간 동안의 중급(3성급) 호텔 기준 예상 비용 (원 단위, 정수 숫자만)"
                        + "foodCost 는 실제 해당 기간 동안의 해당 여행지에서의 (아침, 점심, 저녁) 해당 여행지의 환율을 고려한 식비 예상 비용 (원 단위, 정수 숫자만)"
                        + "otherCost 는 실제 해당 기간 동안의 예상되는 대표 관광지 입장료, 교통비, 기념품 등의 비용 (원 단위, 정수 숫자만)"
                        + "totalCost 는 flightCost + accommodationCost + foodCost + otherCost 의 합산 값 (원 단위, 정수 숫자만)"
                        + "반드시 JSON 형식으로 출력하고, 다른 설명 문장은 포함하지 말 것",
                request.getDestination(),
                request.getDays(),
                request.getStartDate(),
                request.getEndDate()
        );

        return chatClient
                .prompt()
                .system("너는 여행 경비 분석가야. 반드시 JSON으로만 답변해야 한다.")
                .user(promptText)
                .call()
                .entity(TravelResponseDTO.class);
    }
}
