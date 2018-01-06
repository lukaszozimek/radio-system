package io.protone.core.repository;


import io.protone.core.domain.CorNetwork;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the CorNetwork entity.
 */
@SuppressWarnings("unused")
public interface CorNetworkRepository extends JpaRepository<CorNetwork, Long> {
    Slice<CorNetwork> findSliceByCorOrganization_Shortcut(String organizationShortcut);

    CorNetwork findOneByShortcutAndCorOrganization_Shortcut(String shortcut, String organizationShortcut);

    void deleteByShortcutAndCorOrganization_Shortcut(String shortcut, String organizationShortcut);

    Optional<CorNetwork> findOneByShortcutOrNameAndCorOrganization_Shortcut(String shortcut, String name, String organizationShortcut);
}
