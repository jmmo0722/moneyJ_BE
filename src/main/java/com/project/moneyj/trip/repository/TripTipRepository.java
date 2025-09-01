package com.project.moneyj.trip.repository;

import com.project.moneyj.trip.domain.TripPlan;
import com.project.moneyj.trip.domain.TripTip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripTipRepository extends JpaRepository<TripTip, Long> {

    // 나라별 여행 팁
    @Query("""
        select tt.tip
        from TripTip tt
        where tt.country = :country
        """)
    List<String> findAllByCountry(@Param("country") String country);

}
