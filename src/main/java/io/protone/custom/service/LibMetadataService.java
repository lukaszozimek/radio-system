package io.protone.custom.service;

import io.protone.custom.consts.ServiceConstants;
import io.protone.custom.utils.MediaUtils;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.repository.LibArtistRepository;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.*;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.xml.ws.ServiceMode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.HashSet;

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


    public LibMediaItem resolveMetadata(MultipartFile file, LibLibrary libraryDB, CorNetwork corNetwork) throws TikaException, SAXException, IOException {
        InputStream byteArrayInputStream = new ByteArrayInputStream(file.getBytes());
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);


        ///TODO:http://stackoverflow.com/questions/5865728/bitconverter-for-java resolve cart chunk

        //byte[] bytes = new byte["".length() * (Character.SIZE / 8)];
       // System.arraycopy(toString().toCharArray(), 0, bytes, 0, bytes.length);
       // return bytes;
        //algorythm migration
        LibMediaItem mediaItem = new LibMediaItem();
        mediaItem.setItemType(LibItemTypeEnum.IT_AUDIO);
        mediaItem.setName(metadata.get(TikaCoreProperties.TITLE));
        mediaItem.setDescription(metadata.get(TikaCoreProperties.COMMENTS));
        mediaItem.setArtist(new LibArtist().name(metadata.get(XMPDM.ARTIST)));
        mediaItem.addComposer(new CorPerson().firstName(metadata.get(XMPDM.COMPOSER)));
        mediaItem.addAuthor(new CorPerson().firstName(metadata.get(XMPDM.COMPOSER)));
        mediaItem.album(new LibAlbum().name(metadata.get(XMPDM.ALBUM)).artist(new LibArtist().name(metadata.get(XMPDM.ALBUM_ARTIST))));
        mediaItem.setIdx(mediaUtils.generateIdx(libraryDB));
        mediaItem.setLength(-1L);
        mediaItem.setState(LibItemStateEnum.IS_NEW);
        mediaItem.setLibrary(libraryDB);


        return mediaItem;
    }

}
