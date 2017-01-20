package io.protone.repository;

import io.protone.domain.CORNetwork;
import io.protone.domain.LIBLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface LIBLibraryRepositoryEx extends JpaRepository<LIBLibrary,Long> {
    List<LIBLibrary> findByNetwork(CORNetwork network);
    Optional<LIBLibrary> findOneByNetworkAndShortcut(CORNetwork network, String shortcut);
}
