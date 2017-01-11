package io.protone.repository;

import io.protone.domain.CORPerson;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORPerson entity.
 */
@SuppressWarnings("unused")
public interface CORPersonRepository extends JpaRepository<CORPerson,Long> {

}
