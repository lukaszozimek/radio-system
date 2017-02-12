package io.protone.repository.custom;

import io.protone.domain.CorNetwork;
import io.protone.domain.LibLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the LibLibrary entity.
 */
@SuppressWarnings("unused")
public interface CustomLibLibraryRepository extends JpaRepository<LibLibrary, Long> {
    LibLibrary findOneByNetworkAndShortcut(CorNetwork network, String shortcut);

    List<LibLibrary> findByNetwork(CorNetwork network);
}
