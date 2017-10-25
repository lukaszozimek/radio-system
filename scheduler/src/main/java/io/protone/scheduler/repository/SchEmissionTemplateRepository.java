package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchEmissionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * An Emission repository.
 */
public interface SchEmissionTemplateRepository extends JpaRepository<SchEmissionTemplate, Long> {

    void deleteAllByEventTemplate_Id(long eventConfigurationId);

    Set<SchEmissionTemplate> findAllByEventTemplate_Id(long eventConfigurationId);
}
