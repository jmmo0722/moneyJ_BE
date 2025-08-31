package com.project.moneyj.trip.domain;

import com.project.moneyj.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trip_member")
public class TripMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trip_member_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_plan_id")
    private TripPlan tripPlan;

    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private MemberRole memberRole;

    @OneToMany(mappedBy = "tripMember", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripSavingPhrase> tripSavingPhrase = new ArrayList<>();

    public void enrollTripMember(User user, TripPlan tripPlan){
        this.user = user;
        user.getTripMemberList().add(this);

        this.tripPlan = tripPlan;
        tripPlan.getTripMemberList().add(this);

        // TODO 후에 방장과 멤버 역할 구분 로직 작성
        this.memberRole = MemberRole.MEMBER;
    }
}
