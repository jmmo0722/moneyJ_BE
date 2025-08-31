package com.project.moneyj.openai.controller;


import com.project.moneyj.openai.dto.TripBudgetResponseDTO;
import com.project.moneyj.openai.dto.TripBudgetRequestDTO;
import com.project.moneyj.openai.service.TripBudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/openai")
public class OpenAiController {
    private final TripBudgetService tripBudgetService;

    /**
     * 여행 경비 계산
     */
    @PostMapping("/travel")
    public ResponseEntity<TripBudgetResponseDTO> getTripBudget(@RequestBody TripBudgetRequestDTO request) {
        TripBudgetResponseDTO budget = tripBudgetService.getTripBudget(request);
        return ResponseEntity.ok(budget);
    }
    //Todo : 저축 팁과 관련 컨트롤러 작성
//    @PostMapping("/saving")
}
