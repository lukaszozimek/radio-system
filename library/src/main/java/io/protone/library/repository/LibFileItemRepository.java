package io.protone.library.repository;


import io.protone.library.domain.LibFileItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibFileItem entity.
 */
@SuppressWarnings("unused")
public interface LibFileItemRepository extends JpaRepository<LibFileItem, Long> {

    LibFileItem findOneByIdAndNetwork_ShortcutAndChannel_Shortcut(LibFileItem itemDB, String networkShortcut, String channelShortcut);

    Slice<LibFileItem> findSliceByNetwork_ShortcutAndLibrary_Shortcut(String networkShortcut, String libraryDB, Pageable pageable);

    LibFileItem findByNetwork_ShortcutAndLibrary_ShortcutAndIdx(String networkShortcut, String libraryDB, String idx);
}
