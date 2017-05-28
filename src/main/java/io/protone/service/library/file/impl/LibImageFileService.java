package io.protone.service.library.file.impl;

import io.protone.config.s3.S3Client;
import io.protone.domain.LibImageObject;
import io.protone.domain.LibLibrary;
import io.protone.domain.LibMediaItem;
import io.protone.repository.library.LibAudioObjectRepository;
import io.protone.repository.library.LibCloudObjectRepository;
import io.protone.repository.library.LibImageObjectRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.service.library.file.LibFileService;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by lukaszozimek on 28/05/2017.
 */

@Service(value = "libImageFileService")
public class LibImageFileService implements LibFileService {
    public static final String IMAGE = "image";
    @Inject
    private LibImageObjectRepository audioObjectRepository;

    @Inject
    private LibCloudObjectRepository cloudObjectRepository;

    @Inject
    private LibMediaItemRepository libMediaItemRepository;

    @Inject
    private S3Client s3Client;


    @Override
    @Transactional
    public LibMediaItem saveFile(ByteArrayInputStream bais, Metadata metadata, String originalFileName, Long size, LibLibrary libraryDB) throws IOException, SAXException {
        return null;
    }

    @Override
    public byte[] download(LibMediaItem itemDB) {
        return new byte[0];
    }

    @Override
    @Transactional
    public void deleteFile(LibMediaItem libMediaItem) {

    }

}
