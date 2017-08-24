package io.protone.library.repository;


import io.protone.library.domain.LibLabel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibLabel entity.
 */
@SuppressWarnings("unused")
public interface LibLabelRepository extends JpaRepository<LibLabel,Long> {

    Slice<LibLabel> findSliceByNetwork_Shortcut(String networkShortcut, Pageable pagable);

    LibLabel findOneByIdAndNetwork_Shortcut(Long id, String networkShortcut);
}
