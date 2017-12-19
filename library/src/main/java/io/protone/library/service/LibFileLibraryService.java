package io.protone.library.service;


import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.core.service.CorImageItemService;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.mapper.LibLibraryMediaMapper;
import io.protone.library.repository.LibFileLibraryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static io.protone.core.constans.MinioFoldersConstants.FILE;

@Service
@Transactional
public class LibFileLibraryService {

    private final Logger log = LoggerFactory.getLogger(LibFileLibraryService.class);

    @Inject
    private CorNetworkRepository networkRepository;

    @Inject
    private LibFileLibraryRepository libFileLibraryRepository;

    @Inject
    private LibLibraryMediaMapper customLibLibraryMediaMapper;

    @Inject
    private S3Client s3Client;

    @Inject
    private CorImageItemService corImageItemService;

    public Slice<LibFileLibrary> findLibraries(String organizationShortcut, Pageable pageable) {
        return libFileLibraryRepository.findSliceByNetwork_Shortcut(organizationShortcut, pageable);
    }

    public LibFileLibrary findLibrary(String organizationShortcut, String libraryShortcut) {
        return libFileLibraryRepository.findOneByNetwork_ShortcutAndShortcut(organizationShortcut, libraryShortcut);
    }

    public void deleteLibrary(String libraryShortcut, String organizationShortcut) {
        libFileLibraryRepository.deleteByShortcutAndNetwork_Shortcut(libraryShortcut, organizationShortcut);
    }

    public Slice<LibFileLibrary> findLibrariesByChannel(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return libFileLibraryRepository.findSliceByNetwork_ShortcutAndChannels_ShortcutIn(organizationShortcut, channelShortcut, pageable);
    }

    public LibFileLibrary findLibraryByChannel(String organizationShortcut, String channelShortcut, String libraryShortcut) {
        return libFileLibraryRepository.findOneByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(organizationShortcut, channelShortcut, libraryShortcut);
    }

    public void deleteLibrary(String libraryShortcut, String channelShortcut, String organizationShortcut) {
        libFileLibraryRepository.deleteByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(organizationShortcut, channelShortcut, libraryShortcut);
    }

    public LibFileLibrary createOrUpdateLibrary(LibFileLibrary libLibrary) throws CreateBucketException {
        log.debug("Persisting LibFileLibrary: {}", libLibrary);
        s3Client.makeBucket(libLibrary.getNetwork().getShortcut(), FILE + libLibrary.getShortcut());
        libLibrary = libFileLibraryRepository.saveAndFlush(libLibrary);
        return libLibrary;
    }
}
