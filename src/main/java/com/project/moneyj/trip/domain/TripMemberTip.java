package com.project.moneyj.trip.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trip_member_tip")
public class TripMemberTip {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripMemberTipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_plan_id")
    private TripPlan tripPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_member_id")
    private TripMember tripMember;

    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private ContentType contentType;

    private String content;

}
