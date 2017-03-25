package io.protone.custom.service;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import io.protone.custom.metadata.ProtoneMetadataProperty;
import io.protone.custom.utils.MediaUtils;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibAudioQualityEnum;
import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.repository.CorPropertyKeyRepository;
import io.protone.repository.CorPropertyValueRepository;
import io.protone.repository.custom.CustomLibMediaItemRepository;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static io.protone.custom.consts.ServiceConstants.NO_DATA;

/**
 * Created by lukaszozimek on 14/03/2017.
 */
@Service
@Transactional
public class LibMetadataService {

    @Inject
    private MediaUtils mediaUtils;

    @Inject
    private LibArtistService libArtistService;

    @Inject
    private LibLabelService libLabelService;

    @Inject
    private LibAlbumService libAlbumService;

    @Inject
    private LibMarkerService libMArkerService;

    @Inject
    private CorPropertyKeyRepository corPropertyKeyRepository;

    @Inject
    private CorPropertyValueRepository corPropertyValueRepository;

    @Inject
    private CustomLibMediaItemRepository mediaItemRepository;

    public LibMediaItem resolveMetadata(MultipartFile file, LibLibrary libraryDB, CorNetwork corNetwork, LibMediaItem mediaItem, LibAudioObject audioObject) throws TikaException, SAXException, IOException {
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
        mediaItem.setDescription(metadata.get(ProtoneMetadataProperty.COMMENTS));
        LibArtist libArtist = libArtistService.findOrSaveOne(metadata.get(ProtoneMetadataProperty.ARTIST), corNetwork);
        if (libArtist != null) {
            mediaItem.setArtist(libArtist);
        }
        LibAlbum libAlbum = libAlbumService.findOrSaveOne(metadata.get(ProtoneMetadataProperty.ALBUM_NAME), metadata.get(ProtoneMetadataProperty.ALBUM_ARTIST), corNetwork);
        if (libAlbum != null) {
            mediaItem.album(libAlbum);
        }
        mediaItem.setIdx(mediaUtils.generateIdx(libraryDB));
        if (metadata.get(XMPDM.DURATION) != null) {
            mediaItem.setLength(Double.valueOf(metadata.get(XMPDM.DURATION)));

        } else {
            mediaItem.setLength(1.0);
        }
        mediaItem.setState(LibItemStateEnum.IS_NEW);
        mediaItem.setLibrary(libraryDB);
        mediaItem = mediaItemRepository.saveAndFlush(mediaItem);
        LibMediaItem finalMediaItem = mediaItem;
        Arrays.stream(metadata.names()).forEach(metadataName -> {
            CorPropertyKey corPropertyKey = new CorPropertyKey().key(metadataName).network(corNetwork);
            corPropertyKeyRepository.saveAndFlush(corPropertyKey);
            corPropertyValueRepository.saveAndFlush(new CorPropertyValue().value(metadata.get(metadataName)).libItemPropertyValue(finalMediaItem).propertyKey(corPropertyKey));
        });
        audioObject.biTrate(1);
        audioObject.setLength(mediaItem.getLength());
       if(metadata.get(ProtoneMetadataProperty.AUDIO_COMPRESSOR)!=null) {
           audioObject.setCodec(metadata.get(ProtoneMetadataProperty.AUDIO_COMPRESSOR));
       }
       else {
           audioObject.setCodec(NO_DATA);
       }

        audioObject.setQuality(LibAudioQualityEnum.AQ_ORIGINAL);
        return mediaItem;
    }

}
