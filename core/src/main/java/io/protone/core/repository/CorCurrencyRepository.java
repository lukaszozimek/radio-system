package io.protone.core.repository;


import io.protone.core.domain.CorCurrency;
import io.protone.core.domain.CorNetwork;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CorCurrency entity.
 */
@SuppressWarnings("unused")
public interface CorCurrencyRepository extends JpaRepository<CorCurrency, Long> {
    List<CorCurrency> findByOrganization_Shortcut(String organizationShortcut);

    Slice<CorCurrency> findSliceByOrganization_Shortcut(String organizationShortcut, Pageable pageable);

    void deleteByIdAndOrganization_Shortcut(Long id, String organizationShortcut);

    CorCurrency findOneByIdAndOrganization_Shortcut(Long id, String organizationShortcut);
}
