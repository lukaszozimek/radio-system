package io.protone.custom.metadata.audiovault.parser;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.audio.AudioParser;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import static io.protone.custom.metadata.ProtoneMetadataProperty.*;

/**
 * Created by lukaszozimek on 16/03/2017.
 */
public class AudioVaultWaveParser extends AudioParser {

    private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.audio("av-wav"));

    public static final String HELLO_MIME_TYPE = "audio/av-wav";

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

        audioParser.parse(stream, handler, metadata, context);
        metadata.set(Metadata.CONTENT_TYPE, HELLO_MIME_TYPE);
        cartChunk(stream, metadata);
        listAV(stream, metadata);
        XHTMLContentHandler xhtml = new XHTMLContentHandler(handler, metadata);
        xhtml.startDocument();
        xhtml.endDocument();
        /// listBextAV(stream, metadata);
        //  av10(stream, metadata);

    }

    private void listAV(InputStream stream, Metadata metadata) throws IOException {
        byte[] inputStream = new byte[stream.available()];
        stream.read(inputStream);
        String file = new String(inputStream);
        int index = file.indexOf("LIST");
        String listChunkMetadata = file.substring(index, index + 1140);
        String[] splitedUser = listChunkMetadata.split("USER");
        metadata.add(DATE_MODIFIED, splitedUser[1]);
        metadata.add(DATE_ADD, splitedUser[2]);
        metadata.add(CATEGORY, splitedUser[3]);
        metadata.add(CLASS, splitedUser[4]);
        metadata.add(INTROTIME, splitedUser[5]);
        metadata.add(SOURCEDISK, splitedUser[6]);
        metadata.add(SOURCE_TRACK, splitedUser[7]);
        metadata.add(SONG_ID, splitedUser[8]);
        metadata.add(COMPOSER, splitedUser[9]);
        metadata.add(LIRICIST, splitedUser[10]);
        metadata.add(CLICK_URL, splitedUser[11]);
        metadata.add(TEMPO, splitedUser[12]);
        metadata.add(GENRE, splitedUser[13]);
        metadata.add(SUBCATEGORY, splitedUser[14]);
        metadata.add(LANGUAGE, splitedUser[15]);
        metadata.add(ALBUM_NAME, splitedUser[16]);
        // metadata.add("AVListAlbumTrackName", splitedUser[1]);
        metadata.add(ALBUM_ART_LINK, splitedUser[17]);
        metadata.add(ALBUM_REL_YEAR, splitedUser[18]);
        metadata.add(ALBUM_ORIGN_YEAR, splitedUser[19]);
        metadata.add(ALBUM_MUZE_NUMBER, splitedUser[20]);
        metadata.add(ALBUM_RECORD_LABEL, splitedUser[21]);
        metadata.add(ALBUM_PUBLISHER, splitedUser[22]);
        metadata.add(ALBUM_LICENSEE, splitedUser[23]);
        metadata.add(ALBUM_CATALOG_NUMBER, splitedUser[24]);
        metadata.add(ALBUM_UPC_NUMBER, splitedUser[25]);
        metadata.add(ALBUM_ISRC, splitedUser[26]);
        metadata.add(SOUNDTRACK_PRODUCER, splitedUser[27]);
        metadata.add(SOUNDTRACK_DIRECTOR, splitedUser[28]);
        metadata.add(SOUNDTRACK_BOOK_AUTHOR, splitedUser[29]);
        metadata.add(SOUNDTRACK_SCREEN_PLAY, splitedUser[30]);
        metadata.add(SOUNDTRACK_CAST, splitedUser[31]);


    }

    private void listBextAV(InputStream stream, Metadata metadata) throws IOException {
        byte[] inputStream = new byte[stream.available()];
        stream.read(inputStream);
        String file = new String(inputStream);
        int index = file.indexOf("bext");
        String listChunkMetadata = file.substring(index, index + 604);
    }

    private void av10(InputStream stream, Metadata metadata) throws IOException {
        byte[] inputStream = new byte[stream.available()];
        stream.read(inputStream);
        String file = new String(inputStream);
        int index = file.indexOf("av10");
        String listChunkMetadata = file.substring(index, index + 141);
    }

    private void cartChunk(InputStream stream, Metadata metadata) throws IOException {

        byte[] buffer = new byte[758];
        stream.skip(46);
        stream.read(buffer, 0, 758);
        String s2 = new String(buffer);
        metadata.add("CartChunkTitle", s2.substring(0, 64).trim());
        metadata.add("CartChunkArtist", s2.substring(64, 128).trim());
        metadata.add("CartChunkAudioVaultID", s2.substring(128, 192).trim());
        metadata.add("CartChunkClientID", s2.substring(192, 256).trim());
        metadata.add("CartChunkCategoryID", s2.substring(256, 320).trim());
        metadata.add("CartChunkClassyfication", s2.substring(320, 384).trim());
        metadata.add("CartChunkOutCue", s2.substring(384, 448).trim());
        metadata.add("CartChunkStartDate", s2.substring(452, 462).trim());
        metadata.add("CartChunkStartTime", s2.substring(462, 470).trim());
        metadata.add("CartChunkEndDate", s2.substring(470, 480).trim());
        metadata.add("CartChunkEndTime", s2.substring(480, 488).trim());
        metadata.add("CartChunkProducerAppID", s2.substring(488, 552).trim());
        metadata.add("CartChunkProducerAppVersion", s2.substring(552, 616).trim());

        metadata.add("CartChunkTimerAUDe",
            String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(684, 692))));
        metadata.add("CartChunkTimerSEGs", String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(692, 699))));
        metadata.add("CartChunkTimerSEGe", String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(699, 707))));
        metadata.add("CartChunkTimerINT", String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(707, 715))));
        metadata.add("CartChunkFifthTimer", s2.substring(692, 700).trim());
        metadata.add("CartChunkSixthTimer", s2.substring(692, 700).trim());
        metadata.add("CartChunkSeventhTimer", s2.substring(692, 700).trim());
        metadata.add("CartChunkEightTimer", s2.substring(692, 700).trim());

    }

    private long convertCartChunkTimerToSampleOffset(String timerValuePosition) {
        return ToUInt32(timerValuePosition.substring(4).getBytes(), 0);

    }

    public static long ToUInt32(byte[] bytes, int offset) {
        long result = (int) bytes[offset] & 0xff;
        result |= ((int) bytes[offset + 1] & 0xff) << 8;
        result |= ((int) bytes[offset + 2] & 0xff) << 16;
        result |= ((int) bytes[offset + 3] & 0xff) << 24;
        return result & 0xFFFFFFFFL;
    }

}
