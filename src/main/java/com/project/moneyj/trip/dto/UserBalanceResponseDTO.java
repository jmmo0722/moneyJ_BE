package com.project.moneyj.trip.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserBalanceResponseDTO {
    private Long userId;
    private String nickname;
    private String profileImage;
    private Long balance;
    private double progress; // 달성률 %
}
