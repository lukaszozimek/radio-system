package io.protone.repository;

import io.protone.domain.CORArea;
import io.protone.domain.CORChannel;

import io.protone.domain.CORNetwork;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORChannel entity.
 */
@SuppressWarnings("unused")
public interface CCORChannelRepository extends JpaRepository<CORChannel, Long> {

    List<CORChannel> findByCORNetwork(CORNetwork network);

    CORChannel findByPrefix(String shortcut);

    void deleteByPrefix(String shortcut);
}
