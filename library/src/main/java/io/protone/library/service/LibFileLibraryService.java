package io.protone.library.service;


import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.core.service.CorImageItemService;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.repository.LibFileLibraryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static io.protone.core.constans.MinioFoldersConstants.FILE;
import static io.protone.library.service.LibLibraryMediaService.LIBRARY_SEPARATOR;

@Service
@Transactional
public class LibFileLibraryService {

    private final Logger log = LoggerFactory.getLogger(LibFileLibraryService.class);

    @Inject
    private LibFileLibraryRepository libFileLibraryRepository;

    @Inject
    private S3Client s3Client;

    @Inject
    private CorImageItemService corImageItemService;

    public Slice<LibFileLibrary> findLibrariesByChannel(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return libFileLibraryRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pageable);
    }

    public LibFileLibrary findLibraryByChannel(String organizationShortcut, String channelShortcut, String libraryShortcut) {
        return libFileLibraryRepository.findOneByChannel_Organization_ShortcutAndChannel_ShortcutAndShortcut(organizationShortcut, channelShortcut, libraryShortcut);
    }

    public void deleteLibrary(String libraryShortcut, String channelShortcut, String organizationShortcut) {
        libFileLibraryRepository.deleteByChannel_Organization_ShortcutAndChannel_ShortcutAndShortcut(organizationShortcut, channelShortcut, libraryShortcut);
    }

    public LibFileLibrary createOrUpdateLibrary(LibFileLibrary libLibrary) throws CreateBucketException {
        log.debug("Persisting LibFileLibrary: {}", libLibrary);
        s3Client.makeBucket(libLibrary.getChannel().getOrganization().getShortcut() + LIBRARY_SEPARATOR + libLibrary.getChannel().getShortcut(), FILE + libLibrary.getShortcut());
        libLibrary = libFileLibraryRepository.saveAndFlush(libLibrary);
        return libLibrary;
    }
}
