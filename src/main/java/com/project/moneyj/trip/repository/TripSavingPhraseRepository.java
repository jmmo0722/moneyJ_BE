package com.project.moneyj.trip.repository;

import com.project.moneyj.trip.domain.TripSavingPhrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripSavingPhraseRepository extends JpaRepository<TripSavingPhrase, Long> {

    @Query("""
            select tsp.content
            from TripSavingPhrase tsp
            where tsp.tripMember.user.user_id = :userId
            """)
    Optional<List<String>> findAllContentByMemberId(@Param("userId") Long userId);
}
