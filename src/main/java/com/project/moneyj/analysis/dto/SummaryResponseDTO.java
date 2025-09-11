package com.project.moneyj.analysis.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SummaryResponseDTO {
    private boolean cardConnected;
    private List<MonthlySummaryDTO> monthly;

    public static SummaryResponseDTO of(boolean cardConnected, List<MonthlySummaryDTO> monthly) {
        return new SummaryResponseDTO(cardConnected, monthly);
    }}
