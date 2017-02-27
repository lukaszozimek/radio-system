package io.protone.repository;

import io.protone.domain.CrmContact;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CrmContact entity.
 */
@SuppressWarnings("unused")
public interface CrmContactRepository extends JpaRepository<CrmContact,Long> {

}
