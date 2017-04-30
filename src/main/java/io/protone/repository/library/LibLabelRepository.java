package io.protone.repository.library;

import io.protone.domain.LibLabel;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LibLabel entity.
 */
@SuppressWarnings("unused")
public interface LibLabelRepository extends JpaRepository<LibLabel,Long> {

}
