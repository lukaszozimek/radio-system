package io.protone.repository;

import io.protone.domain.CorNetwork;
import io.protone.domain.TraInvoiceStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraInvoiceStatus entity.
 */
@SuppressWarnings("unused")
public interface TraInvoiceStatusRepository extends JpaRepository<TraInvoiceStatus,Long> {

    List<TraInvoiceStatus> findByNetwork(CorNetwork corNetwork);

    TraInvoiceStatus findOneByIdAndNetwork(Long id, CorNetwork corNetwork);

}
