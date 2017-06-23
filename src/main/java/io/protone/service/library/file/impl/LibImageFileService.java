package io.protone.service.library.file.impl;

import com.google.common.collect.Lists;
import io.protone.config.s3.S3Client;
import io.protone.config.s3.exceptions.DeleteException;
import io.protone.config.s3.exceptions.DownloadException;
import io.protone.config.s3.exceptions.S3Exception;
import io.protone.config.s3.exceptions.UploadException;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibImageSizeEnum;
import io.protone.domain.enumeration.LibObjectTypeEnum;
import io.protone.repository.library.LibCloudObjectRepository;
import io.protone.repository.library.LibImageObjectRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.security.SecurityUtils;
import io.protone.service.constans.ServiceConstants;
import io.protone.service.cor.CorUserService;
import io.protone.service.library.file.LibFileService;
import io.protone.service.library.metadata.LibImageMetadataService;
import io.protone.service.metadata.ProtoneMetadataProperty;
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
 * Created by lukaszozimek on 28/05/2017.
 */
@Service("libImageFileService")
@Qualifier("libImageFileService")
public class LibImageFileService implements LibFileService {
    private final Logger log = LoggerFactory.getLogger(LibImageFileService.class);

    public static final String IMAGE = "image";

    @Inject
    private CorUserService corUserService;

    @Inject
    private LibImageObjectRepository libImageObjectRepository;

    @Inject
    private LibCloudObjectRepository cloudObjectRepository;

    @Inject
    private LibMediaItemRepository libMediaItemRepository;

    @Inject
    private LibImageMetadataService libMetadataService;

    @Inject
    private S3Client s3Client;


    @Override
    @Transactional
    public LibMediaItem saveFile(ByteArrayInputStream bais, Metadata metadata, String originalFileName, Long size, LibLibrary libraryDB) throws IOException, SAXException {
        CorUser currentUser = corUserService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).get();
        CorNetwork corNetwork = currentUser.getNetworks().stream().findAny().orElse(null);
        LibMediaItem libMediaItem = new LibMediaItem();

        String fileUUID = UUID.randomUUID().toString();
        try {
            log.debug("Uploading File to Storage: {} ", fileUUID);
            s3Client.upload(fileUUID, bais, metadata.get(HttpHeaders.CONTENT_TYPE));
            LibCloudObject cloudObject = new LibCloudObject()
                .uuid(fileUUID).contentType(metadata.get(HttpHeaders.CONTENT_TYPE))
                .originalName(originalFileName)
                .original(Boolean.TRUE)
                .size(size)
                .network(corNetwork)
                .objectType(LibObjectTypeEnum.OT_IMAGE)
                .hash(ServiceConstants.NO_HASH);
            log.debug("Persisting LibCloudObject: {}", cloudObject);
            cloudObject = cloudObjectRepository.saveAndFlush(cloudObject);
            LibImageObject libImageObject = new LibImageObject();
            libMediaItem = libMetadataService.resolveMetadata(metadata, libraryDB, corNetwork, libMediaItem, libImageObject);
            libImageObject.setCloudObject(cloudObject);
            libImageObject.mediaItem(libMediaItem);
            libImageObject.setImageSize(LibImageSizeEnum.IS_NORMAL);
            log.debug("Persisting LibAudioObject: {}", libImageObject);
            libImageObjectRepository.saveAndFlush(libImageObject);
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
        List<LibImageObject> libImageObjects = libImageObjectRepository.findByMediaItem(itemDB);

        if (libImageObjects == null || libImageObjects.size() == 0) {
            return result;
        }
        LibImageObject audioObject = libImageObjects.iterator().next();

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
            List<LibImageObject> libImageObjects = libImageObjectRepository.findByMediaItem(libMediaItem);
            if (libImageObjects != null || !libImageObjects.isEmpty()) {
                for (LibImageObject libImageObject : libImageObjects) {
                    LibCloudObject cloudObject = libImageObject.getCloudObject();
                    try {
                        s3Client.delete(cloudObject.getUuid());
                        libImageObjectRepository.delete(libImageObject);
                        libImageObjectRepository.flush();
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
