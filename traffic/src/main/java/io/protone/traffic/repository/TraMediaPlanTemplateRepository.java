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
    Slice<TraMediaPlanTemplate> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut, Pageable pageable);

    TraMediaPlanTemplate findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);
}
