package io.protone.core.repository;

import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorTax;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CorTax entity.
 */
@SuppressWarnings("unused")
public interface CorTaxRepository extends JpaRepository<CorTax, Long> {
    List<CorTax> findByChannel_Organization_Shortcut(String organizationShortcut);

    Slice<CorTax> findSliceByChannel_Organization_Shortcut(String organizationShortcut, Pageable pageable);

    CorTax findOneByIdAndChannel_Organization_Shortcut(Long id, String organizationShortcut);

    void deleteByIdAndChannel_Organization_Shortcut(Long id, String organizationShortcut);

}
