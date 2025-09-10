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
import java.time.LocalDateTime;
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

    private LocalDateTime usedDateTime; // resUsedDate + resUsedTime

    private Integer usedAmount;

    private String storeName;
    private String storeCorpNo;
    private String storeAddr;
    private String storeNo;
    private String storeType;
    private String approvalNo;

    private LocalDateTime updateAt;

    // 연관관계 메소드
    public void addTransaction(User user){
        this.user = user;
        user.getTransactionList().add(this);
    }
}