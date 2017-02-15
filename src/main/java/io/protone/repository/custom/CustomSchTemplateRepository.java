package io.protone.repository.custom;

import io.protone.domain.CorChannel;
import io.protone.domain.SchTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the SchTemplate entity.
 */
@SuppressWarnings("unused")
public interface CustomSchTemplateRepository extends JpaRepository<SchTemplate,Long> {
    List<SchTemplate> findByChannel(CorChannel channel);
    Optional<SchTemplate> findByChannelAndName(CorChannel channel, String name);
}
