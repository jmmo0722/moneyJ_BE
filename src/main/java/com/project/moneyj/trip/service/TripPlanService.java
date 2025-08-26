package com.project.moneyj.trip.service;

import com.project.moneyj.trip.domain.ContentType;
import com.project.moneyj.trip.domain.TripMember;
import com.project.moneyj.trip.domain.TripPlan;
import com.project.moneyj.trip.dto.TripPlanDetailResponse;
import com.project.moneyj.trip.dto.TripPlanListResponse;
import com.project.moneyj.trip.dto.TripPlanRequestDTO;
import com.project.moneyj.trip.dto.TripPlanResponseDTO;
import com.project.moneyj.trip.repository.TripMemberRepository;
import com.project.moneyj.trip.repository.TripPlanRepository;
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
                .destination(requestDTO.getDestination())
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
    public List<TripPlanListResponse> getUserTripPlans(Long userId) {
        return tripPlanRepository.findAllByUserId(userId).stream()
                .map(TripPlanListResponse::fromEntity)
                .toList();
    }

    /**
     * 여행 플랜 상세 조회
     */
//    @Transactional(readOnly = true)
//    public TripPlanDetailResponse getTripPlanDetail(Long planId) {
//        TripPlan plan = tripPlanRepository.findDetailById(planId)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플랜"));
//
//        // 문구 조회
//        //List<String> savings = tripMemberRepository.findContentsByPlanIdAndType(planId, ContentType.SAVINGS);
//        //List<String> tips = tripMemberRepository.findContentsByPlanIdAndType(planId, ContentType.TIP);
//
//        // 멤버 DTO 변환
//        List<TripPlanDetailResponse.MemberDto> members = plan.getTripMemberList().stream()
//                .map(tm -> new TripPlanDetailResponse.MemberDto(
//                        tm.getUser().getUser_id(),
//                        tm.getUser().getEmail(),
//                        tm.getUser().getNickname(),
//                        tm.getUser().getImage_url()
//                ))
//                .toList();
//
//        return new TripPlanDetailResponse(
//                plan.getTrip_plan_id(),
//                plan.getDestination(),
//                plan.getDuration(),
//                plan.getTripStartDate(),
//                plan.getTripEndDate(),
//                plan.getTotalBudget(),
//                plan.getCurrentSavings(),
//                plan.getStartDate(),
//                plan.getTargetDate(),
//                savings,
//                tips,
//                members
//        );
//    }
}
