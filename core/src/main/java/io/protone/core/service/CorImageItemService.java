package io.protone.core.service;

import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.core.repository.CorImageItemRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.S3Exception;
import io.protone.core.s3.exceptions.UploadException;
import io.protone.core.s3.exceptions.UrlGenerationResourceException;
import io.protone.core.security.SecurityUtils;
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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static io.protone.core.constans.MinioFoldersConstants.FILE;

/**
 * Created by lukaszozimek on 20.06.2017.
 */
@Service
public class CorImageItemService {
    public static final String PUBLIC_CONTENT = "public-content";
    public static final String DEFAULT = "default";

    private final Logger log = LoggerFactory.getLogger(CorImageItemService.class);

    @Inject
    private S3Client s3Client;

    @Inject
    private CorUserService corUserService;

    @Inject
    private CorImageItemRepository corImageItemRepository;


    public CorImageItem getDefualtImageItem() {
        CorImageItem corImageItem = new CorImageItem();
        CorUser currentUser = corUserService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).get();
        CorNetwork corNetwork = currentUser.getNetworks().stream().findAny().orElse(null);
        corImageItem.setName(DEFAULT);
        corImageItem.network(corNetwork);
        return corImageItemRepository.saveAndFlush(corImageItem);
    }

    public Set<CorImageItem> saveImageItems(MultipartFile[] images) throws TikaException, SAXException, IOException {
        if (images == null || images.length == 0) {
            return new HashSet<>();
        }
        Set<CorImageItem> corImageItemSet = new HashSet<>();
        for (MultipartFile image : images) {
            corImageItemSet.add(saveImageItem(image));
        }
        return corImageItemSet;
    }


    public CorImageItem saveImageItem(MultipartFile image) throws TikaException, SAXException, IOException {
        if (image == null || image.isEmpty()) {
            return null;
        }
        CorImageItem corImageItem = null;
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
            s3Client.upload(corNetwork.getShortcut(), FILE + PUBLIC_CONTENT, fileUUID, bais, metadata.get(HttpHeaders.CONTENT_TYPE));
            corImageItem = new CorImageItem();
            corImageItem.name(fileUUID).network(corNetwork);
            corImageItem = getObjectUrl(corImageItem);
            corImageItem = corImageItemRepository.saveAndFlush(corImageItem);


        } catch (UploadException e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", image.getOriginalFilename());
        } catch (S3Exception e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", image.getOriginalFilename());
        } finally {
            bais.close();
            return corImageItem;
        }
    }

    public CorImageItem getValidLinkToResource(CorImageItem corImageItem) {
        if (corImageItem == null) {
            return null;
        }
        String publicUrl = null;
        try {
            publicUrl = s3Client.getCover(corImageItem.getNetwork().getShortcut(), FILE + PUBLIC_CONTENT, corImageItem.getName());
        } catch (S3Exception e) {
            log.error(e.getLocalizedMessage());
        } catch (UrlGenerationResourceException e) {
            log.error(e.getLocalizedMessage());
        }
        return corImageItem.publicUrl(publicUrl);
    }

    public CorImageItem getObjectUrl(CorImageItem corImageItem) {
        if (corImageItem == null) {
            return null;
        }
        String publicUrl = null;
        try {
            publicUrl = s3Client.getObjectUrl(corImageItem.getNetwork().getShortcut(), FILE + PUBLIC_CONTENT, corImageItem.getName());
        } catch (S3Exception e) {
            log.error(e.getLocalizedMessage());
        } catch (UrlGenerationResourceException e) {

            log.error(e.getLocalizedMessage());
        }
        return corImageItem.publicUrl(publicUrl);
    }
}
