package io.protone.traffic.repository;


import io.protone.traffic.domain.TraPrice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the TraPrice entity.
 */
@SuppressWarnings("unused")
public interface TraPriceRepository extends JpaRepository<TraPrice, Long> {
    @Query("select p from TraPrice as p " +
            "left join fetch p.lenghtMultiplier as lM " +
            "left join fetch p.network as n " +
            " where n.shortcut = :network and p.id =:id")
    TraPrice findOneByIdAndNetwork_Shortcut(@Param("id") Long id, @Param("network") String corNetwork);

    @Query("select p from TraPrice as p " +
            "left join fetch p.lenghtMultiplier as lM " +
            "left join fetch p.network as n " +
            " where n.shortcut = :network")
    Slice<TraPrice> findSliceByNetwork_Shortcut(@Param("network") String corNetwork, Pageable pageable);

    void deleteByIdAndNetwork_Shortcut(Long id, String corNetwork);
}
