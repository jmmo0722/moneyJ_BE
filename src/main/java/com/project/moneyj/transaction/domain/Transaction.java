package com.project.moneyj.transaction.domain;

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
@Table(name = "transaction")
public class Transaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private TransactionCategory transactionCategory;

    private LocalDate usedDate;

    private Integer amount;
    private String merchantName;
    private Integer paymentAmount;
    private Integer afterPaymentBalance;
    private Long approvalNo;
    private String merchantType;
    private String merchantAddr;
    private Long merchantNo;

    private String categoryCode;

    private LocalDate updateAt;


    // 연관관계 메소드
    public void addTransaction(User user){
        this.user = user;
        user.getTransactionList().add(this);
    }
}