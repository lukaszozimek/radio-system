package io.protone.library.service.file.impl;

import io.protone.core.constans.ServiceConstants;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.DeleteException;
import io.protone.core.s3.exceptions.DownloadException;
import io.protone.core.s3.exceptions.S3Exception;
import io.protone.core.s3.exceptions.UploadException;
import io.protone.core.security.SecurityUtils;
import io.protone.core.service.CorUserService;
import io.protone.library.domain.LibAudioObject;
import io.protone.library.domain.LibCloudObject;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.domain.enumeration.LibObjectTypeEnum;
import io.protone.library.repository.LibAudioObjectRepository;
import io.protone.library.repository.LibCloudObjectRepository;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.service.file.LibFileService;
import io.protone.library.service.metadata.LibAudioMetadataService;
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

/**
 * Created by lukaszozimek on 28/05/2017.
 */
@Service("libAudioFileService")
@Qualifier("libAudioFileService")
public class LibAudioFileService implements LibFileService {

    public static final String AUDIO = "audio";
    private final Logger log = LoggerFactory.getLogger(LibAudioFileService.class);
    @Inject
    private LibAudioObjectRepository audioObjectRepository;

    @Inject
    private LibCloudObjectRepository cloudObjectRepository;

    @Inject
    private LibMediaItemRepository libMediaItemRepository;

    @Inject
    private S3Client s3Client;

    @Inject
    private LibAudioMetadataService libMetadataService;

    @Inject
    private CorUserService corUserService;

    @Override
    @Transactional
    public LibMediaItem saveFile(ByteArrayInputStream bais, Metadata metadata, String originalFileName, Long size, LibMediaLibrary libraryDB) throws IOException, SAXException {
        CorUser currentUser = corUserService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).get();
        CorNetwork corNetwork = currentUser.getNetworks().stream().findAny().orElse(null);
        LibMediaItem libMediaItem = new LibMediaItem();

        String fileUUID = UUID.randomUUID().toString();
        try {
            log.debug("Uploading File to Storage: {} ", fileUUID);
            s3Client.upload(libraryDB.getNetwork().getShortcut(), MEDIA_ITEM + libraryDB.getShortcut(), fileUUID, bais, metadata.get(HttpHeaders.CONTENT_TYPE));
            LibCloudObject cloudObject = new LibCloudObject()
                    .uuid(fileUUID).contentType(metadata.get(HttpHeaders.CONTENT_TYPE))
                    .originalName(originalFileName)
                    .original(Boolean.TRUE)
                    .size(size)
                    .network(libraryDB.getNetwork())
                    .hash(ServiceConstants.NO_HASH)
                    .objectType(LibObjectTypeEnum.OT_AUDIO);
            log.debug("Persisting LibCloudObject: {}", cloudObject);
            cloudObject = cloudObjectRepository.saveAndFlush(cloudObject);
            LibAudioObject audioObject = new LibAudioObject();
            libMediaItem = libMetadataService.resolveMetadata(metadata, libraryDB, corNetwork, libMediaItem, audioObject, originalFileName);
            audioObject.setCloudObject(cloudObject);
            audioObject.setMediaItem(libMediaItem);
            log.debug("Persisting LibAudioObject: {}", audioObject);
            audioObjectRepository.saveAndFlush(audioObject);
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
        List<LibAudioObject> audioObjects = audioObjectRepository.findByMediaItem(itemDB);

        if (audioObjects == null || audioObjects.size() == 0) {
            return result;
        }
        LibAudioObject audioObject = audioObjects.iterator().next();

        LibCloudObject cloudObject = audioObject.getCloudObject();

        InputStream stream = null;
        try {
            stream = s3Client.download(itemDB.getNetwork().getShortcut(), MEDIA_ITEM + itemDB.getLibrary().getShortcut(), cloudObject.getUuid());

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

            List<LibAudioObject> audioObjects = audioObjectRepository.findByMediaItem(libMediaItem);
            if (audioObjects != null || !audioObjects.isEmpty()) {
                for (LibAudioObject audioObject : audioObjects) {
                    LibCloudObject cloudObject = audioObject.getCloudObject();
                    try {
                        s3Client.delete(libMediaItem.getNetwork().getShortcut(), MEDIA_ITEM + libMediaItem.getLibrary().getShortcut(), cloudObject.getUuid());
                        audioObjectRepository.delete(audioObject);
                        audioObjectRepository.flush();
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
    public LibMediaItem updateContent(ByteArrayInputStream bais, Metadata metadata, LibMediaItem libMediaItem, Long size, LibMediaLibrary libraryDB) throws IOException, SAXException {
        return null;
    }
}
