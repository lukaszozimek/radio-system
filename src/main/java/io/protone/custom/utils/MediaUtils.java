package io.protone.custom.utils;

import io.protone.domain.LibLibrary;
import io.protone.repository.LibLibraryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by grzesiek on 03.02.2017.
 */
@Service
public class MediaUtils {

    @Inject
    LibLibraryRepository libraryRepository;

    public String generateIdx(LibLibrary currentLibrary) {
        currentLibrary.counter(currentLibrary.getCounter() + 1);
        libraryRepository.save(currentLibrary);
        return String.format("%s%05d", currentLibrary.getPrefix(), currentLibrary.getCounter());
    }
}
