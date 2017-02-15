package io.protone.repository.custom;

import io.protone.domain.CorChannel;
import io.protone.domain.SchPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the SchPlaylist entity.
 */
@SuppressWarnings("unused")
public interface CustomSchPlaylistRepository extends JpaRepository<SchPlaylist,Long> {
    List<SchPlaylist> findByChannel(CorChannel channel);
    Optional<SchPlaylist> findByChannelAndDate(CorChannel channel, LocalDate date);
}
