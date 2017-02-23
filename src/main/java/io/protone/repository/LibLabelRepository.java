package io.protone.repository;

import io.protone.domain.LibLabel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LibLabel entity.
 */
@SuppressWarnings("unused")
public interface LibLabelRepository extends JpaRepository<LibLabel,Long> {

}
