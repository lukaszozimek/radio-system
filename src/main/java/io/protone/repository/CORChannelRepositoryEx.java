package io.protone.repository;

import io.protone.domain.CORChannel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CORChannel entity.
 */
@SuppressWarnings("unused")
public interface CORChannelRepositoryEx extends JpaRepository<CORChannel,Long> {
    CORChannel findByShortcut(String shortcut);
    void deleteByShortcut(String shortcut);
}
