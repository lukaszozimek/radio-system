package io.protone.repository.cor;

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
public interface CorCurrencyRepository extends JpaRepository<CorCurrency, Long> {
    List<CorCurrency> findByNetwork(CorNetwork corNetwork);

    List<CorCurrency> findAllByNetwork_Shortcut(String network, Pageable pageable);

    void deleteByIdAndNetwork_Shortcut(Long id, String network);

    CorCurrency findOneByIdAndNetwork_Shortcut(Long id, String corNetwork);
}
