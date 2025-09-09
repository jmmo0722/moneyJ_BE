package com.project.moneyj.analysis.repository;

import com.project.moneyj.analysis.domain.TransactionSummary;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionSummaryRepository extends JpaRepository<TransactionSummary, Long> {

    @Query(
        "SELECT t FROM TransactionSummary t " +
        "WHERE t.user.userId = :userId AND t.summaryMonth BETWEEN :from AND :to " +
        "ORDER BY t.summaryMonth ASC"
    )
    List<TransactionSummary> findByUserIdBetweenMonths(
        @Param("userId") Long userId,
        @Param("from") String from,
        @Param("to") String to
    );

    // 특정 유저의 특정 월 TransactionSummary 조회
    List<TransactionSummary> findByUser_UserIdAndSummaryMonth(Long userId, String summaryMonth);

}
