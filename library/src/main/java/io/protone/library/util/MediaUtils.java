package io.protone.library.util;


import io.protone.library.domain.LibFileLibrary;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibFileLibraryRepository;
import io.protone.library.repository.LibLibraryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by grzesiek on 03.02.2017.
 */
@Service
public class MediaUtils {

    @Inject
    LibLibraryRepository libraryRepository;
    @Inject
    LibFileLibraryRepository libFileLibraryRepository;

    public String generateIdx(LibMediaLibrary currentLibrary) {
        currentLibrary.counter(currentLibrary.getCounter() + 1);
        libraryRepository.save(currentLibrary);
        return String.format("%s%05d", currentLibrary.getPrefix(), currentLibrary.getCounter());
    }

    public String generateIdx(LibFileLibrary currentLibrary) {
        currentLibrary.counter(currentLibrary.getCounter() + 1);
        libFileLibraryRepository.save(currentLibrary);
        return String.format("%s%05d", currentLibrary.getPrefix(), currentLibrary.getCounter());
    }
}
