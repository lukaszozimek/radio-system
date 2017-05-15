package io.protone.repository.traffic;

import io.protone.domain.TraBlock;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraBlock entity.
 */
@SuppressWarnings("unused")
public interface TraBlockRepository extends JpaRepository<TraBlock, Long> {

    List<TraBlock> findAllByNetwork_Shortcut(String shortcut, Pageable pageable);

    TraBlock findOneByIdAndNetwork_Shortcut(Long id, String shortcut);

    void deleteByIdAndNetwork_Shortcut(Long id, String shortcut);

}
