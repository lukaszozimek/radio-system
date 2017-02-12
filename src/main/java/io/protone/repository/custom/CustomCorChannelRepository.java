package io.protone.repository.custom;

import io.protone.domain.CorChannel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorChannel entity.
 */
@SuppressWarnings("unused")
public interface CustomCorChannelRepository extends JpaRepository<CorChannel, Long> {
    CorChannel findOneByShortcut(String shortcut);

    void deleteByShortcut(String shortcut);
}
