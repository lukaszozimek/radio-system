package io.protone.repository;

import io.protone.domain.CRMContact;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMContact entity.
 */
@SuppressWarnings("unused")
public interface CRMContactRepository extends JpaRepository<CRMContact,Long> {

}
