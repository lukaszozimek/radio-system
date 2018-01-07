package io.protone.core.repository;


import io.protone.core.domain.CorNetwork;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the CorNetwork entity.
 */
@SuppressWarnings("unused")
public interface CorNetworkRepository extends JpaRepository<CorNetwork, Long> {
    List<CorNetwork> findAllByOrganization_Shortcut(String organizationShortcut);

    CorNetwork findOneByShortcutAndOrganization_Shortcut(String shortcut, String organizationShortcut);

    void deleteByShortcutAndOrganization_Shortcut(String shortcut, String organizationShortcut);

    Optional<CorNetwork> findOneByShortcutOrNameAndOrganization_Shortcut(String shortcut, String name, String organizationShortcut);
}
