package com.project.moneyj.trip.controller;

import com.project.moneyj.trip.dto.TripPlansRequestDTO;
import com.project.moneyj.trip.dto.TripPlansResponseDTO;
import com.project.moneyj.trip.service.TripPlansService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trip-plans")
public class TripPlansController {

    private final TripPlansService tripPlansService;

    /**
     * 여행 플랜 생성
     */
    @PostMapping
    public ResponseEntity<TripPlansResponseDTO> createTripPlan(@RequestBody TripPlansRequestDTO request) {
        TripPlansResponseDTO response = tripPlansService.createTripPlans(request);
        return ResponseEntity.ok(response);
    }
}
