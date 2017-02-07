package io.protone.repository;

import io.protone.domain.TraInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TraInvoice entity.
 */
@SuppressWarnings("unused")
public interface TraInvoiceRepository extends JpaRepository<TraInvoice,Long> {

}
