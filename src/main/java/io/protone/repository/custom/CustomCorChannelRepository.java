package io.protone.repository.custom;

import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CorChannel entity.
 */
@SuppressWarnings("unused")
public interface CustomCorChannelRepository extends JpaRepository<CorChannel, Long> {
    CorChannel findOneByShortcut(String shortcut);

    void deleteByShortcut(String shortcut);

    List<CorChannel> findAllByNetwork(CorNetwork network);
}
