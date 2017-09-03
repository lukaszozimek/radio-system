package io.protone.library.repository;

import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibFileLibrary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lukaszozimek on 03/09/2017.
 */
public interface LibFileLibraryRepository extends JpaRepository<LibFileLibrary, Long> {
    LibFileLibrary findOneByNetwork_ShortcutAndShortcut(String network, String shortcut);

    LibFileLibrary findOneByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(String network, String corChannel, String shortcut);

    List<LibFileLibrary> findByNetwork(CorNetwork network);

    Slice<LibFileLibrary> findSliceByNetwork_Shortcut(String network, Pageable pageable);

    Slice<LibFileLibrary> findSliceByNetwork_ShortcutAndChannels_ShortcutIn(String network, String corChannel, Pageable pageable);

    void deleteByShortcutAndNetwork_Shortcut(String shortcut, String network);

    void deleteByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(String shortcut, String channelShortcut, String network);
}