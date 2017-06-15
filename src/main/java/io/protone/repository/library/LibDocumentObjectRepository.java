package io.protone.repository.library;

import io.protone.domain.LibAudioObject;
import io.protone.domain.LibDocumentObject;
import io.protone.domain.LibMediaItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the LibDocumentObject entity.
 */
@SuppressWarnings("unused")
public interface LibDocumentObjectRepository extends JpaRepository<LibDocumentObject,Long> {

    List<LibDocumentObject> findByMediaItem(LibMediaItem itemDB);
}
