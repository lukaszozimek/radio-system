package io.protone.traffic.repository;

import io.protone.traffic.domain.TraEmission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Spring Data JPA repository for the TraEmission entity.
 */
@SuppressWarnings("unused")
public interface TraEmissionRepository extends JpaRepository<TraEmission,Long> {
    List<TraEmission> findAllByNetwork_Shortcut(String shortcut, Pageable pageable);

    TraEmission findOneByIdAndNetwork_Shortcut(Long id, String shortcut);

    void deleteByIdAndNetwork_Shortcut(Long id, String shortcut);
}
