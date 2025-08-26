package com.project.moneyj.trip.repository;

import com.project.moneyj.trip.domain.TripPlans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripPlansRepository extends JpaRepository<TripPlans, Long> {

    @Query("""
           SELECT tp 
           FROM TripPlans tp 
           JOIN tp.tripMembersList tm 
           WHERE tm.user.user_id = :userId
           """)
    List<TripPlans> findAllByUserId(@Param("userId") Long userId);
}
