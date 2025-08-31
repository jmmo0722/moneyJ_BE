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
@Table(name = "trip_tip")
public class TripTip {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    private String country;

    private String tip;
}
