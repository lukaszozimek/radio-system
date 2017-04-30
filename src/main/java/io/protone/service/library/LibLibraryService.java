package io.protone.service.library;

import io.protone.web.rest.mapper.LibLibraryMapper;
import io.protone.domain.LibLibrary;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.library.LibLibraryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class LibLibraryService {

    private final Logger log = LoggerFactory.getLogger(LibLibraryService.class);

    @Inject
    private CorNetworkRepository networkRepository;

    @Inject
    private LibLibraryRepository libraryRepository;

    @Inject
    private LibLibraryMapper customLibLibraryMapper;

    public List<LibLibrary> findLibraries(String networkShortcut, Pageable pageable) {
        return libraryRepository.findAllByNetwork_Shortcut(networkShortcut, pageable);
    }

    public LibLibrary findLibrary(String networkShortcut, String libraryShortcut) {
        LibLibrary result = libraryRepository.findOneByNetwork_ShortcutAndShortcut(networkShortcut, libraryShortcut);
        return result;
    }

    public void deleteLibrary(String libraryShortcut, String networkShortcut) {
        libraryRepository.deleteByShortcutAndNetwork_Shortcut(libraryShortcut, networkShortcut);
    }

    public LibLibrary createOrUpdateLibrary(LibLibrary libLibrary) {
        log.debug("Persisting LibLibrary: {}", libLibrary);
        libLibrary = libraryRepository.saveAndFlush(libLibrary);

        return libLibrary;
    }


}
