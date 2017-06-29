package io.protone.library.service;

import io.protone.core.constans.ServiceConstants;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.S3Exception;
import io.protone.core.s3.exceptions.UploadException;
import io.protone.core.s3.exceptions.UrlGenerationResourceException;
import io.protone.core.security.SecurityUtils;
import io.protone.core.service.CorUserService;
import io.protone.library.domain.LibCloudObject;
import io.protone.library.domain.LibImageItem;
import io.protone.library.domain.LibImageObject;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.enumeration.LibImageSizeEnum;
import io.protone.library.domain.enumeration.LibObjectTypeEnum;
import io.protone.library.repository.LibCloudObjectRepository;
import io.protone.library.repository.LibImageItemRepository;
import io.protone.library.repository.LibImageObjectRepository;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by lukaszozimek on 20.06.2017.
 */
@Service
public class LibImageItemService {
    private static final String IMAGE_LIBARARY = "img";

    private final Logger log = LoggerFactory.getLogger(LibImageItemService.class);

    @Inject
    private S3Client s3Client;

    @Inject
    private CorUserService corUserService;

    @Inject
    private LibCloudObjectRepository cloudObjectRepository;

    @Inject
    private LibImageObjectRepository libImageObjectRepository;

    @Inject
    private LibImageItemRepository libImageItemRepository;

    public LibImageItem saveImageItem(MultipartFile image) throws TikaException, SAXException, IOException {
        if (image.isEmpty() || image == null) {
            return null;
        }
        LibImageItem libImageItem = new LibImageItem();
        ByteArrayInputStream bais = new ByteArrayInputStream(image.getBytes());
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        parser.parse(bais, handler, metadata, pcontext);

        CorUser currentUser = corUserService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).get();
        CorNetwork corNetwork = currentUser.getNetworks().stream().findAny().orElse(null);

        String fileUUID = UUID.randomUUID().toString();
        try {
            log.debug("Uploading File to Storage: {} ", fileUUID);
            s3Client.upload(IMAGE_LIBARARY, fileUUID, bais, metadata.get(HttpHeaders.CONTENT_TYPE));
            LibCloudObject cloudObject = new LibCloudObject()
                    .uuid(fileUUID).contentType(metadata.get(HttpHeaders.CONTENT_TYPE))
                    .originalName(image.getOriginalFilename())
                    .original(Boolean.TRUE)
                    .size(image.getSize())
                    .network(corNetwork)
                    .objectType(LibObjectTypeEnum.OT_IMAGE)
                    .hash(ServiceConstants.NO_HASH);
            log.debug("Persisting LibCloudObject: {}", cloudObject);
            cloudObject = cloudObjectRepository.saveAndFlush(cloudObject);
            LibImageObject libImageObject = new LibImageObject();
            libImageObject.setCloudObject(cloudObject);
            libImageObject.setImageItem(libImageItem);
            libImageObject.setImageSize(LibImageSizeEnum.IS_NORMAL);
            log.debug("Persisting LibAudioObject: {}", libImageObject);
            libImageObjectRepository.saveAndFlush(libImageObject);
        } catch (UploadException e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", image.getOriginalFilename());
        } catch (S3Exception e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", image.getOriginalFilename());
        } finally {
            bais.close();
            return libImageItem;
        }
    }

    public LibMediaItem getValidLinkToResours(LibMediaItem libMediaItem) {
        LibMediaItem mediaItemLocal = libMediaItem;
        libMediaItem.getImageItems().stream().forEach(libImageItem -> {
            try {
                libImageItem.setPublicUrl(s3Client.getCover(libImageItem.getLibrary().getShortcut(), libImageItem.getName()));
            } catch (S3Exception e) {
                e.printStackTrace();
            } catch (UrlGenerationResourceException e) {
                e.printStackTrace();
            }
        });
        //TODO:put to cache?
        return mediaItemLocal;
    }

}
