package io.protone.repository;

import io.protone.domain.LibAudioObject;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LibAudioObject entity.
 */
@SuppressWarnings("unused")
public interface LibAudioObjectRepository extends JpaRepository<LibAudioObject,Long> {

}
