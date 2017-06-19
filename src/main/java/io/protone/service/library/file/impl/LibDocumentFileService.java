package io.protone.service.library.file.impl;

import io.protone.config.s3.S3Client;
import io.protone.config.s3.exceptions.DeleteException;
import io.protone.config.s3.exceptions.DownloadException;
import io.protone.config.s3.exceptions.S3Exception;
import io.protone.config.s3.exceptions.UploadException;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibObjectTypeEnum;
import io.protone.repository.library.LibCloudObjectRepository;
import io.protone.repository.library.LibDocumentObjectRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.security.SecurityUtils;
import io.protone.service.constans.ServiceConstants;
import io.protone.service.cor.CorUserService;
import io.protone.service.library.file.LibFileService;
import io.protone.service.library.metadata.LibDocumentMetadataService;
import org.apache.commons.io.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by lukaszozimek on 08/06/2017.
 */
@Service("libDocumentFileService")
@Qualifier("libDocumentFileService")
public class LibDocumentFileService implements LibFileService {
    public static final String DOCUMENT = "document";

    private final Logger log = LoggerFactory.getLogger(LibDocumentFileService.class);

    @Inject
    private LibDocumentObjectRepository libDocumentObjectRepository;

    @Inject
    private LibCloudObjectRepository cloudObjectRepository;

    @Inject
    private LibMediaItemRepository libMediaItemRepository;

    @Inject
    private S3Client s3Client;

    @Inject
    private LibDocumentMetadataService libDocumentMetadataService;

    @Inject
    private CorUserService corUserService;

    @Override
    @Transactional
    public LibMediaItem saveFile(ByteArrayInputStream bais, Metadata metadata, String originalFileName, Long size, LibLibrary libraryDB) throws IOException, SAXException {
        CorUser currentUser = corUserService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).get();
        CorNetwork corNetwork = currentUser.getNetworks().stream().findAny().orElse(null);
        LibMediaItem libMediaItem = new LibMediaItem();

        String fileUUID = UUID.randomUUID().toString();
        try {
            log.debug("Uploading File to Storage: {} ", fileUUID);
            s3Client.upload(libraryDB.getShortcut(),fileUUID, bais, metadata.get(HttpHeaders.CONTENT_TYPE));
            LibCloudObject cloudObject = new LibCloudObject()
                .uuid(fileUUID).contentType(metadata.get(HttpHeaders.CONTENT_TYPE))
                .originalName(originalFileName)
                .original(Boolean.TRUE)
                .size(size).createDate(ZonedDateTime.now()).createdBy(currentUser)
                .network(libraryDB.getNetwork())
                .hash(ServiceConstants.NO_HASH)
                .objectType(LibObjectTypeEnum.OT_AUDIO);
            log.debug("Persisting LibCloudObject: {}", cloudObject);
            cloudObject = cloudObjectRepository.saveAndFlush(cloudObject);
            LibDocumentObject libDocumentObject = new LibDocumentObject();
            libMediaItem = libDocumentMetadataService.resolveMetadata(metadata, libraryDB, corNetwork, libMediaItem, libDocumentObject);
            libDocumentObject.setCloudObject(cloudObject);
            libDocumentObject.setMediaItem(libMediaItem);
            log.debug("Persisting LibDocumentObject: {}", libDocumentObject);
            libDocumentObjectRepository.saveAndFlush(libDocumentObject);
        } catch (UploadException e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", originalFileName);

        } catch (S3Exception e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", originalFileName);
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            log.error("There is a problem with processing the file  :{}", originalFileName);
        } finally {
            bais.close();
            return libMediaItem;
        }

    }

    @Override
    public byte[] download(LibMediaItem itemDB) throws IOException {
        byte[] result = null;

        if (itemDB == null) {
            return result;
        }
        List<LibDocumentObject> libDocumentObjects = libDocumentObjectRepository.findByMediaItem(itemDB);

        if (libDocumentObjects == null || libDocumentObjects.size() == 0) {
            return result;
        }
        LibDocumentObject audioObject = libDocumentObjects.iterator().next();

        LibCloudObject cloudObject = audioObject.getCloudObject();

        InputStream stream = null;
        try {
            stream = s3Client.download(cloudObject.getUuid());

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "filename=" + cloudObject.getOriginalName());
            responseHeaders.add("Content-Length", String.format("%d", cloudObject.getSize()));

            responseHeaders.add("Content-Type", cloudObject.getContentType());

            result = IOUtils.toByteArray(stream);
        } catch (S3Exception e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", itemDB.getIdx());
        } catch (DownloadException e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", itemDB.getIdx());
        } finally {
            stream.close();
        }
        return result;
    }

    @Override
    @Transactional
    public void deleteFile(LibMediaItem libMediaItem) {
        if (libMediaItem != null) {

            List<LibDocumentObject> objects = libDocumentObjectRepository.findByMediaItem(libMediaItem);
            if (objects != null || !objects.isEmpty()) {
                for (LibDocumentObject audioObject : objects) {
                    LibCloudObject cloudObject = audioObject.getCloudObject();
                    try {
                        s3Client.delete(cloudObject.getUuid());
                        libDocumentObjectRepository.delete(audioObject);
                        libDocumentObjectRepository.flush();
                        cloudObjectRepository.delete(cloudObject);
                        cloudObjectRepository.flush();
                    } catch (DeleteException e) {
                        e.printStackTrace();
                    } catch (S3Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
