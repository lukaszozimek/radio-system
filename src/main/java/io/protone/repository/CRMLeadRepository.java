package io.protone.repository;

import io.protone.domain.CORNetwork;
import io.protone.domain.CRMLead;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMLead entity.
 */
@SuppressWarnings("unused")
public interface CRMLeadRepository extends JpaRepository<CRMLead, Long> {

    List<CRMLead> findByNetwork(CORNetwork network);

    CRMLead findOneByShortnameAndNetwork(String shortcut, CORNetwork network);

    void deleteByShortnameAndNetwork(String shortcut, CORNetwork corNetwork);

}
