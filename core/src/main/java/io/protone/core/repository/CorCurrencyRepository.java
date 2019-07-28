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
    List<CorCurrency> findByNetwork(CorNetwork corNetwork);

    Slice<CorCurrency> findSliceByNetwork_Shortcut(String network, Pageable pageable);

    void deleteByIdAndNetwork_Shortcut(Long id, String network);

    CorCurrency findOneByIdAndNetwork_Shortcut(Long id, String corNetwork);
}
