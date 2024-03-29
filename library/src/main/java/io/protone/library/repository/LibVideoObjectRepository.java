package io.protone.library.repository;


import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibVideoObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the LibVideoObject entity.
 */
@SuppressWarnings("unused")
public interface LibVideoObjectRepository extends JpaRepository<LibVideoObject,Long> {

    List<LibVideoObject> findByMediaItem(LibMediaItem libMediaItem);
}
