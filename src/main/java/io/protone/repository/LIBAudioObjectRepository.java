package io.protone.repository;

import io.protone.domain.LIBAudioObject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBAudioObject entity.
 */
@SuppressWarnings("unused")
public interface LIBAudioObjectRepository extends JpaRepository<LIBAudioObject,Long> {

}
