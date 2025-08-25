package com.project.moneyj.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Table(name = "trip_plans")
public class TripPlans {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_plans_id")
    private Long id;

    private Integer membersCount;
    private String destination;

    private Integer duration;
    private Date tripStartDate;
    private Date tripEndDate;

    private Integer totalBudget;
    private Integer currentSavings;

    private Date startDate;
    private Date targetDate;

    private String savingsPhrase;
    private String tripTip;

    @OneToMany(mappedBy = "tripPlans", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripMembers> tripMembersList = new ArrayList<>();

    protected TripPlans(){}
}