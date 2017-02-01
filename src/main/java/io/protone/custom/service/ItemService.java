package io.protone.custom.service;

import io.protone.config.s3.S3Client;
import io.protone.custom.consts.GKAssociationConstants;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.custom.service.mapper.CustomCORChannelMapper;
import io.protone.custom.service.mapper.ext.CustomCORUserMapperExt;
import io.protone.custom.service.mapper.ext.CustomItemMapperExt;
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

    @Inject
    CustomItemMapperExt itemMapper;

    public LibItemPT getItem(String networkShortcut, String libraryShortcut, String idx) {
        LibItemPT result = null;
        LIBLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return result;

        LIBMediaItem itemDB = itemRepository.findByLibraryAndIdx(libraryDB, idx).get();
        result = itemMapper.DB2DTO(itemDB);
        return result;
    }

    public List<LibItemPT> getItem(String networkShortcut, String libraryShortcut) {
        List<LibItemPT> results = new ArrayList<>();
        LIBLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return results;

        List<LIBMediaItem> itemsDB = itemRepository.findByLibrary(libraryDB);
        results = itemMapper.DBs2DTOs(itemsDB);
        return results;
    }

    @Transactional
    public void deleteItem(String networkShortcut, String libraryShortcut, String idx) {
        LibItemPT dto = getItem(networkShortcut, libraryShortcut, idx);
        LIBMediaItem itemToDelete = itemMapper.DTO2DB(dto);
        if (itemToDelete != null) {
            itemRepository.delete(itemToDelete);
            itemRepository.flush();
        }
    }
}
