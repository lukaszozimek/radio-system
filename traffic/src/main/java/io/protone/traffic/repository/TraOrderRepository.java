package io.protone.traffic.repository;


import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmAccount;
import io.protone.traffic.domain.TraOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Spring Data JPA repository for the TraOrder entity.
 */
@SuppressWarnings("unused")
public interface TraOrderRepository extends JpaRepository<TraOrder, Long> {
    List<TraOrder> findByNetwork(CorNetwork network);


    @Query("select o  from TraOrder as o " +
            "left join fetch o.network as n " +
            "left join fetch o.advertisment as a " +
            "left join fetch o.status as stat " +
            "left join fetch o.campaign as camp " +
            "left join fetch o.customer as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            " where n.shortcut = :network")
    Slice<TraOrder> findSliceByNetwork_Shortcut(@Param("network") String network, Pageable pageable);

    @Query("select o  from TraOrder as o " +
            "left join fetch o.network as n " +
            "left join fetch o.advertisment as a " +
            "left join fetch o.status as stat " +
            "left join fetch o.campaign as camp " +
            "left join fetch o.customer as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            " where n.shortcut = :network and c.shortName=:shortName")
    Slice<TraOrder> findSliceByCustomer_ShortNameAndNetwork_Shortcut(@Param("shortName") String crmAccount, @Param("network") String corNetwork, Pageable pageable);

    List<TraOrder> findByCustomer_ShortNameAndNetwork_Shortcut(String crmAccount, String corNetwork);

    @Query("select o  from TraOrder as o " +
            "left join fetch o.network as n " +
            "left join fetch o.advertisment as a " +
            "left join fetch o.status as stat " +
            "left join fetch o.campaign as camp " +
            "left join fetch o.emissions as em " +
            "left join fetch o.customer as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            "left join fetch o.customer as c where n.shortcut = :network and o.id =:id")
    TraOrder findByIdAndNetwork_Shortcut(@Param("id") Long id, @Param("network") String corNetwork);

    void deleteByIdAndNetwork_Shortcut(Long id, String corNetwork);

    void deleteByCustomerAndNetwork_Shortcut(CrmAccount customer, String corNetwork);
}
