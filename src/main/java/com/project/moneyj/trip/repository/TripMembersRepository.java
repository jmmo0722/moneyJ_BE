package com.project.moneyj.trip.repository;

import com.project.moneyj.trip.domain.TripMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripMembersRepository extends JpaRepository<TripMembers, Long> {
}
