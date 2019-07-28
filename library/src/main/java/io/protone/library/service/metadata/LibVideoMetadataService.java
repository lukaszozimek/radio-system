package io.protone.library.service.metadata;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorPropertyService;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.domain.LibVideoObject;
import io.protone.library.domain.enumeration.LibItemStateEnum;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.protone.library.domain.enumeration.LibVideoQualityEnum;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.service.LibAlbumService;
import io.protone.library.service.LibArtistService;
import io.protone.library.util.MediaUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.XMPDM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;

import static io.protone.core.constans.ServiceConstants.NO_DATA;


/**
 * Created by lukaszozimek on 14/03/2017.
 */
@Service
@Transactional
public class LibVideoMetadataService {

    private final Logger log = LoggerFactory.getLogger(LibVideoMetadataService.class);

    @Inject
    private MediaUtils mediaUtils;

    @Inject
    private LibArtistService libArtistService;

    @Inject
    private LibAlbumService libAlbumService;

    @Inject
    private CorPropertyService corPropertyService;

    @Inject
    private LibMediaItemRepository mediaItemRepository;


    public LibMediaItem resolveMetadata(Metadata metadata, LibMediaLibrary libraryDB, CorNetwork corNetwork, LibMediaItem mediaItem, LibVideoObject libVideoObject, String orginalFileName) throws TikaException, SAXException, IOException {
        log.debug("Start processing Video :" + metadata.get(ProtoneMetadataProperty.TITLE.getName()));


        mediaItem.setItemType(LibItemTypeEnum.IT_VIDEO);
        if (!Strings.isNullOrEmpty(metadata.get(ProtoneMetadataProperty.TITLE))) {
            mediaItem.setName(metadata.get(ProtoneMetadataProperty.TITLE));
        } else {
            mediaItem.setName(orginalFileName);
        }

        metadata.remove(ProtoneMetadataProperty.TITLE.getName());
        mediaItem.setDescription(metadata.get(ProtoneMetadataProperty.COMMENTS));
        if (mediaItem.getIdx() == null) {
            mediaItem.setIdx(mediaUtils.generateIdx(libraryDB));
        }
        if (metadata.get(XMPDM.DURATION) != null) {
            mediaItem.setLength(Double.valueOf(metadata.get(XMPDM.DURATION)));

        } else {
            mediaItem.setLength(1.0);
        }
        metadata.remove(ProtoneMetadataProperty.DURATION.getName());
        metadata.remove(ProtoneMetadataProperty.ALBUM_NAME);
        metadata.remove(ProtoneMetadataProperty.ARTIST.getName());
        mediaItem.setState(LibItemStateEnum.IS_NEW);
        mediaItem.setLibrary(libraryDB);
        mediaItem.network(corNetwork);
        log.debug("Persisting LibMediaItem: {}", mediaItem);
        mediaItem = mediaItemRepository.saveAndFlush(mediaItem);
        LibMediaItem finalMediaItem = mediaItem;


        libVideoObject.biTrate(1);
        libVideoObject.setLength(mediaItem.getLength().longValue());
        if (metadata.get(ProtoneMetadataProperty.VIDEO_COMPRESSOR) != null) {
            libVideoObject.setCodec(metadata.get(ProtoneMetadataProperty.VIDEO_COMPRESSOR));
            metadata.remove(ProtoneMetadataProperty.VIDEO_COMPRESSOR.getName());
        } else {
            libVideoObject.setCodec(NO_DATA);
        }

        libVideoObject.setQuality(LibVideoQualityEnum.VQ_OTHER);

        Arrays.stream(metadata.names()).forEach(metadataName -> {
            finalMediaItem.addProperites(corPropertyService.saveCorProperty(metadataName, finalMediaItem, metadata, corNetwork));
        });

        log.debug("Resolved LibMediaItem with Metadata: {}", mediaItem);
        return mediaItem;
    }

}
