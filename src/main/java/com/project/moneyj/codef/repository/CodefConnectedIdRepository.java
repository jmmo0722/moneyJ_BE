package com.project.moneyj.codef.repository;

import com.project.moneyj.codef.domain.CodefConnectedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodefConnectedIdRepository extends JpaRepository<CodefConnectedId, Long> {
    Optional<CodefConnectedId> findByUserId(Long userId);

    Optional<CodefConnectedId> findCodefConnectedIdByConnectedId(String connectedId);

    @Query("select c.connectedId from CodefConnectedId c where c.userId = :userId and c.status = 'ACTIVE'")
    Optional<String> findActiveConnectedIdByUserId(@Param("userId") Long userId);
}
