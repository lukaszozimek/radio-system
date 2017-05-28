package io.protone.service.library.file.impl;

import io.protone.config.s3.S3Client;
import io.protone.domain.LibImageObject;
import io.protone.repository.library.LibAudioObjectRepository;
import io.protone.repository.library.LibCloudObjectRepository;
import io.protone.repository.library.LibImageObjectRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.service.library.file.LibFileService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 28/05/2017.
 */

@Service
public class LibImageFileService implements LibFileService {

    @Inject
    private LibImageObjectRepository audioObjectRepository;

    @Inject
    private LibCloudObjectRepository cloudObjectRepository;

    @Inject
    private LibMediaItemRepository libMediaItemRepository;

    @Inject
    private S3Client s3Client;

    @Override
    public void saveFile() {

    }

    @Override
    public byte[] download(String networkShortcut, String libraryShortcut, String idx) {
        return new byte[0];
    }

    @Override
    public void deleteFile() {

    }

}
