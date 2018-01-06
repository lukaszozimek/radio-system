package io.protone.core.repository;

import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorOrganization;
import io.protone.core.domain.CorTax;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the CorTax entity.
 */
@SuppressWarnings("unused")
public interface CorOrganizationRepository extends JpaRepository<CorOrganization, Long> {

    CorOrganization findOneByShortcut(String organizationShortcut);

    Optional<CorOrganization> findOneByShortcutOrName(String shortcut, String name);

}
