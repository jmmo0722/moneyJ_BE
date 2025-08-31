package com.project.moneyj.trip.domain;

import com.project.moneyj.trip.dto.TripPlanPatchRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trip_plan")
public class TripPlan {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trip_plan_id;

    private Integer membersCount;
    private String country;
    private String city;

    private Integer flight_cost;
    private Integer accommodation_cost;
    private Integer food_cost;
    private Integer other_cost;

    private Integer duration;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;

    private Integer totalBudget;
    private Integer currentSavings;

    private LocalDate startDate;
    private LocalDate targetDate;

    @OneToMany(mappedBy = "tripPlan", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripMember> tripMemberList = new ArrayList<>();

    // Patch 비즈니스 메소드
    public void update(TripPlanPatchRequestDTO patchRequestDTO){
        // DTO의 각 필드가 null이 아닌지 확인하고, 엔티티의 상태를 직접 변경합니다.
        if (patchRequestDTO.getCountry() != null) {
            this.country = patchRequestDTO.getCountry();
        }
        if (patchRequestDTO.getCity() != null) {
            this.city = patchRequestDTO.getCity();
        }
        if (patchRequestDTO.getFlight_cost() != null) {
            this.flight_cost = patchRequestDTO.getFlight_cost();
        }
        if (patchRequestDTO.getTripStartDate() != null) {
            this.tripStartDate = patchRequestDTO.getTripStartDate();
        }
        if (patchRequestDTO.getTripEndDate() != null) {
            this.tripEndDate = patchRequestDTO.getTripEndDate();
        }
        // ... (나머지 필드에 대해 동일한 패턴으로 반복) ...

        // 이 메소드 내에서 데이터의 일관성을 검증할 수 있습니다.
        validateDates();
    }

    // 검증 메소드
    private void validateDates() {
        if (this.tripStartDate != null && this.tripEndDate != null) {
            if (this.tripStartDate.isAfter(this.tripEndDate)) {
                throw new IllegalStateException("여행 시작일은 종료일보다 늦을 수 없습니다.");
            }
        }
    }
}