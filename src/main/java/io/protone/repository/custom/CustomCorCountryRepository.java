package io.protone.repository.custom;

import io.protone.domain.CorCountry;

import io.protone.domain.CorCurrency;
import io.protone.domain.CorNetwork;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorCountry entity.
 */
@SuppressWarnings("unused")
public interface CustomCorCountryRepository extends JpaRepository<CorCountry, Long> {
    List<CorCountry> findByNetwork(CorNetwork corNetwork);
    CorCountry findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}
