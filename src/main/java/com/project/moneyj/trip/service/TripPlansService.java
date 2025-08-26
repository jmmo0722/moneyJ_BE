package com.project.moneyj.trip.service;

import com.project.moneyj.trip.domain.TripMembers;
import com.project.moneyj.trip.domain.TripPlans;
import com.project.moneyj.trip.dto.TripPlansRequestDTO;
import com.project.moneyj.trip.dto.TripPlansResponseDTO;
import com.project.moneyj.trip.repository.TripMembersRepository;
import com.project.moneyj.trip.repository.TripPlansRepository;
import com.project.moneyj.user.domain.User;
import com.project.moneyj.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripPlansService {

    private final UserRepository userRepository;
    private final TripPlansRepository tripPlansRepository;
    private final TripMembersRepository tripMembersRepository;

    /**
     * 여행 플랜 생성
     */
    public TripPlansResponseDTO createTripPlans(TripPlansRequestDTO requestDTO){

        // 멤버들 id 조회
        List<User> members = userRepository.findAllByEmailIn(requestDTO.getTripMemberList());

        TripPlans tripPlan = TripPlans.builder()
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

        TripPlans saved = tripPlansRepository.save(tripPlan);

        // 모든 멤버 등록
        for (User user : members) {
            TripMembers tripMember = new TripMembers();
            tripMember.enrollTripMember(user, saved);
            tripMembersRepository.save(tripMember);
        }


        return new TripPlansResponseDTO(saved.getTrip_plans_id(), "여행 플랜 생성 완료");
    }
}
