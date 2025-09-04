package com.project.moneyj.trip.repository;

import com.project.moneyj.trip.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM category WHERE trip_plan_id = :tripPlanId", nativeQuery = true)
    List<Category> findByTripPlanId(@Param("tripPlanId") Long tripPlanId);
}
