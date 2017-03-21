package io.protone.custom.metadata.audiovault.parser;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.audio.AudioParser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

import static io.protone.custom.metadata.ProtoneMetadataProperty.*;

/**
 * Created by lukaszozimek on 16/03/2017.
 */
public class AudioVaultMp3Parser extends Mp3Parser {
    private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.audio("av-mp3"));

    public static final String HELLO_MIME_TYPE = "audio/av-mp3";

    private AudioParser audioParser = new AudioParser();

    @Override
    public Set<MediaType> getSupportedTypes(ParseContext context) {
        return SUPPORTED_TYPES;
    }

    @Override
    public void parse(
        InputStream stream, ContentHandler handler,
        Metadata metadata, ParseContext context)
        throws IOException, SAXException, TikaException {
        byte[] inputStream = new byte[stream.available()];
        stream.read(inputStream);
        Supplier<InputStream> inputStreamSupplier = () -> new ByteArrayInputStream(inputStream);
        super.parse(inputStreamSupplier.get(), handler, metadata, context);
        listChunkAV(inputStreamSupplier.get(), metadata);


    }

    private void listChunkAV(InputStream stream, Metadata metadata) throws IOException {
        byte[] inputStream = new byte[stream.available()];
        stream.read(inputStream);
        String file = new String(inputStream); //6983562
        int endOfAvMetadata = file.lastIndexOf("@");


        String[] listChunkMetadataAtStart = Arrays.toString(file.substring(0, 4517).split("TXXX")).split("AudioVAULT/");

        String[] listChunkMetadataAtEnd = Arrays.toString(file.substring(endOfAvMetadata - 1104, endOfAvMetadata).split("TXXX")).split("AudioVAULT/");

        metadata.add(DATE_MODIFIED, listChunkMetadataAtStart[5].substring(listChunkMetadataAtStart[1].indexOf("__HeaderTLM") + 12, 33).trim().replace("\u0000","").replace("'\u0000' 0",""));
        metadata.add(DATE_ADD, listChunkMetadataAtStart[4].substring(listChunkMetadataAtStart[4].indexOf("__BodyTLM") + 9).trim().replace("\u0000",""));
        metadata.add(CATEGORY, listChunkMetadataAtStart[6].substring(listChunkMetadataAtStart[6].indexOf("Category") + 8).trim().replace("\u0000",""));
        metadata.add(CLASS, listChunkMetadataAtStart[7].substring(listChunkMetadataAtStart[7].indexOf("Class") + 5).trim().replace("\u0000",""));
        metadata.add(INTRO_TIME, listChunkMetadataAtEnd[1].substring(listChunkMetadataAtEnd[1].indexOf("IntroTime") + 9).trim().replace("\u0000",""));
        metadata.add(SOURCE_DISK, listChunkMetadataAtEnd[2].substring(listChunkMetadataAtEnd[2].indexOf("SourceDisk") + 10).trim().replace("\u0000",""));
        metadata.add(SOURCE_TRACK, listChunkMetadataAtEnd[3].substring(listChunkMetadataAtEnd[3].indexOf("SourceTrack") + 11).trim().replace("\u0000",""));
        metadata.add(SONG_ID, listChunkMetadataAtEnd[4].substring(listChunkMetadataAtEnd[4].indexOf("TMCSongID") + 9, listChunkMetadataAtEnd[4].indexOf("TMCSongID") + 21).trim().replace("\u0000",""));
        metadata.add(COMPOSER, listChunkMetadataAtEnd[4].substring(listChunkMetadataAtEnd[4].indexOf("TCOM") + 5, listChunkMetadataAtEnd[4].indexOf("TCOM") + 19).trim().replace("\u0000",""));
        metadata.add(LYRICIST, listChunkMetadataAtEnd[4].substring(listChunkMetadataAtEnd[4].indexOf("TEXT") + 5, listChunkMetadataAtEnd[4].indexOf("TEXT") + 21).trim().replace("\u0000",""));
        metadata.add(CLICK_URL, listChunkMetadataAtEnd[5].substring(listChunkMetadataAtEnd[5].indexOf("ClickURL") + 8, listChunkMetadataAtEnd[5].indexOf("ClickURL") + 24).trim().replace("\u0000",""));
        metadata.add(TEMPO, listChunkMetadataAtEnd[5].substring(listChunkMetadataAtEnd[5].indexOf("TBPM") + 4, listChunkMetadataAtEnd[5].indexOf("TBPM") + 15).trim().replace("\u0000",""));
        metadata.add(GENRE, listChunkMetadataAtEnd[5].substring(listChunkMetadataAtEnd[5].indexOf("TCON") + 4, listChunkMetadataAtEnd[5].indexOf("TCON") + 6).trim().replace("\u0000",""));
        metadata.add(SUBCATEGORY, listChunkMetadataAtEnd[6].substring(listChunkMetadataAtEnd[6].indexOf("GenreSubCat") + 11).trim().replace("\u0000",""));
        metadata.add(LANGUAGE, listChunkMetadataAtEnd[7].substring(listChunkMetadataAtEnd[7].indexOf("Language") + 8, listChunkMetadataAtEnd[7].indexOf("Language") + 19).trim().replace("\u0000",""));
        metadata.add(ALBUM_NAME, listChunkMetadataAtEnd[7].substring(listChunkMetadataAtEnd[7].indexOf("TALB") + 4, listChunkMetadataAtEnd[7].indexOf("TALB") + 25).trim().replace("\u0000"," "));
        //  metadata.add("AVListAlbumTrackName", listChunkMetadataAtEnd.substring(listChunkMetadataAtEnd.indexOf("AudioVAULT/SourceDisk")+21,listChunkMetadataAtEnd.indexOf("AudioVAULT/IntroTime")+28).trim());
        metadata.add(ALBUM_ART_LINK, listChunkMetadataAtEnd[8].substring(listChunkMetadataAtEnd[8].indexOf("AlbumArt") + 8, listChunkMetadataAtEnd[8].indexOf("AlbumArt") + 47).trim().replace("\u0000",""));
        metadata.add(ALBUM_REL_YEAR, listChunkMetadataAtEnd[8].substring(listChunkMetadataAtEnd[8].indexOf("TDRL") + 4, listChunkMetadataAtEnd[8].indexOf("TDRL") + 16).trim().replace("\u0000",""));
        metadata.add(ALBUM_ORIGIN_YEAR, listChunkMetadataAtEnd[8].substring(listChunkMetadataAtEnd[8].indexOf("TDOR") + 4, listChunkMetadataAtEnd[8].indexOf("TDOR") + 16).trim().replace("\u0000",""));
        metadata.add(ALBUM_MUZE_NUMBER, listChunkMetadataAtEnd[9].substring(listChunkMetadataAtEnd[9].indexOf("MuzeNum") + 7).trim().replace("\u0000",""));
        metadata.add(ALBUM_RECORD_LABEL, listChunkMetadataAtEnd[10].substring(listChunkMetadataAtEnd[10].indexOf("Label") + 5, listChunkMetadataAtEnd[10].indexOf("Label") + 15).trim().replace("\u0000",""));
        metadata.add(ALBUM_PUBLISHER, listChunkMetadataAtEnd[10].substring(listChunkMetadataAtEnd[10].indexOf("TPUB") + 4, listChunkMetadataAtEnd[10].indexOf("TPUB") + 36).trim().replace("\u0000",""));
        metadata.add(ALBUM_LICENSEE, listChunkMetadataAtEnd[11].substring(listChunkMetadataAtEnd[11].indexOf("License") + 7).trim().replace("\u0000"," "));
        metadata.add(ALBUM_CATALOG_NUMBER, listChunkMetadataAtEnd[12].substring(listChunkMetadataAtEnd[12].indexOf("CatalogNo") + 9).trim().replace("\u0000"," "));
        metadata.add(ALBUM_UPC_NUMBER, listChunkMetadataAtEnd[13].substring(listChunkMetadataAtEnd[13].indexOf("UPC") + 14, listChunkMetadataAtEnd[13].indexOf("UPC") + 22).trim().replace("\u0000"," "));
        metadata.add(ALBUM_ISRC, listChunkMetadataAtEnd[13].substring(listChunkMetadataAtEnd[13].indexOf("TSRC") + 4).trim().replace("\u0000",""));
        metadata.add(SOUNDTRACK_PRODUCER, listChunkMetadataAtEnd[14].substring(listChunkMetadataAtEnd[14].indexOf("Producer") + 8).trim().replace("\u0000",""));
        metadata.add(SOUNDTRACK_DIRECTOR, listChunkMetadataAtEnd[15].substring(listChunkMetadataAtEnd[15].indexOf("Director") + 8).trim().replace("\u0000",""));
        metadata.add(SOUNDTRACK_BOOK_AUTHOR, listChunkMetadataAtEnd[16].substring(listChunkMetadataAtEnd[16].indexOf("Book") + 4).trim().replace("\u0000",""));
        metadata.add(SOUNDTRACK_SCREEN_PLAY, listChunkMetadataAtEnd[17].substring(listChunkMetadataAtEnd[17].indexOf("Screenplay") + 10).trim().replace("\u0000",""));
        metadata.add(SOUNDTRACK_CAST, listChunkMetadataAtEnd[18].substring(listChunkMetadataAtEnd[18].indexOf("Cast") + 4).trim().replace("\u0000"," "));


    }
}
