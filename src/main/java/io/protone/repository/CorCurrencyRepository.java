package io.protone.repository;

import io.protone.domain.CorCurrency;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorCurrency entity.
 */
@SuppressWarnings("unused")
public interface CorCurrencyRepository extends JpaRepository<CorCurrency,Long> {

}
