package io.protone.library.repository;


import io.protone.library.domain.LibLabel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibLabel entity.
 */
@SuppressWarnings("unused")
public interface LibLabelRepository extends JpaRepository<LibLabel, Long> {

    Slice<LibLabel> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(String organizationShortcut, String channelShortcut, Pageable pagable);

    LibLabel findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organizationShortcut, String channelShortcut);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organizationShortcut, String channelShortcut);
}
