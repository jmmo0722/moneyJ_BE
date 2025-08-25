package com.project.moneyj.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "trip_members")
public class TripMembers {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_members_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_plans_id")
    private TripPlans tripPlans;

    protected TripMembers(){}
    public void enrollTripMember(User user, TripPlans tripPlans){
        this.user = user;
        user.getTripMembersList().add(this);

        this.tripPlans = tripPlans;
        tripPlans.getTripMembersList().add(this);
    }
}
