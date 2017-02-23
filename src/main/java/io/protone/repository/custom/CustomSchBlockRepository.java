package io.protone.repository.custom;

import io.protone.domain.SchBlock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the SchBlock entity.
 */
@SuppressWarnings("unused")
public interface CustomSchBlockRepository extends JpaRepository<SchBlock,Long> {

}
