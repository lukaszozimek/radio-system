package io.protone.custom.service;

import com.google.api.client.repackaged.com.google.common.base.Strings;

import java.util.*;

import io.protone.service.constans.MarkerConstans;
import io.protone.custom.metadata.ProtoneMetadataProperty;
import io.protone.custom.utils.MediaUtils;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibAudioQualityEnum;
import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.repository.cor.CorPropertyKeyRepository;
import io.protone.repository.cor.CorPropertyValueRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.service.library.LibMarkerService;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static io.protone.service.constans.ServiceConstants.NO_DATA;

/**
 * Created by lukaszozimek on 14/03/2017.
 */
@Service
@Transactional
public class LibMetadataService {

    private final Logger log = LoggerFactory.getLogger(LibMetadataService.class);

    @Inject
    private MediaUtils mediaUtils;

    @Inject
    private LibArtistService libArtistService;

    @Inject
    private LibAlbumService libAlbumService;

    @Inject
    private LibMarkerService libMArkerService;

    @Inject
    private CorPropertyKeyRepository corPropertyKeyRepository;

    @Inject
    private CorPropertyValueRepository corPropertyValueRepository;

    @Inject
    private LibMediaItemRepository mediaItemRepository;

    private Map<String, String> metadataMap;

    public LibMetadataService() {
        metadataMap = new HashMap<>();
        metadataMap.put(MarkerConstans.AUDe, MarkerConstans.AUDe);
        metadataMap.put(MarkerConstans.AUDs, MarkerConstans.AUDs);
        metadataMap.put(MarkerConstans.TERs, MarkerConstans.TERs);
        metadataMap.put(MarkerConstans.TERe, MarkerConstans.TERe);
        metadataMap.put(MarkerConstans.SEGs, MarkerConstans.SEGs);
        metadataMap.put(MarkerConstans.SEGe, MarkerConstans.SEGe);
        metadataMap.put(MarkerConstans.INT, MarkerConstans.INT);
    }

    public LibMediaItem resolveMetadata(MultipartFile file, LibLibrary libraryDB, CorNetwork corNetwork, LibMediaItem mediaItem, LibAudioObject audioObject) throws TikaException, SAXException, IOException {
        log.debug("Start processing :" + file.getOriginalFilename());
        InputStream byteArrayInputStream = new ByteArrayInputStream(file.getBytes());
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        mediaItem.setItemType(LibItemTypeEnum.IT_AUDIO);
        if (!Strings.isNullOrEmpty(metadata.get(ProtoneMetadataProperty.TITLE))) {
            mediaItem.setName(metadata.get(ProtoneMetadataProperty.TITLE));
        } else {
            mediaItem.setName(NO_DATA);
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
        }
        mediaItem.setIdx(mediaUtils.generateIdx(libraryDB));
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
            CorPropertyKey corPropertyKey;
            if (Strings.isNullOrEmpty(metadataName)) {
                corPropertyKey = new CorPropertyKey().key(NO_DATA).network(corNetwork);
            } else {
                corPropertyKey = new CorPropertyKey().key(metadataName).network(corNetwork);
            }

            log.debug("Persisting CorKey: {}", corPropertyKey);
            corPropertyKey = corPropertyKeyRepository.saveAndFlush(corPropertyKey);
            log.debug("Persisting CorValues: {}", metadata.get(metadataName));
            corPropertyValueRepository.saveAndFlush(new CorPropertyValue().value(metadata.get(metadataName)).libItemPropertyValue(finalMediaItem).propertyKey(corPropertyKey));
        });

        log.debug("Resolved LibMediaItem with Metadata: {}", mediaItem);
        return mediaItem;
    }

}
