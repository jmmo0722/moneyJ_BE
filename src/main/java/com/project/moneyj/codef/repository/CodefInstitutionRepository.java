package com.project.moneyj.codef.repository;

import com.project.moneyj.codef.domain.CodefInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodefInstitutionRepository extends JpaRepository<CodefInstitution, Long> {

    Optional<CodefInstitution> findByConnectedIdAndOrganization(String connectedId, String organization);
}
