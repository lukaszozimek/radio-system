package io.protone.library.service;


import io.protone.core.domain.CorImageItem;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.core.service.CorImageItemService;
import io.protone.library.domain.LibLibrary;
import io.protone.library.mapper.LibLibraryMapper;
import io.protone.library.repository.LibLibraryRepository;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
    @Inject
    private S3Client s3Client;

    @Inject
    private CorImageItemService corImageItemService;

    public List<LibLibrary> findLibraries(String networkShortcut, Pageable pageable) {
        return libraryRepository.findAllByNetwork_Shortcut(networkShortcut, pageable).stream().map(library -> library.mainImage(corImageItemService.getValidLinkToResource(library.getMainImage()))).collect(toList());

    }

    public LibLibrary findLibrary(String networkShortcut, String libraryShortcut) {
        LibLibrary result = libraryRepository.findOneByNetwork_ShortcutAndShortcut(networkShortcut, libraryShortcut);
        if(result!=null){
            result.mainImage(corImageItemService.getValidLinkToResource(result.getMainImage()));
        }
        return result;
    }

    public void deleteLibrary(String libraryShortcut, String networkShortcut) {
        libraryRepository.deleteByShortcutAndNetwork_Shortcut(libraryShortcut, networkShortcut);
    }

    public List<LibLibrary> findLibrariesByChannel(String networkShortcut, String channelShortcut, Pageable pageable) {
        return libraryRepository.findAllByNetwork_ShortcutAndChannels_ShortcutIn(networkShortcut, channelShortcut, pageable).stream().map(library -> library.mainImage(corImageItemService.getValidLinkToResource(library.getMainImage()))).collect(toList());
    }

    public LibLibrary findLibraryByChannel(String networkShortcut, String channelShortcut, String libraryShortcut) {
        LibLibrary result = libraryRepository.findOneByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(networkShortcut, channelShortcut, libraryShortcut);
        if(result!=null){
            result.mainImage(corImageItemService.getValidLinkToResource(result.getMainImage()));
        }
        return result;
    }

    public void deleteLibrary(String libraryShortcut, String channelShortcut, String networkShortcut) {
        libraryRepository.deleteByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(networkShortcut, channelShortcut, libraryShortcut);
    }

    public LibLibrary createOrUpdateLibrary(LibLibrary libLibrary) throws CreateBucketException {
        log.debug("Persisting LibLibrary: {}", libLibrary);
        s3Client.makeBucket(libLibrary.getShortcut());
        libLibrary = libraryRepository.saveAndFlush(libLibrary);
        return libLibrary;
    }

    public LibLibrary createOrUpdateLibraryWithImage(LibLibrary libLibrary, MultipartFile cover) throws CreateBucketException, IOException, TikaException, SAXException {
        CorImageItem imageItem = corImageItemService.saveImageItem(cover);
        log.debug("Persisting LibLibrary: {}", libLibrary);
        s3Client.makeBucket(libLibrary.getShortcut());
        libLibrary = libraryRepository.saveAndFlush(libLibrary.addImageItems(imageItem));
        return libLibrary;
    }

}
