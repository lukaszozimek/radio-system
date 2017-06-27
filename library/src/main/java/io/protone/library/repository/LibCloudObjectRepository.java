package io.protone.library.repository;

import io.protone.domain.LibCloudObject;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibCloudObject entity.
 */
@SuppressWarnings("unused")
public interface LibCloudObjectRepository extends JpaRepository<LibCloudObject,Long> {

}
