package io.protone.repository.custom;

import io.protone.domain.LibAudioObject;
import io.protone.domain.LibMediaItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the LibAudioObject entity.
 */
@SuppressWarnings("unused")
public interface CustomLibAudioObjectRepository extends JpaRepository<LibAudioObject,Long> {
    List<LibAudioObject> findByMediaItem(LibMediaItem mediaItem);
}
