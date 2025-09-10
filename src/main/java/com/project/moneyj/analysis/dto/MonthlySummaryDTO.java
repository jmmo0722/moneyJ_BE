package com.project.moneyj.analysis.dto;

import com.project.moneyj.analysis.domain.TransactionSummary;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MonthlySummaryDTO {
    private String month;
    private Integer monthTotalAmount;
    private List<CategorySummaryDTO> categories;

    @AllArgsConstructor
    @Getter
    public static class CategorySummaryDTO {
        private String category;
        private Integer totalAmount;
        private Integer transactionCount;

        public static CategorySummaryDTO from(TransactionSummary summary) {
            return new CategorySummaryDTO(
                summary.getTransactionCategory().getDescription(),
                summary.getTotalAmount(),
                summary.getTransactionCount()
            );
        }
    }
}