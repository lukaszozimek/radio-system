package io.protone.custom.utils;

import io.protone.custom.service.LibraryService;
import io.protone.domain.LIBLibrary;
import io.protone.domain.LIBMediaItem;
import io.protone.repository.LIBLibraryRepository;
import io.protone.repository.LIBMediaItemRepositoryEx;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by grzesiek on 03.02.2017.
 */
@Service
public class MediaUtils {

    @Inject
    LIBLibraryRepository libraryRepository;

    @Inject
    LibraryService libraryService;

    @Inject
    LIBMediaItemRepositoryEx itemRepository;

    public String generateIdx(LIBLibrary currentLibrary) {
        currentLibrary.counter(currentLibrary.getCounter() + 1);
        libraryRepository.save(currentLibrary);
        return String.format("%s%05d", currentLibrary.getPrefix(), currentLibrary.getCounter());
    }

    public LIBMediaItem getItemFromDB(String networkShortcut, String libraryShortcut, String idx) {
        LIBMediaItem result = null;
        LIBLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return result;

        return itemRepository.findByLibraryAndIdx(libraryDB, idx).get();
    }
}
