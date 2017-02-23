package io.protone.repository;

import io.protone.domain.LibCloudObject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LibCloudObject entity.
 */
@SuppressWarnings("unused")
public interface LibCloudObjectRepository extends JpaRepository<LibCloudObject,Long> {

}
