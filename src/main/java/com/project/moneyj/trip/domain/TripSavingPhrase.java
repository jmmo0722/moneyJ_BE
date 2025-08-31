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
@Table(name = "trip_saving_phrase")
public class TripSavingPhrase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripSavingPhraseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_member_id")
    private TripMember tripMember;

    private String content;

}
