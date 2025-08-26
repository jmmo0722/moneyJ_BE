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
@Table(name = "trip_members_tip")
public class TripMembersTIp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripMembersTipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_plans_id")
    private TripPlans tripPlans;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_members_id")
    private TripMembers tripMembers;

    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private ContentType contentType;

    private String content;

}
