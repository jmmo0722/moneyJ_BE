package com.project.moneyj.account.repository;

import com.project.moneyj.account.domain.Account;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a where a.tripPlan.tripPlanId = :tripPlanId")
    List<Account> findByTripPlanId(@Param("tripPlanId") Long tripPlanId);
}
