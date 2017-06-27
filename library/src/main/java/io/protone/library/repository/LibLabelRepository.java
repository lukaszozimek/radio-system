package io.protone.library.repository;

import io.protone.domain.LibLabel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibLabel entity.
 */
@SuppressWarnings("unused")
public interface LibLabelRepository extends JpaRepository<LibLabel,Long> {

}
