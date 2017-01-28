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

    List<CORChannel> findByNetwork(CORNetwork network);

    CORChannel findByShortcut(String shortcut);

    void deleteByShortcut(String shortcut);
}
