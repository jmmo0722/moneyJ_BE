package com.project.moneyj.analysis.domain;

import com.project.moneyj.transaction.domain.TransactionCategory;
import com.project.moneyj.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_summary", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "yearMonth", "transactionCategory"})
})
public class TransactionSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_summary_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private TransactionCategory transactionCategory;

    private String summaryMonth;
    private Integer totalAmount = 0;
    private Integer transactionCount = 0;
    private LocalDate updateAt;
}
