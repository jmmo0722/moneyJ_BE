package com.project.moneyj.trip.service;


import com.project.moneyj.openai.util.PromptLoader;
import com.project.moneyj.trip.domain.Category;
import com.project.moneyj.trip.domain.MemberRole;
import com.project.moneyj.trip.dto.*;
import com.project.moneyj.trip.repository.*;
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
import com.project.moneyj.trip.dto.TripBudgetResponseDTO;
import com.project.moneyj.trip.dto.TripBudgetRequestDTO;
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
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TripPlanService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TripPlanRepository tripPlanRepository;
    private final TripMemberRepository tripMemberRepository;
    private final TripTipRepository tripTipRepository;
    private final TripSavingPhraseRepository tripSavingPhraseRepository;
    private final AccountRepository accountRepository;
    private final ChatClient chatClient;

    /**
     * 여행 플랜 생성
     */
    @Transactional
    public TripPlanResponseDTO createTripPlans(TripPlanRequestDTO requestDTO){

        // 멤버들 id 조회
        List<User> members = userRepository.findAllByEmailIn(requestDTO.getTripMemberEmail());

        TripPlan tripPlan = TripPlan.builder()
                .country(requestDTO.getCountry())
                .countryCode(requestDTO.getCountryCode())
                .city(requestDTO.getCity())
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

        // 카테고리 등록
        for(CategoryDTO categoryDTO : requestDTO.getCategoryDTOList()){
            Category category = Category.builder()
                    .categoryName(categoryDTO.getCategoryName())
                    .amount(categoryDTO.getAmount())
                    .tripPlan(saved)
                    .build();

            saved.getCategoryList().add(category);
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

        // 여행 플랜 조회
        TripPlan plan = tripPlanRepository.findDetailById(planId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플랜"));

        // TripMember 가 한 명도 없는 경우
        if (plan.getTripMemberList().isEmpty()) {
            throw new IllegalArgumentException("해당 플랜에 멤버가 존재하지 않습니다!");
        }

        // Category 가 한 개도 없는 경우
        if (plan.getCategoryList().isEmpty()) {
            throw new IllegalArgumentException("해당 플랜에 아직 카테고리가 없습니다.");
        }

        // 문구 조회
        // 저축 플랜 문구
        List<String> savings = tripSavingPhraseRepository.findAllContentByMemberId(userId);
        if(savings.isEmpty()) throw new IllegalArgumentException("저축 플랜이 존재하지 않습니다!");

        // 여행 팁 문구
        List<String> tips = tripTipRepository.findAllByCountry(plan.getCountry());
        if(tips.isEmpty()) throw new IllegalArgumentException("여행 팁이 존재하지 않습니다!");

        // 카테고리 조회 및 DTO 변환
        List<Category> categoryList = categoryRepository.findByTripPlanId(planId);
        List<CategoryDTO> categoryDTOList = categoryList.stream().map(CategoryDTO::fromEntity).toList();


        return TripPlanDetailResponseDTO.fromEntity(plan, savings, tips, categoryDTOList);
    }

    /**
     * 여행 플랜 수정
     */
    @Transactional
    public TripPlanResponseDTO patchPlan(Long planId, TripPlanPatchRequestDTO requestDTO) {

        TripPlan existingPlan = tripPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("여행 플랜을 찾을 수 없습니다!: " + planId));

        existingPlan.update(requestDTO);

        return new TripPlanResponseDTO(planId, "여행 플랜 수정하였습니다.");
    }

    /**
     * 여행 멤버 추가
     */
    @Transactional
    public TripPlanResponseDTO addTripMember(Long planId, AddTripMemberRequestDTO addDTO){

        // 여행 플랜 조회
        TripPlan existingPlan = tripPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("여행 플랜을 찾을 수 없습니다!" + planId));

        // 사용자 조회
        for(String email : addDTO.getEmail()){
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다!" + email));

            TripMember tripMember = TripMember.builder()
                    .user(user)
                    .memberRole(MemberRole.MEMBER)
                    .build();

            tripMember.addTripMember(existingPlan);
        }
        return new TripPlanResponseDTO(planId, "멤버 추가 완료");

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
    /**
     * 여행 경비 계산 관련 Prompt
     */
    public TripBudgetResponseDTO getTripBudget(TripBudgetRequestDTO request) {

        String promptTemplate = PromptLoader.load("/prompts/trip_budget.txt");

        String promptText = String.format(
                promptTemplate,
                request.getCountry(),
                request.getCity(),
                request.getNights(),
                request.getDays(),
                request.getStartDate(),
                request.getEndDate()
        );

        return chatClient
                .prompt()
                .system("너는 여행 경비 분석가야. 반드시 JSON으로만 답변해야 한다.")
                .user(promptText)
                .call()
                .entity(TripBudgetResponseDTO.class);
    }
}
