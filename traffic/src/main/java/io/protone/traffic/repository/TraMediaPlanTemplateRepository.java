package io.protone.traffic.repository;


import io.protone.traffic.domain.TraMediaPlanTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TraBlockConfiguration entity.
 */
@SuppressWarnings("unused")
public interface TraMediaPlanTemplateRepository extends JpaRepository<TraMediaPlanTemplate, Long> {
    Slice<TraMediaPlanTemplate> findSliceByNetwork_Shortcut(String shortcut, Pageable pageable);

    TraMediaPlanTemplate findOneByIdAndNetwork_Shortcut(Long id, String shortcut);

    void deleteByIdAndNetwork_Shortcut(Long id, String shortcut);
}
