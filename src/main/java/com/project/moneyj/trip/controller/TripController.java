package com.project.moneyj.trip.controller;

import com.project.moneyj.trip.dto.TripPlanDetailResponseDTO;
import com.project.moneyj.trip.dto.TripPlanListResponseDTO;
import com.project.moneyj.trip.dto.TripPlanRequestDTO;
import com.project.moneyj.trip.dto.TripPlanResponseDTO;
import com.project.moneyj.trip.service.TripPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TripPlanListResponseDTO>> getUserTripPlans(Long userId){
        return ResponseEntity.ok(tripPlanService.getUserTripPlans(userId));
    }

    /**
     * 여행 플랜 상세 조회
     */
    @GetMapping("/{planId}/{userId}")
    public ResponseEntity<TripPlanDetailResponseDTO> getPlanDetail(@PathVariable Long planId, @PathVariable Long userId) {
        return ResponseEntity.ok(tripPlanService.getTripPlanDetail(planId, userId));
    }
}
