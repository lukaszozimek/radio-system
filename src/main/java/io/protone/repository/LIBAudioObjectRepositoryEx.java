package io.protone.repository;

import io.protone.domain.LIBAudioObject;
import io.protone.domain.LIBMediaItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBAudioObject entity.
 */
@SuppressWarnings("unused")
public interface LIBAudioObjectRepositoryEx extends JpaRepository<LIBAudioObject,Long> {
    List<LIBAudioObject> findByMediaItem(LIBMediaItem mediaItem);
}
