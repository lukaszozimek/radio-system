package io.protone.repository.cor;

import java.util.*;

import io.protone.domain.CorNetwork;
import io.protone.domain.CorTax;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorTax entity.
 */
@SuppressWarnings("unused")
public interface CorTaxRepository extends JpaRepository<CorTax, Long> {
    List<CorTax> findByNetwork(CorNetwork corNetwork);

    List<CorTax> findAllByNetwork(CorNetwork network, Pageable pageable);

    CorTax findOneByIdAndNetwork(Long id, CorNetwork corNetwork);

}
