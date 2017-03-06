package io.protone.repository.custom;

import java.util.*;

import io.protone.domain.SchBlock;
import io.protone.domain.SchEmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;

/**
 * Spring Data JPA repository for the SchEmission entity.
 */
@SuppressWarnings("unused")
public interface CustomSchEmissionRepository extends JpaRepository<SchEmission, Long> {
    List<SchEmission> findByBlock(SchBlock block);
}
