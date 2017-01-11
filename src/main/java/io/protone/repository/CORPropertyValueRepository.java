package io.protone.repository;

import io.protone.domain.CORPropertyValue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORPropertyValue entity.
 */
@SuppressWarnings("unused")
public interface CORPropertyValueRepository extends JpaRepository<CORPropertyValue,Long> {

}
