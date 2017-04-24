package io.protone.repository.custom;

import io.protone.domain.CorNetwork;
import io.protone.domain.TraInvoice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraInvoice entity.
 */
@SuppressWarnings("unused")
public interface CustomTraInvoiceRepository extends JpaRepository<TraInvoice, Long> {
    List<TraInvoice> findByNetwork(CorNetwork network);

    List<TraInvoice> findAllByNetwork(CorNetwork network, Pageable pageable);
}
