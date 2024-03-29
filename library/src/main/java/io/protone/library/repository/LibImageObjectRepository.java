package io.protone.library.repository;


import io.protone.library.domain.LibImageObject;
import io.protone.library.domain.LibMediaItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the LibImageObject entity.
 */
@SuppressWarnings("unused")
public interface LibImageObjectRepository extends JpaRepository<LibImageObject, Long> {
    List<LibImageObject> findByMediaItem(LibMediaItem itemDB);

}
