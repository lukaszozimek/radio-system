package io.protone.traffic.repository;


import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmDiscount;
import io.protone.traffic.domain.TraCompany;
import io.protone.traffic.domain.TraPrice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraPrice entity.
 */
@SuppressWarnings("unused")
public interface TraPriceRepository extends JpaRepository<TraPrice, Long> {
    TraPrice findOneByIdAndNetwork_Shortcut(Long id, String corNetwork);

    List<TraPrice> findAllByNetwork_Shortcut(String corNetwork, Pageable pageable);

    void deleteByIdAndNetwork_Shortcut(Long id, String corNetwork);
}
