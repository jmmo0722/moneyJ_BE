package com.project.moneyj.trip.controller;

import com.project.moneyj.trip.dto.TripPlansListResponse;
import com.project.moneyj.trip.dto.TripPlansRequestDTO;
import com.project.moneyj.trip.dto.TripPlansResponseDTO;
import com.project.moneyj.trip.repository.TripPlansRepository;
import com.project.moneyj.trip.service.TripPlansService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trip-plans")
public class TripController {

    private final TripPlansService tripPlansService;

    /**
     * 여행 플랜 생성
     */
    @PostMapping
    public ResponseEntity<TripPlansResponseDTO> createTripPlan(@RequestBody TripPlansRequestDTO request) {
        TripPlansResponseDTO response = tripPlansService.createTripPlans(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 여행 플랜 조회
     */
    @GetMapping
    public ResponseEntity<List<TripPlansListResponse>> getUserTripPlans(Long userId){
        return ResponseEntity.ok(tripPlansService.getUserTripPlans(userId));
    }
}
