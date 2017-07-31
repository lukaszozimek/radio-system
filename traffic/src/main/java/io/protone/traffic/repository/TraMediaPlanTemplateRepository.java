package io.protone.traffic.repository;


import io.protone.traffic.domain.TraMediaPlanTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraBlockConfiguration entity.
 */
@SuppressWarnings("unused")
public interface TraMediaPlanTemplateRepository extends JpaRepository<TraMediaPlanTemplate, Long> {
    List<TraMediaPlanTemplate> findAllByNetwork_Shortcut(String shortcut, Pageable pageable);

    TraMediaPlanTemplate findOneByIdAndNetwork_Shortcut(Long id, String shortcut);

    void deleteByIdAndNetwork_Shortcut(Long id, String shortcut);
}
