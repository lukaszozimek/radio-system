package io.protone.traffic.repository;

import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmAccount;
import io.protone.traffic.domain.TraInvoice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TraInvoice entity.
 */
@SuppressWarnings("unused")
public interface TraInvoiceRepository extends JpaRepository<TraInvoice, Long> {
    List<TraInvoice> findByNetwork(CorNetwork network);

    @Query("select i  from TraInvoice as i " +
            "left join fetch i.network as n " +
            "left join fetch i.customer as c " +
            "left join fetch c.addres as ca " +
            "left join fetch c.range as cr " +
            "left join fetch c.area as car " +
            "left join fetch c.size as css " +
            " where n.shortcut = :network")
    List<TraInvoice> findAllByNetwork_Shortcut(@Param("network") String network, Pageable pageable);

    void deleteByIdAndNetwork_Shortcut(Long id, String network);

    void deleteByCustomerAndNetwork_Shortcut(CrmAccount crmAccount, String network);

    TraInvoice findByIdAndNetwork_Shortcut(Long id, String network);

    List<TraInvoice> findAllByCustomer_ShortNameAndNetwork_Shortcut(String customer, String network, Pageable pageable);

}
