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

    @Query("select i  from TraInvoice as i " +
            "left join fetch i.network as n " +
            "left join fetch i.customer as c " +
            "left join fetch c.addres as ica " +
            "left join fetch i.company as comp " +
            "left join fetch i.orders as o " +
            "left join fetch o.advertisment as a " +
            "left join fetch o.status as stat " +
            "left join fetch o.campaign as camp " +
            "left join fetch o.customer as castom " +
            "left join fetch o.emissions as emissions " +
            "left join fetch castom.area as car " +
            "left join fetch castom.size as cs " +
            "left join fetch castom.range as cr " +
            "left join fetch castom.industry as ind " +
            "left join fetch castom.addres as ca " +
            "left join fetch castom.discount as disc " +
            "left join fetch castom.range as cr " +
            "left join fetch castom.area as car " +
            "left join fetch castom.size as css " +
            " where n.shortcut = :network and i.id =:id")
    TraInvoice findByIdAndNetwork_Shortcut(@Param("id") Long id, @Param("network") String network);

    @Query("select i  from TraInvoice as i " +
            "left join fetch i.network as n " +
            "left join fetch i.customer as c " +
            "left join fetch c.addres as ca " +
            "left join fetch c.range as cr " +
            "left join fetch c.area as car " +
            "left join fetch c.size as css " +
            " where n.shortcut = :network and c.shortName =:shortName")
    List<TraInvoice> findAllByCustomer_ShortNameAndNetwork_Shortcut(@Param("shortName") String customer, @Param("network") String network, Pageable pageable);

}
