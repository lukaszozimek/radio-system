package io.protone.repository;

import io.protone.domain.TRAInvoice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TRAInvoice entity.
 */
@SuppressWarnings("unused")
public interface TRAInvoiceRepository extends JpaRepository<TRAInvoice,Long> {

}
