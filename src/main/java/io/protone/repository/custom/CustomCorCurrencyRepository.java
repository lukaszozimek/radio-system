package io.protone.repository.custom;

import io.protone.domain.CorCurrency;

import io.protone.domain.CorNetwork;
import io.protone.domain.CorTax;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorCurrency entity.
 */
@SuppressWarnings("unused")
public interface CustomCorCurrencyRepository extends JpaRepository<CorCurrency, Long> {
    List<CorCurrency> findByNetwork(CorNetwork corNetwork);

    List<CorCurrency> findAllByNetwork(CorNetwork network, Pageable pageable);

    CorCurrency findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}
