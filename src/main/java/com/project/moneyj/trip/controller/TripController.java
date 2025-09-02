package com.project.moneyj.trip.controller;

import com.project.moneyj.auth.dto.CustomOAuth2User;
import com.project.moneyj.trip.dto.*;
import com.project.moneyj.trip.service.TripPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trip-plans")
public class TripController {

    private final TripPlanService tripPlanService;

    /**
     * 여행 플랜 생성
     */
    @PostMapping
    public ResponseEntity<TripPlanResponseDTO> createTripPlan(@RequestBody TripPlanRequestDTO request) {
        TripPlanResponseDTO response = tripPlanService.createTripPlans(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 여행 플랜 조회
     */
    @GetMapping
    public ResponseEntity<List<TripPlanListResponseDTO>> getUserTripPlans(@AuthenticationPrincipal CustomOAuth2User customUser){
        Long userId = customUser.getUserId();
        return ResponseEntity.ok(tripPlanService.getUserTripPlans(userId));
    }

    /**
     * 여행 플랜 상세 조회
     */
    @GetMapping("/{planId}")
    public ResponseEntity<TripPlanDetailResponseDTO> getPlanDetail(
            @PathVariable Long planId,
            @AuthenticationPrincipal CustomOAuth2User customUser) {
        Long userId = customUser.getUserId();
        return ResponseEntity.ok(tripPlanService.getTripPlanDetail(planId, userId));
    }

    /**
     * 여행 플랜 수정
     */
    @PatchMapping("/{planId}")
    public ResponseEntity<TripPlanResponseDTO> putPlan(
            @PathVariable Long planId,
            @RequestBody TripPlanPatchRequestDTO requestDTO){

        TripPlanResponseDTO updatedPlan = tripPlanService.patchPlan(planId, requestDTO);
        return ResponseEntity.ok(updatedPlan);
    }

    /**
     * 여행 플랜 탈퇴
     */
    // TODO 아무도 없는 유령 플랜 삭제 로직 추가
    @DeleteMapping("/{planId}")
    public ResponseEntity<TripPlanResponseDTO> leavePlan(
            @PathVariable Long planId,
            @AuthenticationPrincipal CustomOAuth2User customUser){

        Long userId = customUser.getUserId();
        TripPlanResponseDTO response = tripPlanService.leavePlan(planId, userId);

        return ResponseEntity.ok(response);
    }
}
