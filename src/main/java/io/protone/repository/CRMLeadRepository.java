package io.protone.repository;

import io.protone.domain.CRMLead;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMLead entity.
 */
@SuppressWarnings("unused")
public interface CRMLeadRepository extends JpaRepository<CRMLead, Long> {
    CRMLead findByShortcut(String shortcut);
}
