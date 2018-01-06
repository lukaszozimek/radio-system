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

    LibFileItem findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(LibFileItem itemDB, String organizationShortcut, String channelShortcut);

    Slice<LibFileItem> findSliceByChannel_Organization_ShortcutAndChannel_ShortcutAndLibrary_Shortcut(String organizationShortcut, String channelShortcut, String libraryDB, Pageable pageable);

    LibFileItem findByChannel_Organization_ShortcutAndChannel_ShortcutAndLibrary_ShortcutAndIdx(String organizationShortcut, String channelShortcut, String libraryDB, String idx);
}
