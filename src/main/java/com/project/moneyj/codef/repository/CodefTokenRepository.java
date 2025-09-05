package com.project.moneyj.codef.repository;

import com.project.moneyj.codef.domain.CodefToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodefTokenRepository extends JpaRepository<CodefToken, Long> {

    Optional<CodefToken> findTopByOrderByIdDesc();
}
