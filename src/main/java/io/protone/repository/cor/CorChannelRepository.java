package io.protone.repository.cor;

import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CorChannel entity.
 */
@SuppressWarnings("unused")
public interface CorChannelRepository extends JpaRepository<CorChannel, Long> {
    CorChannel findOneByNetwork_ShortcutAndShortcut(String network, String shortcut);

    void deleteByShortcutAndNetwork_Shortcut(String shortcut, String network);

    List<CorChannel> findAllByNetwork(CorNetwork network);

    List<CorChannel> findAllByNetwork_Shortcut(String network, Pageable pagable);

}
