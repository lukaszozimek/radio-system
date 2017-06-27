package io.protone.traffic.repository;

import io.protone.core.domain.CorNetwork;
import io.protone.traffic.domain.TraInvoice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Spring Data JPA repository for the TraInvoice entity.
 */
@SuppressWarnings("unused")
public interface TraInvoiceRepository extends JpaRepository<TraInvoice, Long> {
    List<TraInvoice> findByNetwork(CorNetwork network);

    List<TraInvoice> findAllByNetwork_Shortcut(String network, Pageable pageable);

    void deleteByIdAndNetwork_Shortcut(Long id, String network);

    TraInvoice findByIdAndNetwork_Shortcut(Long id, String network);

    List<TraInvoice> findAllByCustomer_ShortNameAndNetwork_Shortcut(String customer, String network, Pageable pageable);

}
