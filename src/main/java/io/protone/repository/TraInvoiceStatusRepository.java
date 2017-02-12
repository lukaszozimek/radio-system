package io.protone.repository;

import io.protone.domain.TraInvoiceStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraInvoiceStatus entity.
 */
@SuppressWarnings("unused")
public interface TraInvoiceStatusRepository extends JpaRepository<TraInvoiceStatus,Long> {

}
