package io.protone.repository;

import io.protone.domain.LIBArtist;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBArtist entity.
 */
@SuppressWarnings("unused")
public interface LIBArtistRepository extends JpaRepository<LIBArtist,Long> {

}
