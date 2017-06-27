package io.protone.core.repository;

import io.protone.domain.CorCountry;
import io.protone.domain.CorNetwork;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CorCountry entity.
 */
@SuppressWarnings("unused")
public interface CorCountryRepository extends JpaRepository<CorCountry, Long> {
    List<CorCountry> findByNetwork(CorNetwork corNetwork);

    List<CorCountry> findByNetwork(CorNetwork corNetwork, Pageable pageable);

    CorCountry findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}
