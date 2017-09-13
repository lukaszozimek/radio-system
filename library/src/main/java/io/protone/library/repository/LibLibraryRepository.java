package io.protone.library.repository;


import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaLibrary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the LibMediaLibrary entity.
 */
@SuppressWarnings("unused")
public interface LibLibraryRepository extends JpaRepository<LibMediaLibrary, Long> {
    LibMediaLibrary findOneByNetwork_ShortcutAndShortcut(String network, String shortcut);

    LibMediaLibrary findOneByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(String network, String corChannel, String shortcut);

    List<LibMediaLibrary> findByNetwork(CorNetwork network);

    Slice<LibMediaLibrary> findSliceByNetwork_Shortcut(String network, Pageable pageable);

    Slice<LibMediaLibrary> findSliceByNetwork_ShortcutAndChannels_ShortcutIn(String network, String corChannel, Pageable pageable);

    void deleteByShortcutAndNetwork_Shortcut(String shortcut, String network);

    void deleteByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(String shortcut, String channelShortcut, String network);
}
