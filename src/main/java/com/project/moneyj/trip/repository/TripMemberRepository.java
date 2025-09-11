package com.project.moneyj.trip.repository;

import com.project.moneyj.trip.domain.TripMember;
import com.project.moneyj.trip.domain.TripPlan;
import com.project.moneyj.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripMemberRepository extends JpaRepository<TripMember, Long> {

    @Query("SELECT tm FROM TripMember tm WHERE tm.tripPlan.tripPlanId = :planId")
    List<TripMember> findTripMemberByTripPlanId(@Param("planId") Long planId);

    // 여행 플랜 삭제
    Optional<TripMember> findByTripPlanAndUser(TripPlan tripPlan, User user);


    @Query("SELECT tm FROM TripMember tm WHERE tm.user.userId = :userId")
    Optional<TripMember> findByUserId(@Param("userId") Long userId);

    boolean existsByUser_UserId(Long userId);
}
