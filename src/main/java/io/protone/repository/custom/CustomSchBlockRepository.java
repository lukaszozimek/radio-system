package io.protone.repository.custom;

import io.protone.domain.SchBlock;
import io.protone.domain.SchPlaylist;
import io.protone.domain.SchTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the SchBlock entity.
 */
@SuppressWarnings("unused")
public interface CustomSchBlockRepository extends JpaRepository<SchBlock,Long> {
    List<SchBlock> findByPlaylistAndTemplateAndParentBlock(SchPlaylist playlist, SchTemplate template, SchBlock parentBlock);
}
