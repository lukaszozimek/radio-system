package io.protone.repository;

import io.protone.domain.LIBImageObject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBImageObject entity.
 */
@SuppressWarnings("unused")
public interface LIBImageObjectRepository extends JpaRepository<LIBImageObject,Long> {

}
