package com.project.moneyj.trip.repository;

import com.project.moneyj.trip.domain.TripPlans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripPlansRepository extends JpaRepository<TripPlans, Long> {
}
