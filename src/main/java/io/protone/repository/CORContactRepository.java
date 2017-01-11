package io.protone.repository;

import io.protone.domain.CORContact;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORContact entity.
 */
@SuppressWarnings("unused")
public interface CORContactRepository extends JpaRepository<CORContact,Long> {

}
