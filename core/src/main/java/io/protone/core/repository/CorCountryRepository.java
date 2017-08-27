package io.protone.core.repository;


import io.protone.core.domain.CorCountry;
import io.protone.core.domain.CorNetwork;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CorCountry entity.
 */
@SuppressWarnings("unused")
public interface CorCountryRepository extends JpaRepository<CorCountry, Long> {
    List<CorCountry> findSliceByNetwork(CorNetwork corNetwork);

    Slice<CorCountry> findSliceByNetwork(CorNetwork corNetwork, Pageable pageable);

    CorCountry findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}
