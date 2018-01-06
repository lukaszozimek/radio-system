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

    LibFileLibrary findOneByChannel_Organization_ShortcutAndChannel_ShortcutAndShortcut(String network, String corChannel, String shortcut);

    Slice<LibFileLibrary> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(String network, String corChannel, Pageable pageable);

    void deleteByChannel_Organization_ShortcutAndChannel_ShortcutAndShortcut(String shortcut, String channelShortcut, String network);
}