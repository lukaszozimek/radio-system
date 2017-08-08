package io.protone.traffic.repository;


import io.protone.core.domain.CorNetwork;
import io.protone.traffic.domain.TraAdvertisement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TraAdvertisement entity.
 */
@SuppressWarnings("unused")
public interface TraAdvertisementRepository extends JpaRepository<TraAdvertisement, Long> {
    List<TraAdvertisement> findByNetwork(CorNetwork network);

    @Query("select a  from TraAdvertisement as a " +
            "left join fetch a.network as n " +
            "left join fetch a.customer as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.industry as ind " +
            " where n.shortcut = :network")
    List<TraAdvertisement> findAllByNetwork_Shortcut(@Param("network") String network, Pageable pageable);

    @Query("select a  from TraAdvertisement as a " +
            "left join fetch a.network as n " +
            "left join fetch a.customer as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.industry as ind " +
            " where n.shortcut = :network  and a.id =:id")
    TraAdvertisement findByIdAndNetwork_Shortcut(@Param("id") Long id, @Param("network") String network);

    List<TraAdvertisement> findByCustomer_ShortNameAndNetwork_Shortcut(String crmAccount, String network, Pageable pageable);

    List<TraAdvertisement> findByCustomer_ShortNameAndNetwork_Shortcut(String crmAccount, String network);
}
