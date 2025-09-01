package com.project.moneyj.trip.repository;

import com.project.moneyj.trip.domain.TripPlan;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TripPlanRepository extends JpaRepository<TripPlan, Long> {

    @Query("""
           select tp 
           from TripPlan tp 
           join fetch tp.tripMemberList tm 
           where tm.user.userId = :userId
           """)
    List<TripPlan> findAllByUserId(@Param("userId") Long userId);

    @Query("""
        select tp from TripPlan tp
        left join fetch tp.tripMemberList tm
        left join fetch tm.user u
        where tp.trip_plan_id = :planId
    """)
    Optional<TripPlan> findDetailById(@Param("planId") Long planId);
}
