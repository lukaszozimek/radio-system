package io.protone.library.service.metadata;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorPropertyService;
import io.protone.library.constans.MarkerConstans;
import io.protone.library.domain.*;
import io.protone.library.domain.enumeration.LibAudioQualityEnum;
import io.protone.library.domain.enumeration.LibItemStateEnum;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.service.LibAlbumService;
import io.protone.library.service.LibArtistService;
import io.protone.library.service.LibMarkerService;
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
import java.util.*;

import static io.protone.core.constans.ServiceConstants.NO_DATA;


/**
 * Created by lukaszozimek on 14/03/2017.
 */
@Service
@Transactional
public class LibAudioMetadataService {

    private final Logger log = LoggerFactory.getLogger(LibAudioMetadataService.class);

    @Inject
    private MediaUtils mediaUtils;

    @Inject
    private LibArtistService libArtistService;

    @Inject
    private LibAlbumService libAlbumService;

    @Inject
    private LibMarkerService libMArkerService;

    @Inject
    private LibMediaItemRepository mediaItemRepository;

    private Map<String, String> metadataMap;

    @Inject
    private CorPropertyService corPropertyService;

    public LibAudioMetadataService() {
        metadataMap = new HashMap<>();
        metadataMap.put(MarkerConstans.AUDe, MarkerConstans.AUDe);
        metadataMap.put(MarkerConstans.AUDs, MarkerConstans.AUDs);
        metadataMap.put(MarkerConstans.TERs, MarkerConstans.TERs);
        metadataMap.put(MarkerConstans.TERe, MarkerConstans.TERe);
        metadataMap.put(MarkerConstans.SEGs, MarkerConstans.SEGs);
        metadataMap.put(MarkerConstans.SEGe, MarkerConstans.SEGe);
        metadataMap.put(MarkerConstans.INT, MarkerConstans.INT);
    }

    public LibMediaItem resolveMetadata(Metadata metadata, LibMediaLibrary libraryDB, CorNetwork corNetwork, LibMediaItem mediaItem, LibAudioObject audioObject, String orginalFileName) throws TikaException, SAXException, IOException {
        log.debug("Start processing Audio :" + metadata.get(ProtoneMetadataProperty.TITLE.getName()));


        mediaItem.setItemType(LibItemTypeEnum.IT_AUDIO);
        if (!Strings.isNullOrEmpty(metadata.get(ProtoneMetadataProperty.TITLE))) {
            mediaItem.setName(metadata.get(ProtoneMetadataProperty.TITLE));
        } else {
            mediaItem.setName(orginalFileName);
        }

        metadata.remove(ProtoneMetadataProperty.TITLE.getName());
        mediaItem.setDescription(metadata.get(ProtoneMetadataProperty.COMMENTS));
        LibArtist libArtist = libArtistService.findOrSaveOne(metadata.get(ProtoneMetadataProperty.ARTIST), corNetwork);
        if (libArtist != null) {
            mediaItem.setArtist(libArtist);
        }
        if (!Strings.isNullOrEmpty(metadata.get(ProtoneMetadataProperty.ALBUM_NAME))) {
            LibAlbum libAlbum = libAlbumService.findOrSaveOne(metadata.get(ProtoneMetadataProperty.ALBUM_NAME), metadata.get(ProtoneMetadataProperty.ARTIST), corNetwork);
            if (libAlbum != null) {
                mediaItem.album(libAlbum);
            }
        } else {
            LibAlbum libAlbum = libAlbumService.findOrSaveOne(metadata.get(NO_DATA), metadata.get(ProtoneMetadataProperty.ARTIST), corNetwork);
            mediaItem.album(libAlbum);
        }
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


        audioObject.biTrate(1);
        audioObject.setLength(mediaItem.getLength());
        if (metadata.get(ProtoneMetadataProperty.AUDIO_COMPRESSOR) != null) {
            audioObject.setCodec(metadata.get(ProtoneMetadataProperty.AUDIO_COMPRESSOR));
            metadata.remove(ProtoneMetadataProperty.AUDIO_COMPRESSOR.getName());
        } else {
            audioObject.setCodec(NO_DATA);
        }
        audioObject.setQuality(LibAudioQualityEnum.AQ_ORIGINAL);
        Set<LibMarker> markers = new HashSet<>();
        metadataMap.keySet().stream().forEach(timer -> {
            String timerValue = metadata.get(timer);
            if (!Strings.isNullOrEmpty(timerValue)) {
                markers.add(libMArkerService.saveLibMarker(timer, Long.valueOf(timerValue), finalMediaItem));
                metadata.remove(timer);
            }
        });
        mediaItem.markers(markers);
        Arrays.stream(metadata.names()).forEach(metadataName -> {
            finalMediaItem.addProperites(corPropertyService.saveCorProperty(metadataName, finalMediaItem, metadata, corNetwork));
        });

        log.debug("Resolved LibMediaItem with Metadata: {}", mediaItem);
        return mediaItem;
    }

}
