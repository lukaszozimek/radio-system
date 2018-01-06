package io.protone.core.repository;


import io.protone.core.domain.CorCountry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CorCountry entity.
 */
@SuppressWarnings("unused")
public interface CorCountryRepository extends JpaRepository<CorCountry, Long> {
    List<CorCountry> findSliceByOrganization_Shortcut(String organizationShortcut);

    Slice<CorCountry> findSliceByOrganization_Shortcut(String organizationShortcut, Pageable pageable);

    CorCountry findOneByIdAndOrganization_Shortcut(Long id, String organizationShortcut);
}
