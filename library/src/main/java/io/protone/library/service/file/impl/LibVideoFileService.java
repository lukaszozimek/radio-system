package io.protone.library.service.file.impl;

import io.protone.core.constans.ServiceConstants;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.DeleteException;
import io.protone.core.s3.exceptions.DownloadException;
import io.protone.core.s3.exceptions.S3Exception;
import io.protone.core.s3.exceptions.UploadException;
import io.protone.core.security.SecurityUtils;
import io.protone.core.service.CorUserService;
import io.protone.library.domain.LibCloudObject;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.domain.LibVideoObject;
import io.protone.library.domain.enumeration.LibObjectTypeEnum;
import io.protone.library.repository.LibCloudObjectRepository;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.repository.LibVideoObjectRepository;
import io.protone.library.service.file.LibFileService;
import io.protone.library.service.metadata.LibVideoMetadataService;
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
import java.util.List;
import java.util.UUID;

import static io.protone.core.constans.MinioFoldersConstants.MEDIA_ITEM;
import static io.protone.library.service.LibLibraryMediaService.LIBRARY_SEPARATOR;

/**
 * Created by lukaszozimek on 28/05/2017.
 */

@Service("libVideoFileService")
@Qualifier("libVideoFileService")
public class LibVideoFileService implements LibFileService {
    public static final String VIDEO = "video";
    private final Logger log = LoggerFactory.getLogger(LibVideoFileService.class);
    @Inject
    private LibVideoObjectRepository libVideoObjectRepository;

    @Inject
    private LibCloudObjectRepository cloudObjectRepository;

    @Inject
    private LibVideoMetadataService libMetadataService;

    @Inject
    private S3Client s3Client;


    @Override
    @Transactional
    public LibMediaItem saveFile(ByteArrayInputStream bais, Metadata metadata, String originalFileName, Long size, LibMediaLibrary libraryDB, CorChannel corChannel) throws IOException, SAXException {
        LibMediaItem libMediaItem = new LibMediaItem();

        String fileUUID = UUID.randomUUID().toString();
        try {
            log.debug("Uploading File to Storage: {} ", fileUUID);
            s3Client.upload(corChannel.getOrganization().getShortcut() + LIBRARY_SEPARATOR + corChannel.getShortcut(), MEDIA_ITEM + libraryDB.getShortcut(), fileUUID, bais, metadata.get(HttpHeaders.CONTENT_TYPE));
            LibCloudObject cloudObject = new LibCloudObject()
                    .uuid(fileUUID).contentType(metadata.get(HttpHeaders.CONTENT_TYPE))
                    .originalName(originalFileName)
                    .original(Boolean.TRUE)
                    .size(size).objectType(LibObjectTypeEnum.OT_VIDEO)
                    .hash(ServiceConstants.NO_HASH);
            log.debug("Persisting LibCloudObject: {}", cloudObject);
            cloudObject = cloudObjectRepository.saveAndFlush(cloudObject);
            LibVideoObject libVideoObject = new LibVideoObject();
            libMediaItem = libMetadataService.resolveMetadata(metadata, libraryDB, corChannel, libMediaItem.contentAvailable(true), libVideoObject, originalFileName);
            libVideoObject.setCloudObject(cloudObject);
            libVideoObject.setMediaItem(libMediaItem);
            log.debug("Persisting LibAudioObject: {}", libVideoObject);
            libVideoObjectRepository.saveAndFlush(libVideoObject);
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
    public byte[] download(LibMediaItem libMediaItem) throws IOException {
        byte[] result = null;

        if (libMediaItem == null) {
            return result;
        }
        List<LibVideoObject> videoObjects = libVideoObjectRepository.findByMediaItem(libMediaItem);

        if (videoObjects == null || videoObjects.size() == 0) {
            return result;
        }
        LibVideoObject libVideoObject = videoObjects.iterator().next();

        LibCloudObject cloudObject = libVideoObject.getCloudObject();

        InputStream stream = null;
        try {
            stream = s3Client.download(libMediaItem.getChannel().getOrganization().getShortcut() + LIBRARY_SEPARATOR + libMediaItem.getChannel().getShortcut(), MEDIA_ITEM + libMediaItem.getLibrary().getShortcut(), cloudObject.getUuid());

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "filename=" + cloudObject.getOriginalName());
            responseHeaders.add("Content-Length", String.format("%d", cloudObject.getSize()));

            responseHeaders.add("Content-Type", cloudObject.getContentType());

            result = IOUtils.toByteArray(stream);
        } catch (S3Exception e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", libMediaItem.getIdx());
        } catch (DownloadException e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", libMediaItem.getIdx());
        } finally {
            stream.close();
        }
        return result;
    }

    @Override
    @Transactional
    public void deleteFile(LibMediaItem libMediaItem) {
        if (libMediaItem != null) {
            List<LibVideoObject> videoObjects = libVideoObjectRepository.findByMediaItem(libMediaItem);
            if (videoObjects != null || !videoObjects.isEmpty()) {
                for (LibVideoObject libVideoObject : videoObjects) {
                    LibCloudObject cloudObject = libVideoObject.getCloudObject();
                    try {
                        s3Client.delete(libMediaItem.getChannel().getOrganization().getShortcut() + LIBRARY_SEPARATOR + libMediaItem.getChannel().getShortcut(), MEDIA_ITEM + libMediaItem.getLibrary().getShortcut(), cloudObject.getUuid());
                        libVideoObjectRepository.delete(libVideoObject);
                        libVideoObjectRepository.flush();
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

    @Override
    public LibMediaItem updateContent(ByteArrayInputStream bais, Metadata metadata, LibMediaItem libMediaItem, Long size, LibMediaLibrary libraryDB, CorChannel corChannel) throws IOException, SAXException {
        String fileUUID = UUID.randomUUID().toString();
        try {
            log.debug("Uploading File to Storage: {} ", fileUUID);
            s3Client.upload(corChannel.getOrganization().getShortcut() + LIBRARY_SEPARATOR + corChannel.getShortcut(), MEDIA_ITEM + libraryDB.getShortcut(), fileUUID, bais, metadata.get(HttpHeaders.CONTENT_TYPE));
            LibCloudObject cloudObject = new LibCloudObject()
                    .uuid(fileUUID).contentType(metadata.get(HttpHeaders.CONTENT_TYPE))
                    .originalName(libMediaItem.getName())
                    .original(Boolean.TRUE)
                    .size(size).objectType(LibObjectTypeEnum.OT_VIDEO)
                    .hash(ServiceConstants.NO_HASH);
            log.debug("Persisting LibCloudObject: {}", cloudObject);
            cloudObject = cloudObjectRepository.saveAndFlush(cloudObject);
            LibVideoObject libVideoObject = new LibVideoObject();
            libMediaItem = libMetadataService.resolveMetadata(metadata, libraryDB, corChannel, libMediaItem, libVideoObject, libMediaItem.getName());
            libVideoObject.setCloudObject(cloudObject);
            libVideoObject.setMediaItem(libMediaItem);
            log.debug("Persisting LibAudioObject: {}", libVideoObject);
            libVideoObjectRepository.saveAndFlush(libVideoObject);
        } catch (UploadException e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", libMediaItem.getName());

        } catch (S3Exception e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", libMediaItem.getName());
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            log.error("There is a problem with processing the file  :{}", libMediaItem.getName());
        } finally {
            bais.close();
            return libMediaItem;
        }
    }


}
