package io.protone.repository;

import io.protone.domain.SchTemplate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SchTemplate entity.
 */
@SuppressWarnings("unused")
public interface SchTemplateRepository extends JpaRepository<SchTemplate,Long> {

}
