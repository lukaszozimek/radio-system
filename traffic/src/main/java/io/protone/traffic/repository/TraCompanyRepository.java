package io.protone.traffic.repository;

import io.protone.traffic.domain.TraCompany;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lukaszozimek on 11/08/2017.
 */
public interface TraCompanyRepository extends JpaRepository<TraCompany, Long> {
    List<TraCompany> findAllByNetwork_Shortcut(String corNetwork, Pageable pageable);

    TraCompany findOneByIdAndNetwork_Shortcut(Long id, String corNetwork);

    void deleteByIdAndNetwork_Shortcut(Long id, String corNetwork);
}
