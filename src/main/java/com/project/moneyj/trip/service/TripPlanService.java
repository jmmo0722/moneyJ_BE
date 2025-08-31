package com.project.moneyj.trip.service;

import com.project.moneyj.trip.domain.TripMember;
import com.project.moneyj.trip.domain.TripPlan;
import com.project.moneyj.trip.dto.*;
import com.project.moneyj.trip.repository.TripMemberRepository;
import com.project.moneyj.trip.repository.TripPlanRepository;
import com.project.moneyj.trip.repository.TripSavingPhraseRepository;
import com.project.moneyj.trip.repository.TripTipRepository;
import com.project.moneyj.user.domain.User;
import com.project.moneyj.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripPlanService {

    private final UserRepository userRepository;
    private final TripPlanRepository tripPlanRepository;
    private final TripMemberRepository tripMemberRepository;
    private final TripTipRepository tripTipRepository;
    private final TripSavingPhraseRepository tripSavingPhraseRepository;

    /**
     * 여행 플랜 생성
     */
    @Transactional
    public TripPlanResponseDTO createTripPlans(TripPlanRequestDTO requestDTO){

        // 멤버들 id 조회
        List<User> members = userRepository.findAllByEmailIn(requestDTO.getTripMemberList());

        TripPlan tripPlan = TripPlan.builder()
                .currentSavings(0)
                .duration(requestDTO.getDuration())
                .membersCount(members.size())
                .tripStartDate(requestDTO.getTripStartDate())
                .tripEndDate(requestDTO.getTripEndDate())
                .totalBudget(requestDTO.getTotalBudget())
                .startDate(requestDTO.getStartDate())
                .targetDate(requestDTO.getTargetDate())
                .build();

        TripPlan saved = tripPlanRepository.save(tripPlan);

        // 모든 멤버 등록
        for (User user : members) {
            TripMember tripMember = new TripMember();
            tripMember.enrollTripMember(user, saved);
            tripMemberRepository.save(tripMember);
        }


        return new TripPlanResponseDTO(saved.getTrip_plan_id(), "여행 플랜 생성 완료");
    }

    /**
     * 여행 플랜 조회
     */
    @Transactional
    public List<TripPlanListResponseDTO> getUserTripPlans(Long userId) {
        return tripPlanRepository.findAllByUserId(userId).stream()
                .map(TripPlanListResponseDTO::fromEntity)
                .toList();
    }

    /**
     * 여행 플랜 상세 조회
     */
    @Transactional(readOnly = true)
    public TripPlanDetailResponseDTO getTripPlanDetail(Long planId, Long userId) {
        TripPlan plan = tripPlanRepository.findDetailById(planId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플랜"));

        // 문구 조회
        List<String> savings = tripSavingPhraseRepository.findAllContentByMemberId(userId);
        List<String> tips = tripTipRepository.findAllByCountry(plan.getCountry());

        // 멤버 DTO 변환
        List<TripMemberDTO> tripMemberDTOList = tripMemberRepository.findTripMemberByTripPlanId(planId).stream()
                .map(TripMemberDTO::fromEntity).toList();

        return TripPlanDetailResponseDTO.fromEntity(plan, savings, tips, tripMemberDTOList);
    }
}
