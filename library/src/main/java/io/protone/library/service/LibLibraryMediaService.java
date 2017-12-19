package io.protone.library.service;


import io.protone.core.domain.CorImageItem;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.core.service.CorImageItemService;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.mapper.LibLibraryMediaMapper;
import io.protone.library.repository.LibLibraryRepository;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.IOException;

import static io.protone.core.constans.MinioFoldersConstants.MEDIA_ITEM;

@Service
@Transactional
public class LibLibraryMediaService {

    private final Logger log = LoggerFactory.getLogger(LibLibraryMediaService.class);

    @Inject
    private CorNetworkRepository networkRepository;

    @Inject
    private LibLibraryRepository libraryRepository;

    @Inject
    private LibLibraryMediaMapper customLibLibraryMediaMapper;
    @Inject
    private S3Client s3Client;

    @Inject
    private CorImageItemService corImageItemService;

    public Slice<LibMediaLibrary> findLibraries(String organizationShortcut, Pageable pageable) {
        return libraryRepository.findSliceByNetwork_Shortcut(organizationShortcut, pageable);

    }

    public LibMediaLibrary findLibrary(String organizationShortcut, String libraryShortcut) {
        return libraryRepository.findOneByNetwork_ShortcutAndShortcut(organizationShortcut, libraryShortcut);
    }

    public void deleteLibrary(String libraryShortcut, String organizationShortcut) {
        libraryRepository.deleteByShortcutAndNetwork_Shortcut(libraryShortcut, organizationShortcut);
    }

    public Slice<LibMediaLibrary> findLibrariesByChannel(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return libraryRepository.findSliceByNetwork_ShortcutAndChannels_ShortcutIn(organizationShortcut, channelShortcut, pageable);
    }

    public LibMediaLibrary findLibraryByChannel(String organizationShortcut, String channelShortcut, String libraryShortcut) {
        return libraryRepository.findOneByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(organizationShortcut, channelShortcut, libraryShortcut);
    }

    public void deleteLibrary(String libraryShortcut, String channelShortcut, String organizationShortcut) {
        libraryRepository.deleteByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(organizationShortcut, channelShortcut, libraryShortcut);
    }

    public LibMediaLibrary createOrUpdateLibrary(LibMediaLibrary libMediaLibrary) throws CreateBucketException {
        log.debug("Persisting LibMediaLibrary: {}", libMediaLibrary);
        s3Client.makeBucket(libMediaLibrary.getNetwork().getShortcut(), MEDIA_ITEM + libMediaLibrary.getShortcut());
        libMediaLibrary = libraryRepository.saveAndFlush(libMediaLibrary);
        return libMediaLibrary;
    }

    public LibMediaLibrary createOrUpdateLibraryWithImage(LibMediaLibrary libMediaLibrary, MultipartFile cover) throws CreateBucketException, IOException, TikaException, SAXException {
        CorImageItem imageItem = corImageItemService.saveImageItem(cover);
        log.debug("Persisting LibMediaLibrary: {}", libMediaLibrary);
        s3Client.makeBucket(libMediaLibrary.getNetwork().getShortcut(), MEDIA_ITEM + libMediaLibrary.getShortcut());
        if (imageItem == null) {
            imageItem = corImageItemService.getDefualtImageItem();
        }
        libMediaLibrary = libraryRepository.saveAndFlush(libMediaLibrary.addImageItems(imageItem));
        return libMediaLibrary;
    }

}
