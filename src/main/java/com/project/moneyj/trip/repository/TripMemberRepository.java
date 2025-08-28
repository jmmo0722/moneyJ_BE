package com.project.moneyj.trip.repository;

import com.project.moneyj.trip.domain.ContentType;
import com.project.moneyj.trip.domain.TripMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripMemberRepository extends JpaRepository<TripMember, Long> {

    // 특정 플랜(planId) + TipType 기준으로 content 만 조회
    @Query("""
        select t.content
        from TripMemberTip t
        join t.tripMember m
        join m.tripPlan p
        where p.trip_plan_id = :planId
          and t.contentType = :type
        """)
    List<String> findContentsByPlanIdAndType(@Param("planId") Long planId,
                                             @Param("type") ContentType type);
}
