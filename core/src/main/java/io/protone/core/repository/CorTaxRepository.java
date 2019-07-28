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
    List<CorTax> findByNetwork(CorNetwork corNetwork);

    Slice<CorTax> findSliceByNetwork_Shortcut(String network, Pageable pageable);

    CorTax findOneByIdAndNetwork_Shortcut(Long id, String corNetwork);

    void deleteByIdAndNetwork_Shortcut(Long id, String corNetwork);

}
