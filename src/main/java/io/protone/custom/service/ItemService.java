package io.protone.custom.service;

import io.protone.config.s3.S3Client;
import io.protone.custom.consts.GKAssociationConstants;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.custom.service.mapper.CustomCORChannelMapper;
import io.protone.custom.service.mapper.ext.CustomCORUserMapperExt;
import io.protone.custom.service.mapper.ext.CustomLIBLibraryMapperExt;
import io.protone.domain.*;
import io.protone.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Inject
    S3Client s3Client;

    @Inject
    LibraryService libraryService;

    @Inject
    LIBMediaItemRepositoryEx itemRepository;

    public List<LIBMediaItem> findItems(String networkShortcut, String libraryShortcut) {
        List<LIBMediaItem> results = new ArrayList<>();
        LIBLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return results;
        results = itemRepository.findByLibrary(libraryDB);
        return results;
    }

    public LIBMediaItem findItem(String networkShortcut, String libraryShortcut, String idx) {
        LIBMediaItem result = null;
        LIBLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return result;
        result = itemRepository.findByLibraryAndIdx(libraryDB, idx).get();
        return result;
    }

    @Transactional
    public void deleteItem(String networkShortcut, String libraryShortcut, String idx) {
        LIBMediaItem itemToDelete = findItem(networkShortcut, libraryShortcut, idx);
        if (itemToDelete != null) {
            itemRepository.delete(itemToDelete);
            itemRepository.flush();
        }
    }
}
