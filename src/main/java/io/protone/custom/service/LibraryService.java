package io.protone.custom.service;

import io.protone.domain.CORNetwork;
import io.protone.domain.LIBLibrary;
import io.protone.repository.CCORNetworkRepository;
import io.protone.repository.LIBLibraryRepositoryEx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {

    @Inject
    CCORNetworkRepository networkRepository;

    @Inject
    private LIBLibraryRepositoryEx libraryRepository;

    public List<LIBLibrary> findByNetworkShortcut(String networkShortcut) {
        List<LIBLibrary> results = new ArrayList<>();
        CORNetwork network = networkRepository.findByShortcut(networkShortcut);
        if (network != null)
            results = libraryRepository.findByNetwork(network);
        return results;
    }

    public LIBLibrary findByNetworkShortcutAndLibraryShortcut(String networkShortcut, String libraryShortcut) {
        LIBLibrary result = null;
        CORNetwork network = networkRepository.findByShortcut(networkShortcut);
        if (network != null)
            result = libraryRepository.findOneByNetworkAndShortcut(network, libraryShortcut).get();
        return result;
    }

    @Transactional
    public void deleteLibraryByNetworkShortcutAndLibraryShortcut(String networkShortcut, String libraryShortcut) {
        LIBLibrary libraryToDelete = findByNetworkShortcutAndLibraryShortcut(networkShortcut, libraryShortcut);
        if (libraryToDelete != null)
            libraryRepository.delete(libraryToDelete);
    }
}
