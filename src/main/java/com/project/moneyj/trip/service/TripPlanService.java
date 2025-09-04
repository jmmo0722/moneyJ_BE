package com.project.moneyj.trip.service;

import com.project.moneyj.account.domain.Account;
import com.project.moneyj.account.repository.AccountRepository;
import com.project.moneyj.trip.domain.TripMember;
import com.project.moneyj.trip.domain.TripPlan;
import com.project.moneyj.trip.dto.TripMemberDTO;
import com.project.moneyj.trip.dto.TripPlanDetailResponseDTO;
import com.project.moneyj.trip.dto.TripPlanListResponseDTO;
import com.project.moneyj.trip.dto.TripPlanPatchRequestDTO;
import com.project.moneyj.trip.dto.TripPlanRequestDTO;
import com.project.moneyj.trip.dto.TripPlanResponseDTO;
import com.project.moneyj.trip.dto.UserBalanceResponseDTO;
import com.project.moneyj.trip.repository.TripMemberRepository;
import com.project.moneyj.trip.repository.TripPlanRepository;
import com.project.moneyj.trip.repository.TripSavingPhraseRepository;
import com.project.moneyj.trip.repository.TripTipRepository;
import com.project.moneyj.user.domain.User;
import com.project.moneyj.user.repository.UserRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TripPlanService {

    private final UserRepository userRepository;
    private final TripPlanRepository tripPlanRepository;
    private final TripMemberRepository tripMemberRepository;
    private final TripTipRepository tripTipRepository;
    private final TripSavingPhraseRepository tripSavingPhraseRepository;
    private final AccountRepository accountRepository;

    /**
     * 여행 플랜 생성
     */
    @Transactional
    public TripPlanResponseDTO createTripPlans(TripPlanRequestDTO requestDTO){

        // 멤버들 id 조회
        List<User> members = userRepository.findAllByEmailIn(requestDTO.getTripMemberList());
        for (User member : members) {
            log.info("------------------------member: {}", member);
        }

        TripPlan tripPlan = TripPlan.builder()
                .country(requestDTO.getCountry())
                .countryCode(requestDTO.getCountryCode())
                .city(requestDTO.getCity())
                .flightCost((requestDTO.getFlightCost()))
                .accommodationCost((requestDTO.getAccommodationCost()))
                .foodCost((requestDTO.getFoodCost()))
                .otherCost((requestDTO.getOtherCost()))
                .currentSavings(0)
                .membersCount(members.size())
                .duration(requestDTO.getDuration())
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

        return new TripPlanResponseDTO(saved.getTripPlanId(), "여행 플랜 생성 완료");
    }

    /**
     * 여행 플랜 조회
     */
    @Transactional(readOnly = true)
    public List<TripPlanListResponseDTO> getUserTripPlans(Long userId) {

        List<TripPlan> tripPlan = tripPlanRepository.findAllByUserId(userId);
        return tripPlan.stream()
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
        if(savings.isEmpty()) throw new IllegalArgumentException("저축 플랜이 존재하지 않습니다!");

        List<String> tips = tripTipRepository.findAllByCountry(plan.getCountry());
        if(tips.isEmpty()) throw new IllegalArgumentException("여행 팁이 존재하지 않습니다!");

        // 멤버 DTO 변환
        List<TripMember> tripMemberList = tripMemberRepository.findTripMemberByTripPlanId(planId);
        if (tripMemberList.isEmpty()) {
            throw new IllegalArgumentException("해당 플랜에 멤버가 존재하지 않습니다!");
        }

        List<TripMemberDTO> tripMemberDTOList = tripMemberList.stream()
                .map(TripMemberDTO::fromEntity).toList();

        return TripPlanDetailResponseDTO.fromEntity(plan, savings, tips, tripMemberDTOList);
    }

    /**
     * 여행 플랜 수정
     */
    @Transactional
    public TripPlanResponseDTO patchPlan(Long planId, TripPlanPatchRequestDTO requestDTO) {

        TripPlan existingPlan = tripPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found: " + planId));

        existingPlan.update(requestDTO);

        return new TripPlanResponseDTO(planId, "여행 플랜 수정하였습니다.");
    }

    /**
     * 여행 플랜 삭제
     */
    @Transactional
    public TripPlanResponseDTO leavePlan(Long planId, Long currentUserId) {

        // 사용자가 존재하는지 확인
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("가입된 사용자가 아닙니다."));

        // 해당 여행 플랜이 존재하는지 확인
        TripPlan tripPlan = tripPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플랜입니다."));

        // 사용자가 해당 플랜의 멤버인지 확인
        TripMember memberToRemove = tripMemberRepository.findByTripPlanAndUser(tripPlan, currentUser)
                .orElseThrow(() -> new IllegalStateException("해당 여행 플랜의 멤버가 아닙니다."));

        // TripMember 삭제
        // 고아 객체 옵션에 의해 TripPlan 의 tripMemberList 에서도 자동으로 제거 됨.
        tripMemberRepository.delete(memberToRemove);

        return new TripPlanResponseDTO(planId, "해당 플랜을 삭제하였습니다.");
    }

    @Transactional(readOnly = true)
    public List<UserBalanceResponseDTO> getUserBalances(Long tripPlanId) {
        List<Account> accounts = accountRepository.findByTripPlanId(tripPlanId);

        return accounts.stream()
            .map(a -> {
                double rawProgress = 0.0;
                TripPlan tp = a.getTripPlan();
                if (tp != null && tp.getTotalBudget() != null && tp.getTotalBudget() > 0) {
                    rawProgress = (a.getBalance() * 100.0) / tp.getTotalBudget();
                }
                // 소수점 1자리로 반올림
                double progress = new BigDecimal(rawProgress)
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue();

                return new UserBalanceResponseDTO(
                    a.getUser().getUserId(),
                    a.getUser().getNickname(),
                    a.getUser().getProfileImage(),
                    a.getBalance(),
                    progress
                );
            })
            .toList();
    }
}
