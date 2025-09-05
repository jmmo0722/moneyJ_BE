package com.project.moneyj.codef.repository;

import com.project.moneyj.codef.domain.CodefConnectedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodefConnectedIdRepository extends JpaRepository<CodefConnectedId, Long> {
    Optional<CodefConnectedId> findByUserId(Long userId);
    Optional<CodefConnectedId> findByConnectedId(String connectedId);
}
