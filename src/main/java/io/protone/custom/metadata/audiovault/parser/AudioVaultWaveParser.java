package io.protone.custom.metadata.audiovault.parser;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.audio.AudioParser;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

import static io.protone.custom.metadata.ProtoneMetadataProperty.*;

/**
 * Created by lukaszozimek on 16/03/2017.
 */
public class AudioVaultWaveParser extends AudioParser {

    private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.audio("av-wav"));

    public static final String HELLO_MIME_TYPE = "audio/av-wav";

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
        cartChunk(inputStreamSupplier.get(), metadata);
        listAV(inputStreamSupplier.get(), metadata);
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
        metadata.add(DATE_MODIFIED, splitedUser[1].replace("\u0000","").trim());
        metadata.add(DATE_ADD, splitedUser[2].replace("\u0000","").trim());
        metadata.add(CATEGORY, splitedUser[3].replace("\u0000","").trim());
        metadata.add(CLASS, splitedUser[4].replace("\u0000","").trim());
        metadata.add(INTRO_TIME, splitedUser[5].replace("\u0000","").trim());
        metadata.add(SOURCE_DISK, splitedUser[6].replace("\u0000","").trim());
        metadata.add(SOURCE_TRACK, splitedUser[7].replace("\u0000","").trim());
        metadata.add(SONG_ID, splitedUser[8].replace("\u0000","").trim());
        metadata.add(COMPOSER, splitedUser[9].replace("\u0000","").trim());
        metadata.add(LYRICIST, splitedUser[10].replace("\u0000","").trim());
        metadata.add(CLICK_URL, splitedUser[11].replace("\u0000","").trim());
        metadata.add(TEMPO, splitedUser[12].replace("\u0000","").trim());
        metadata.add(GENRE, splitedUser[13].replace("\u0000","").trim());
        metadata.add(SUBCATEGORY, splitedUser[14].replace("\u0000","").trim());
        metadata.add(LANGUAGE, splitedUser[15].replace("\u0000","").trim());
        metadata.add(ALBUM_NAME, splitedUser[16].replace("\u0000","").trim());
        // metadata.add("AVListAlbumTrackName", splitedUser[1]);
        metadata.add(ALBUM_ART_LINK, splitedUser[17].replace("\u0000","").trim());
        metadata.add(ALBUM_REL_YEAR, splitedUser[18].replace("\u0000","").trim());
        metadata.add(ALBUM_ORIGIN_YEAR, splitedUser[19].replace("\u0000","").trim());
        metadata.add(ALBUM_MUZE_NUMBER, splitedUser[20].replace("\u0000","").trim());
        metadata.add(ALBUM_RECORD_LABEL, splitedUser[21].replace("\u0000","").trim());
        metadata.add(ALBUM_PUBLISHER, splitedUser[22].replace("\u0000","").trim());
        metadata.add(ALBUM_LICENSEE, splitedUser[23].replace("\u0000","").trim());
        metadata.add(ALBUM_CATALOG_NUMBER, splitedUser[24].replace("\u0000","").trim());
        metadata.add(ALBUM_UPC_NUMBER, splitedUser[25].replace("\u0000","").trim());
        metadata.add(ALBUM_ISRC, splitedUser[26].replace("\u0000","").trim());
        metadata.add(SOUNDTRACK_PRODUCER, splitedUser[27].replace("\u0000","").trim());
        metadata.add(SOUNDTRACK_DIRECTOR, splitedUser[28].replace("\u0000","").trim());
        metadata.add(SOUNDTRACK_BOOK_AUTHOR, splitedUser[29].replace("\u0000","").trim());
        metadata.add(SOUNDTRACK_SCREEN_PLAY, splitedUser[30].replace("\u0000","").trim());
        metadata.add(SOUNDTRACK_CAST, splitedUser[31].replace("\u0000","").trim());
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
        metadata.add(CART_CHUNK_TITLE, s2.substring(0, 64).trim());
        metadata.add(CART_CHUNK_ARTIST, s2.substring(64, 128).trim());
        metadata.add(CART_CHUNK_AUDIOVAULTID, s2.substring(128, 192).trim());
        metadata.add(CART_CHUNK_CLIENTID, s2.substring(192, 256).trim());
        metadata.add(CART_CHUNK_CATEGORYID, s2.substring(256, 320).trim());
        metadata.add(CART_CHUNK_CLASSYFICATION, s2.substring(320, 384).trim());
        metadata.add(CART_CHUNK_OUT_CUE, s2.substring(384, 448).trim());
        metadata.add(CART_CHUNK_START_DATE, s2.substring(452, 462).trim());
        metadata.add(CART_CHUNK_START_TIME, s2.substring(462, 470).trim());
        metadata.add(CART_CHUNK_END_DATE, s2.substring(470, 480).trim());
        metadata.add(CART_CHUNK_END_TIME, s2.substring(480, 488).trim());
        metadata.add(CART_CHUNK_PRODUCER_APP_ID, s2.substring(488, 552).trim());
        metadata.add(CART_CHUNK_PRODUCER_APP_VERSION, s2.substring(552, 616).trim());

        metadata.add(CART_CHUNK_TIMER_AUD_E,
            String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(684, 692))));
        metadata.add(CART_CHUNK_TIMER_SEG_S, String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(692, 699))));
        metadata.add(CART_CHUNK_TIMER_SEG_E, String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(699, 707))));
        metadata.add(CART_CHUNK_TIMER_INT, String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(707, 715))));
        metadata.add(CART_CHUNK_FIFTH_TIMER, s2.substring(692, 700).trim());
        metadata.add(CART_CHUNK_SIXTH_TIMER, s2.substring(692, 700).trim());
        metadata.add(CART_CHUNK_SEVENTH_TIMER, s2.substring(692, 700).trim());
        metadata.add(CART_CHUNK_EIGHT_TIMER, s2.substring(692, 700).trim());

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
