package io.protone.repository;

import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorChannel entity.
 */
@SuppressWarnings("unused")
public interface CorChannelRepository extends JpaRepository<CorChannel, Long> {
    CorChannel findOneByPrefix(String prefix);

    void deleteByPrefix(String prefix);
}
