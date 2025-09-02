package com.project.moneyj.trip.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "trip_tip")
public class TripTip {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripTipId;

    private String country;

    private String tip;
}
