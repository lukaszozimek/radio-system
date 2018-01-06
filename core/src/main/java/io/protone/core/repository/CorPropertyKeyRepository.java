package io.protone.core.repository;


import io.protone.core.domain.CorPropertyKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorPropertyKey entity.
 */
@SuppressWarnings("unused")
public interface CorPropertyKeyRepository extends JpaRepository<CorPropertyKey, Long> {

    Slice<CorPropertyKey> findSliceByChannel_Organization_Shortcut(String organizationShortcut, Pageable pageable);

    void deleteByIdAndChannel_Organization_Shortcut(Long id, String organizationShortcut);

    CorPropertyKey findByIdAndChannel_Organization_Shortcut(Long id, String organizationShortcut);
}
