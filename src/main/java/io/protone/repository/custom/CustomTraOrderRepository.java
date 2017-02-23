package io.protone.repository.custom;

import io.protone.domain.CorNetwork;
import io.protone.domain.TraOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraOrder entity.
 */
@SuppressWarnings("unused")
public interface CustomTraOrderRepository extends JpaRepository<TraOrder,Long> {
    List<TraOrder> findByNetwork(CorNetwork network);
}
