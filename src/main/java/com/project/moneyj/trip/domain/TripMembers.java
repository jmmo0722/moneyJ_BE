package com.project.moneyj.trip.domain;

import com.project.moneyj.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trip_members")
public class TripMembers {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trip_members_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_plans_id")
    private TripPlans tripPlans;

    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private MemberRole memberRole;

    public void enrollTripMember(User user, TripPlans tripPlans){
        this.user = user;
        user.getTripMembersList().add(this);

        this.tripPlans = tripPlans;
        tripPlans.getTripMembersList().add(this);
    }
}
