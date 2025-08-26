package com.project.moneyj.trip.domain;

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
@Table(name = "trip_plans")
public class TripPlans {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trip_plans_id;

    private Integer membersCount;
    private String destination;

    private Integer duration;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;

    private Integer totalBudget;
    private Integer currentSavings;

    private LocalDate startDate;
    private LocalDate targetDate;

    private String savingsPhrase;
    private String tripTip;

    @OneToMany(mappedBy = "tripPlans", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripMembers> tripMembersList = new ArrayList<>();

}