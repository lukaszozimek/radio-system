package io.protone.repository.custom;

import io.protone.domain.SchBlock;
import io.protone.domain.SchEmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the SchEmission entity.
 */
@SuppressWarnings("unused")
public interface CustomSchEmissionRepository extends JpaRepository<SchEmission,Long> {
    List<SchEmission> findByBlock(SchBlock block);
}
