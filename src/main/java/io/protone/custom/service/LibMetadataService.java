package io.protone.custom.service;

import io.protone.custom.utils.MediaUtils;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibAudioObject;
import io.protone.domain.LibLibrary;
import io.protone.domain.LibMediaItem;
import io.protone.domain.enumeration.LibAudioQualityEnum;
import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;
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

    public void resolveMetadata(MultipartFile file, LibLibrary libraryDB, CorNetwork corNetwork, LibMediaItem mediaItem, LibAudioObject audioObject) throws TikaException, SAXException, IOException {
        InputStream byteArrayInputStream = new ByteArrayInputStream(file.getBytes());
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);


        mediaItem.setItemType(LibItemTypeEnum.IT_AUDIO);
        mediaItem.setName(metadata.get(TikaCoreProperties.TITLE));
        mediaItem.setDescription(metadata.get(TikaCoreProperties.COMMENTS));
        mediaItem.setArtist(libArtistService.findOrSaveOne(metadata.get(XMPDM.ARTIST), corNetwork));
        mediaItem.album(libAlbumService.findOrSaveOne(metadata.get(XMPDM.ALBUM), metadata.get(XMPDM.ALBUM_ARTIST), corNetwork));
        mediaItem.setIdx(mediaUtils.generateIdx(libraryDB));
        //Change to Double
        ///mediaItem.setLength(Double.valueOf(metadata.get(XMPDM.DURATION)));
        mediaItem.setState(LibItemStateEnum.IS_NEW);
        mediaItem.setLibrary(libraryDB);

        audioObject.biTrate(Integer.valueOf(metadata.get(XMPDM.FILE_DATA_RATE)));
        audioObject.setLength(mediaItem.getLength());
        audioObject.setCodec(metadata.get(XMPDM.AUDIO_COMPRESSOR));
        audioObject.setQuality(LibAudioQualityEnum.AQ_ORIGINAL);


    }

}
