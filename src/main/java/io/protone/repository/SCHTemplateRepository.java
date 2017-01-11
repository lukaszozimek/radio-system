package io.protone.repository;

import io.protone.domain.SCHTemplate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SCHTemplate entity.
 */
@SuppressWarnings("unused")
public interface SCHTemplateRepository extends JpaRepository<SCHTemplate,Long> {

}
