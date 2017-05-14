package io.protone.repository.library;

import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmTask;
import io.protone.domain.LibLibrary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the LibLibrary entity.
 */
@SuppressWarnings("unused")
public interface LibLibraryRepository extends JpaRepository<LibLibrary, Long> {
    LibLibrary findOneByNetwork_ShortcutAndShortcut(String network, String shortcut);

    LibLibrary findOneByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(String network, String corChannel, String shortcut);

    List<LibLibrary> findByNetwork(CorNetwork network);

    List<LibLibrary> findAllByNetwork_Shortcut(String network, Pageable pageable);

    List<LibLibrary> findAllByNetwork_ShortcutAndChannels_ShortcutIn(String network, String corChannel, Pageable pageable);

    void deleteByShortcutAndNetwork_Shortcut(String shortcut, String network);

    void deleteByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(String shortcut, String channelShortcut, String network);
}
