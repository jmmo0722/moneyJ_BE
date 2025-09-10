package com.project.moneyj.account.repository;

import com.project.moneyj.account.domain.Account;
import java.util.List;
import java.util.Optional;

import com.project.moneyj.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("""
      select a
      from Account a
      join fetch a.user u
      join fetch a.tripPlan tp
      where tp.tripPlanId = :tripPlanId
      order by a.balance desc
    """)
    List<Account> findByTripPlanId(@Param("tripPlanId") Long tripPlanId);

    Optional<Account> findByUser_UserId(Long userUserId);

    Optional<Account> findByUser_UserIdAndOrganizationCode(Long userId, String organizationCode);

}
