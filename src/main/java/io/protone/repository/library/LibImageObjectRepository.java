package io.protone.repository.library;

import io.protone.domain.LibAudioObject;
import io.protone.domain.LibImageObject;

import io.protone.domain.LibMediaItem;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LibImageObject entity.
 */
@SuppressWarnings("unused")
public interface LibImageObjectRepository extends JpaRepository<LibImageObject,Long> {

  //  List<LibImageObject> findByMediaItem(LibMediaItem itemDB);
}
