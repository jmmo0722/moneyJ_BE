package com.project.moneyj.transaction.domain;

import com.project.moneyj.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

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