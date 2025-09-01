package com.project.moneyj.trip.repository;

import com.project.moneyj.trip.domain.TripPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripPlanRepository extends JpaRepository<TripPlan, Long> {

    // 사용자별 모든 여행 플랜 조회
    @Query("""
           select tp 
           from TripPlan tp 
           join fetch tp.tripMemberList tm 
           where tm.user.userId = :userId
           """)
    List<TripPlan> findAllByUserId(@Param("userId") Long userId);

    // 여행 플랜 상세 조회
    @Query("""
        select tp from TripPlan tp
        left join fetch tp.tripMemberList tm
        where tp.tripPlanId = :planId
    """)
    Optional<TripPlan> findDetailById(@Param("planId") Long planId);
}
