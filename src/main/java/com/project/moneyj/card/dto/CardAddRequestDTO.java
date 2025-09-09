package com.project.moneyj.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardAddRequestDTO {

    private String organizationCode;
    private String cardNumber;
    private Long cardPassword;
}
