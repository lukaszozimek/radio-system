package io.protone.repository;

import io.protone.domain.CORTag;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORTag entity.
 */
@SuppressWarnings("unused")
public interface CORTagRepository extends JpaRepository<CORTag,Long> {

}
