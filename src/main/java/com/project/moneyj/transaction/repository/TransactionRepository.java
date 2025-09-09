package com.project.moneyj.transaction.repository;

import com.project.moneyj.transaction.domain.Transaction;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // 특정 유저의 특정 기간 거래 내역 조회
    List<Transaction> findByUser_UserIdAndUsedDateBetween(Long userId, LocalDate from, LocalDate to);

}
