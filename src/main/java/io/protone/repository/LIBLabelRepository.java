package io.protone.repository;

import io.protone.domain.LIBLabel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBLabel entity.
 */
@SuppressWarnings("unused")
public interface LIBLabelRepository extends JpaRepository<LIBLabel,Long> {

}
