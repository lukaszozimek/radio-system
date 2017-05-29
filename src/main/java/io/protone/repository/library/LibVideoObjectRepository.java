package io.protone.repository.library;

import io.protone.domain.LibAudioObject;
import io.protone.domain.LibMediaItem;
import io.protone.domain.LibVideoObject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LibVideoObject entity.
 */
@SuppressWarnings("unused")
public interface LibVideoObjectRepository extends JpaRepository<LibVideoObject,Long> {

    List<LibVideoObject> findByMediaItem(LibMediaItem libMediaItem);
}
