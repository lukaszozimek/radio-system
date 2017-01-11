package io.protone.repository;

import io.protone.domain.LIBVideoObject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBVideoObject entity.
 */
@SuppressWarnings("unused")
public interface LIBVideoObjectRepository extends JpaRepository<LIBVideoObject,Long> {

}
