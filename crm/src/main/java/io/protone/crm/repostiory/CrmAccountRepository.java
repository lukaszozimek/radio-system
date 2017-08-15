package io.protone.crm.repostiory;


import io.protone.crm.domain.CrmAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmAccount entity.
 */
@SuppressWarnings("unused")
public interface CrmAccountRepository extends JpaRepository<CrmAccount, Long> {

    @Query("select a from CrmAccount as a " +
            "left join fetch a.network as n " +
            "left join fetch a.addres as adr " +
            "left join fetch a.area as ar " +
            "left join fetch a.size as s " +
            "left join fetch a.range as r " +
            "left join fetch a.industry as ind " +
            "left join fetch a.discount as disc "+
            "left join fetch a.corImageItem as image " +
            "left join fetch a.keeper as kep  " +
            "left join fetch a.tasks as tasks " +
            "left join fetch a.person as p where n.shortcut = :network and a.shortName =:shortName")
    CrmAccount findOneByShortNameAndNetwork_Shortcut(@Param("shortName")String shortName,@Param("network") String network);

    List<CrmAccount> findByNetwork_Shortcut(String network);

    @Query("select a from CrmAccount as a " +
            "left join fetch a.network as n " +
            "left join fetch a.addres as adr " +
            "left join fetch a.area as ar " +
            "left join fetch a.size as s " +
            "left join fetch a.discount as disc "+
            "left join fetch a.range as r " +
            "left join fetch a.industry as ind " +
            "left join fetch a.corImageItem as image " +
            "left join fetch a.keeper as kep  " +
            "left join fetch a.person as p where n.shortcut = :network")
    List<CrmAccount> findAllByNetwork_Shortcut(@Param("network") String network, Pageable pageable);

    void deleteByShortNameAndNetwork_Shortcut(String shortName, String network);
}
