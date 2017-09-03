package io.protone.library.service;


import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.core.service.CorImageItemService;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.mapper.LibLibraryMapper;
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
    private LibLibraryMapper customLibLibraryMapper;

    @Inject
    private S3Client s3Client;

    @Inject
    private CorImageItemService corImageItemService;

    public Slice<LibFileLibrary> findLibraries(String networkShortcut, Pageable pageable) {
        return libFileLibraryRepository.findSliceByNetwork_Shortcut(networkShortcut, pageable);
    }

    public LibFileLibrary findLibrary(String networkShortcut, String libraryShortcut) {
        return libFileLibraryRepository.findOneByNetwork_ShortcutAndShortcut(networkShortcut, libraryShortcut);
    }

    public void deleteLibrary(String libraryShortcut, String networkShortcut) {
        libFileLibraryRepository.deleteByShortcutAndNetwork_Shortcut(libraryShortcut, networkShortcut);
    }

    public Slice<LibFileLibrary> findLibrariesByChannel(String networkShortcut, String channelShortcut, Pageable pageable) {
        return libFileLibraryRepository.findSliceByNetwork_ShortcutAndChannels_ShortcutIn(networkShortcut, channelShortcut, pageable);
    }

    public LibFileLibrary findLibraryByChannel(String networkShortcut, String channelShortcut, String libraryShortcut) {
        return libFileLibraryRepository.findOneByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(networkShortcut, channelShortcut, libraryShortcut);
    }

    public void deleteLibrary(String libraryShortcut, String channelShortcut, String networkShortcut) {
        libFileLibraryRepository.deleteByNetwork_ShortcutAndChannels_ShortcutInAndShortcut(networkShortcut, channelShortcut, libraryShortcut);
    }

    public LibFileLibrary createOrUpdateLibrary(LibFileLibrary libLibrary) throws CreateBucketException {
        log.debug("Persisting LibFileLibrary: {}", libLibrary);
        s3Client.makeBucket(libLibrary.getNetwork().getShortcut(), FILE + libLibrary.getShortcut());
        libLibrary = libFileLibraryRepository.saveAndFlush(libLibrary);
        return libLibrary;
    }
}
