package io.protone.library.service.metadata.parser;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.*;
import org.springframework.util.ReflectionUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;

import static io.protone.library.service.metadata.ProtoneMetadataProperty.*;

/**
 * Created by lukaszozimek on 16/03/2017.
 */
public class AudioVaultMp3Parser extends Mp3Parser {
    public static final String HELLO_MIME_TYPE = "audio/av-mp3";
    private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.audio("av-mp3"));
    private static final String HEADER_TLM = "__HeaderTLM";
    private static final String BODY_TLM = "__BodyTLM";
    private static final String CATEGORY = "Category";
    private static final String CLASS = "Class";
    private static final String INTRO_TIME = "IntroTime";
    private static final String SOURCE_DISK = "SourceDisk";
    private static final String SOURCE_TRACK = "SourceTrack";
    private static final String TMC_SONG_ID = "TMCSongID";
    private static final String T_COM = "TCOM";
    private static final String TEXT = "TEXT";
    private static final String CLICK_URL = "ClickURL";
    private static final String T_BPM = "TBPM";
    private static final String T_CON = "TCON";
    private static final String GENRE_SUB_CAT = "GenreSubCat";
    private static final String LANGUAGE = "Language";
    private static final String T_ALB = "TALB";
    private static final String ALBUM_ART = "AlbumArt";
    private static final String T_DRL = "TDRL";
    private static final String T_DOR = "TDOR";
    private static final String MUZE_NUM = "MuzeNum";
    private static final String LABEL = "Label";
    private static final String T_PUB = "TPUB";
    private static final String LICENSE = "License";
    private static final String CATALOG_NO = "CatalogNo";
    private static final String UPC = "UPC";
    private static final String T_SRC = "TSRC";
    private static final String PRODUCER = "Producer";
    private static final String DIRECTOR = "Director";
    private static final String BOOK = "Book";
    private static final String SCREENPLAY = "Screenplay";
    private static final String CAST = "Cast";

    public static boolean checkFiledsNotNull(Object obj) {
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.get(obj) != null && !(f.get(obj) instanceof List)) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

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
        metadata.set("Content-Type", "audio/mp3");
        ID3TagsAndAudio id3TagsAndAudio = Mp3Parser.getAllTagHandlers(inputStreamSupplier.get(), handler);

        Field field = id3TagsAndAudio.getClass().getDeclaredFields()[0];
        ReflectionUtils.makeAccessible(field);
        Object object = ReflectionUtils.getField(field, id3TagsAndAudio);
        if (object instanceof ID3Tags[]) {
            ID3Tags[] id3Tags = (ID3Tags[]) object;
            Optional<ID3Tags> tags = Arrays.stream(id3Tags).filter(id3Tags1 -> id3Tags1 instanceof ID3v24Handler || id3Tags1 instanceof ID3v22Handler || id3Tags1 instanceof ID3v23Handler).findFirst();
            if (tags.isPresent()) {
                if (checkFiledsNotNull(tags.get())) {
                    parseWithIDV2(inputStreamSupplier.get(), metadata);
                } else {
                    parseWithoutIDv2(inputStreamSupplier.get(), metadata);
                }
            }
        }
    }

    private void parseWithIDV2(InputStream stream, Metadata metadata) throws IOException {
        byte[] inputStream = new byte[stream.available()];
        stream.read(inputStream);
        stream.close();
        String file = new String(inputStream);
        String listChunkMetadataAtStart = Arrays.toString(file.substring(0, 4517).split("TXXX"));
        parseAvChunksWithIDV2(listChunkMetadataAtStart, metadata);

    }

    private void parseWithoutIDv2(InputStream stream, Metadata metadata) throws IOException {
        byte[] inputStream = new byte[stream.available()];
        stream.read(inputStream);
        stream.close();
        String file = new String(inputStream);
        int endOfAvMetadata = file.length();
        String listChunkMetadataAtStart = Arrays.toString(file.substring(0, 4517).split("TXXX"));
        String listChunkMetadataAtEnd = Arrays.toString(file.substring(endOfAvMetadata - 1104, endOfAvMetadata).split("TXXX"));
        parseAvChunksWithOutIDV2(listChunkMetadataAtStart, listChunkMetadataAtEnd, metadata);
    }

    private void parseAvChunksWithIDV2(String listChunkMetadataAtStart, Metadata metadata) {

        metadata.add(DATE_MODIFIED, getAudioVaultTag(listChunkMetadataAtStart, HEADER_TLM, HEADER_TLM.length(), 20));
        metadata.add(DATE_ADD, getAudioVaultTag(listChunkMetadataAtStart, BODY_TLM, BODY_TLM.length(), 20));
        metadata.add(CATEGORY, getAudioVaultTag(listChunkMetadataAtStart, CATEGORY, CATEGORY.length(), 10));
        metadata.add(CLASS, getAudioVaultTag(listChunkMetadataAtStart, CLASS, CLASS.length(), 10));
        metadata.add(INTRO_TIME, getAudioVaultTag(listChunkMetadataAtStart, INTRO_TIME, INTRO_TIME.length(), 10));
        metadata.add(SOURCE_DISK, getAudioVaultTag(listChunkMetadataAtStart, SOURCE_DISK, SOURCE_DISK.length(), 10));
        metadata.add(SOURCE_TRACK, getAudioVaultTag(listChunkMetadataAtStart, SOURCE_TRACK, SOURCE_TRACK.length(), 10));
        metadata.add(SONG_ID, getAudioVaultTag(listChunkMetadataAtStart, TMC_SONG_ID, TMC_SONG_ID.length(), 12));
        metadata.add(COMPOSER, getAudioVaultTag(listChunkMetadataAtStart, T_COM, T_COM.length(), 14));
        metadata.add(LYRICIST, getAudioVaultTag(listChunkMetadataAtStart, TEXT, TEXT.length(), 16));
        metadata.add(CLICK_URL, getAudioVaultTag(listChunkMetadataAtStart, CLICK_URL, CLICK_URL.length(), 16));
        metadata.add(TEMPO, getAudioVaultTag(listChunkMetadataAtStart, T_BPM, T_BPM.length(), 11));
        metadata.add(GENRE, getAudioVaultTag(listChunkMetadataAtStart, T_CON, T_CON.length(), 6));
        metadata.add(SUBCATEGORY, getAudioVaultTag(listChunkMetadataAtStart, GENRE_SUB_CAT, GENRE_SUB_CAT.length(), 10));
        metadata.add(LANGUAGE, getAudioVaultTag(listChunkMetadataAtStart, LANGUAGE, LANGUAGE.length(), 11));
        metadata.add(ALBUM_NAME, getAudioVaultTag(listChunkMetadataAtStart, T_ALB, T_ALB.length(), 21));
        metadata.add(ALBUM_ART_LINK, getAudioVaultTag(listChunkMetadataAtStart, ALBUM_ART, ALBUM_ART.length(), 40));
        //  metadata.add("AVListAlbumTrackName", listChunkMetadataAtEnd.substring(listChunkMetadataAtEnd.indexOf("AudioVAULT/SourceDisk")+21,listChunkMetadataAtEnd.indexOf("AudioVAULT/IntroTime")+28).trim());
        metadata.add(ALBUM_REL_YEAR, getAudioVaultTag(listChunkMetadataAtStart, T_DRL, T_DRL.length(), 12));
        metadata.add(ALBUM_ORIGIN_YEAR, getAudioVaultTag(listChunkMetadataAtStart, T_DOR, T_DOR.length(), 12));
        metadata.add(ALBUM_MUZE_NUMBER, getAudioVaultTag(listChunkMetadataAtStart, MUZE_NUM, MUZE_NUM.length(), 7));
        metadata.add(ALBUM_RECORD_LABEL, getAudioVaultTag(listChunkMetadataAtStart, LABEL, LABEL.length(), 15));
        metadata.add(ALBUM_PUBLISHER, getAudioVaultTag(listChunkMetadataAtStart, T_PUB, T_PUB.length(), 36));
        metadata.add(ALBUM_LICENSEE, getAudioVaultTag(listChunkMetadataAtStart, LICENSE, LICENSE.length(), 10));
        metadata.add(ALBUM_CATALOG_NUMBER, getAudioVaultTag(listChunkMetadataAtStart, CATALOG_NO, CATALOG_NO.length(), 10));
        metadata.add(ALBUM_UPC_NUMBER, getAudioVaultTag(listChunkMetadataAtStart, UPC, UPC.length(), 22));
        metadata.add(ALBUM_ISRC, getAudioVaultTag(listChunkMetadataAtStart, T_SRC, T_SRC.length(), 10));
        metadata.add(SOUNDTRACK_PRODUCER, getAudioVaultTag(listChunkMetadataAtStart, PRODUCER, PRODUCER.length(), 10));
        metadata.add(SOUNDTRACK_DIRECTOR, getAudioVaultTag(listChunkMetadataAtStart, DIRECTOR, DIRECTOR.length(), 10));
        metadata.add(SOUNDTRACK_BOOK_AUTHOR, getAudioVaultTag(listChunkMetadataAtStart, BOOK, BOOK.length(), 10));
        metadata.add(SOUNDTRACK_SCREEN_PLAY, getAudioVaultTag(listChunkMetadataAtStart, SCREENPLAY, SCREENPLAY.length(), 10));
        metadata.add(SOUNDTRACK_CAST, getAudioVaultTag(listChunkMetadataAtStart, CAST, CAST.length(), 10));
    }

    private void parseAvChunksWithOutIDV2(String fileLineHeader, String fileLineBottom, Metadata metadata) {

        metadata.add(DATE_MODIFIED, getAudioVaultTag(fileLineHeader, HEADER_TLM, HEADER_TLM.length(), 20));
        metadata.add(DATE_ADD, getAudioVaultTag(fileLineHeader, BODY_TLM, BODY_TLM.length(), 20));
        metadata.add(CATEGORY, getAudioVaultTag(fileLineHeader, CATEGORY, CATEGORY.length(), 10));
        metadata.add(CLASS, getAudioVaultTag(fileLineHeader, CLASS, CLASS.length(), 10));
        metadata.add(INTRO_TIME, getAudioVaultTag(fileLineBottom, INTRO_TIME, INTRO_TIME.length(), 10));
        metadata.add(SOURCE_DISK, getAudioVaultTag(fileLineBottom, SOURCE_DISK, SOURCE_DISK.length(), 10));
        metadata.add(SOURCE_TRACK, getAudioVaultTag(fileLineBottom, SOURCE_TRACK, SOURCE_TRACK.length(), 10));
        metadata.add(SONG_ID, getAudioVaultTag(fileLineBottom, TMC_SONG_ID, TMC_SONG_ID.length(), 12));
        metadata.add(COMPOSER, getAudioVaultTag(fileLineBottom, T_COM, T_COM.length(), 14));
        metadata.add(LYRICIST, getAudioVaultTag(fileLineBottom, TEXT, TEXT.length(), 16));
        metadata.add(CLICK_URL, getAudioVaultTag(fileLineBottom, CLICK_URL, CLICK_URL.length(), 16));
        metadata.add(TEMPO, getAudioVaultTag(fileLineBottom, T_BPM, T_BPM.length(), 11));
        metadata.add(GENRE, getAudioVaultTag(fileLineBottom, T_CON, T_CON.length(), 6));
        metadata.add(SUBCATEGORY, getAudioVaultTag(fileLineBottom, GENRE_SUB_CAT, GENRE_SUB_CAT.length(), 10));
        metadata.add(LANGUAGE, getAudioVaultTag(fileLineBottom, LANGUAGE, LANGUAGE.length(), 11));
        metadata.add(ALBUM_NAME, getAudioVaultTag(fileLineBottom, T_ALB, T_ALB.length(), 21));
        metadata.add(ALBUM_ART_LINK, getAudioVaultTag(fileLineBottom, ALBUM_ART, ALBUM_ART.length(), 40));
        //  metadata.add("AVListAlbumTrackName", listChunkMetadataAtEnd.substring(listChunkMetadataAtEnd.indexOf("AudioVAULT/SourceDisk")+21,listChunkMetadataAtEnd.indexOf("AudioVAULT/IntroTime")+28).trim());
        metadata.add(ALBUM_REL_YEAR, getAudioVaultTag(fileLineBottom, T_DRL, T_DRL.length(), 12));
        metadata.add(ALBUM_ORIGIN_YEAR, getAudioVaultTag(fileLineBottom, T_DOR, T_DOR.length(), 12));
        metadata.add(ALBUM_MUZE_NUMBER, getAudioVaultTag(fileLineBottom, MUZE_NUM, MUZE_NUM.length(), 7));
        metadata.add(ALBUM_RECORD_LABEL, getAudioVaultTag(fileLineBottom, LABEL, LABEL.length(), 15));
        metadata.add(ALBUM_PUBLISHER, getAudioVaultTag(fileLineBottom, T_PUB, T_PUB.length(), 36));
        metadata.add(ALBUM_LICENSEE, getAudioVaultTag(fileLineBottom, LICENSE, LICENSE.length(), 10));
        metadata.add(ALBUM_CATALOG_NUMBER, getAudioVaultTag(fileLineBottom, CATALOG_NO, CATALOG_NO.length(), 10));
        metadata.add(ALBUM_UPC_NUMBER, getAudioVaultTag(fileLineBottom, UPC, UPC.length(), 22));
        metadata.add(ALBUM_ISRC, getAudioVaultTag(fileLineBottom, T_SRC, T_SRC.length(), 10));
        metadata.add(SOUNDTRACK_PRODUCER, getAudioVaultTag(fileLineBottom, PRODUCER, PRODUCER.length(), 10));
        metadata.add(SOUNDTRACK_DIRECTOR, getAudioVaultTag(fileLineBottom, DIRECTOR, DIRECTOR.length(), 10));
        metadata.add(SOUNDTRACK_BOOK_AUTHOR, getAudioVaultTag(fileLineBottom, BOOK, BOOK.length(), 10));
        metadata.add(SOUNDTRACK_SCREEN_PLAY, getAudioVaultTag(fileLineBottom, SCREENPLAY, SCREENPLAY.length(), 10));
        metadata.add(SOUNDTRACK_CAST, getAudioVaultTag(fileLineBottom, CAST, CAST.length(), 10));
    }

    private String getAudioVaultTag(String header, String avTag, int tagLenght, int valueLenght) {
        return header.substring(header.indexOf(avTag) + tagLenght, header.indexOf(avTag) + tagLenght + valueLenght).replace("\u0000", "").replace("'\u0000' 0", "").trim();
    }

}
