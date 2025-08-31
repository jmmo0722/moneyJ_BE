package com.project.moneyj.trip.repository;

import com.project.moneyj.trip.domain.TripMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripMemberRepository extends JpaRepository<TripMember, Long> {

    @Query("""
    select TripMember
    from TripMember tm
    where tm.tripPlan.trip_plan_id = :planId
    """)
    List<TripMember> findTripMemberByTripPlanId(@Param("planId") Long planId);

    // 사용자별 저축 플랜 문구 조회
    @Query("""
        select t.content
        from TripSavingPhrase t
        join t.tripMember m
        join m.tripPlan p
        where p.trip_plan_id = :planId
        """)
    List<String> findContentsByPlanIdAndType(@Param("planId") Long planId);
}
