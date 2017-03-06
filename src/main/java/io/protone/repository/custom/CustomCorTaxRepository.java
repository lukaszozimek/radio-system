package io.protone.repository.custom;

import java.util.*;

import io.protone.domain.CorNetwork;
import io.protone.domain.CorTax;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorTax entity.
 */
@SuppressWarnings("unused")
public interface CustomCorTaxRepository extends JpaRepository<CorTax, Long> {
    List<CorTax> findByNetwork(CorNetwork corNetwork);
    CorTax findOneByIdAndNetwork(Long id, CorNetwork corNetwork);

}
