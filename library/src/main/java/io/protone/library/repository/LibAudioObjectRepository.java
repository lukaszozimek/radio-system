package io.protone.library.repository;


import io.protone.library.domain.LibAudioObject;
import io.protone.library.domain.LibMediaItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the LibAudioObject entity.
 */
@SuppressWarnings("unused")
public interface LibAudioObjectRepository extends JpaRepository<LibAudioObject,Long> {
    List<LibAudioObject> findByMediaItem(LibMediaItem mediaItem);
}
