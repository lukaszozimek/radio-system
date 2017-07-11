package io.protone.core.repository;


import io.protone.core.domain.CorPropertyKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CorPropertyKey entity.
 */
@SuppressWarnings("unused")
public interface CorPropertyKeyRepository extends JpaRepository<CorPropertyKey, Long> {

    List<CorPropertyKey> findByNetwork_Shortcut(String networkShortcut, Pageable pageable);

    void deleteByIdAndNetwork_Shortcut(Long id, String networkShortcut);

    CorPropertyKey findByIdAndNetwork_Shortcut(Long id, String networkShortcut);
}
