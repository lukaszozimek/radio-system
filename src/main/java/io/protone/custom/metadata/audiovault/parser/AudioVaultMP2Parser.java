package io.protone.custom.metadata.audiovault.parser;

import com.google.common.base.Strings;
import io.protone.custom.consts.MarkerConstans;
import io.protone.custom.metadata.ProtoneMetadataProperty;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.audio.AudioParser;
import org.apache.tika.parser.mp4.MP4Parser;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static io.protone.custom.consts.MarkerConstans.EMPTY_TIMER;
import static io.protone.custom.metadata.ProtoneMetadataProperty.*;

/**
 * Created by lukaszozimek on 16/03/2017.
 */
public class AudioVaultMP2Parser extends MP4Parser {
    private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.audio("av-mp2"));

    public static final String HELLO_MIME_TYPE = "audio/av-mp2";
    private Map<String, String> metadataMap;

    public AudioVaultMP2Parser() {
        metadataMap = new HashMap<>();
        metadataMap.put(MarkerConstans.AUDe, MarkerConstans.AUDe);
        metadataMap.put(MarkerConstans.AUDs, MarkerConstans.AUDs);
        metadataMap.put(MarkerConstans.TERs, MarkerConstans.TERs);
        metadataMap.put(MarkerConstans.TERe, MarkerConstans.TERe);
        metadataMap.put(MarkerConstans.SEGs, MarkerConstans.SEGs);
        metadataMap.put(MarkerConstans.SEGe, MarkerConstans.SEGe);
        metadataMap.put(MarkerConstans.INT, MarkerConstans.INT);

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

       /// super.parse(inputStreamSupplier.get(), handler, metadata, context);
        cartChunk(inputStreamSupplier.get(), metadata);
        listAV(inputStreamSupplier.get(), metadata);
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
        if (splitedUser[splitedUser.length - 1].length() >= 101) {
            splitedUser[splitedUser.length - 1] = splitedUser[splitedUser.length - 1].substring(0, 100);
        }
        if (splitedUser.length >= 2) {
            metadata.add(DATE_MODIFIED, splitedUser[1].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 3) {
            metadata.add(DATE_ADD, splitedUser[2].replace("\u0000", "").trim());
        }

        if (splitedUser.length >= 4) {
            metadata.add(CATEGORY, splitedUser[3].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 5) {

            metadata.add(CLASS, splitedUser[4].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 6) {

            metadata.add(INTRO_TIME, splitedUser[5].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 7) {
            metadata.add(SOURCE_DISK, splitedUser[6].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 8) {

            metadata.add(SOURCE_TRACK, splitedUser[7].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 9) {


            metadata.add(SONG_ID, splitedUser[8].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 10) {
            metadata.add(COMPOSER, splitedUser[9].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 11) {
            metadata.add(LYRICIST, splitedUser[10].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 12) {
            metadata.add(CLICK_URL, splitedUser[11].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 13) {
            metadata.add(TEMPO, splitedUser[12].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 14) {
            metadata.add(GENRE, splitedUser[13].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 15) {
            metadata.add(SUBCATEGORY, splitedUser[14].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 16) {
            metadata.add(LANGUAGE, splitedUser[15].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 17) {
            metadata.add(ProtoneMetadataProperty.ALBUM_NAME, splitedUser[16].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 18) {
            // metadata.add("AVListAlbumTrackName", splitedUser[1]);
        }
        if (splitedUser.length >= 18) {
            metadata.add(ALBUM_ART_LINK, splitedUser[17].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 19) {
            metadata.add(ALBUM_REL_YEAR, splitedUser[18].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 20) {
            metadata.add(ALBUM_ORIGIN_YEAR, splitedUser[19].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 21) {
            metadata.add(ALBUM_MUZE_NUMBER, splitedUser[20].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 22) {
            metadata.add(ALBUM_RECORD_LABEL, splitedUser[21].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 23) {
            metadata.add(ALBUM_PUBLISHER, splitedUser[22].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 24) {
            metadata.add(ALBUM_LICENSEE, splitedUser[23].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 25) {
            metadata.add(ALBUM_CATALOG_NUMBER, splitedUser[24].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 26) {
            metadata.add(ALBUM_UPC_NUMBER, splitedUser[25].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 27) {
            metadata.add(ALBUM_ISRC, splitedUser[26].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 28) {
            metadata.add(SOUNDTRACK_PRODUCER, splitedUser[27].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 29) {
            metadata.add(SOUNDTRACK_DIRECTOR, splitedUser[28].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 30) {
            metadata.add(SOUNDTRACK_BOOK_AUTHOR, splitedUser[29].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 31) {
            metadata.add(SOUNDTRACK_SCREEN_PLAY, splitedUser[30].replace("\u0000", "").trim());
        }
        if (splitedUser.length >= 32) {
            metadata.add(SOUNDTRACK_CAST, splitedUser[31].replace("\u0000", "").trim());
        }
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

        byte[] inputStream = new byte[stream.available()];
        stream.read(inputStream);
        stream.close();
        String file = new String(inputStream);
        int index = file.indexOf("cart");
        String s2 = new String(inputStream);
        s2 = s2.substring(index + 12, 2000);

        metadata.add(ProtoneMetadataProperty.TITLE, s2.substring(0, 64).trim());
        metadata.add(ProtoneMetadataProperty.ARTIST, s2.substring(64, 128).trim());
        metadata.add(CART_CHUNK_AUDIOVAULTID, s2.substring(128, 192).trim());
        metadata.add(CART_CHUNK_CLIENTID, s2.substring(192, 256).trim());
        metadata.add(CART_CHUNK_CATEGORYID, s2.substring(256, 320).trim());
        metadata.add(CART_CHUNK_CLASSYFICATION, s2.substring(320, 384).trim());
        metadata.add(CART_CHUNK_OUT_CUE, s2.substring(384, 448).trim());
        metadata.add(CART_CHUNK_START_DATE, s2.substring(448, 458).trim());
        metadata.add(CART_CHUNK_START_TIME, s2.substring(458, 468).trim());
        metadata.add(CART_CHUNK_END_DATE, s2.substring(468, 478).trim());
        metadata.add(CART_CHUNK_END_TIME, s2.substring(478, 484).trim());
        metadata.add(CART_CHUNK_PRODUCER_APP_ID, s2.substring(484, 548).trim());
        metadata.add(CART_CHUNK_PRODUCER_APP_VERSION, s2.substring(548, 614).trim());

        metadata.add(resolveTimerType(s2.substring(680, 688)), String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(680, 688))));
        metadata.add(resolveTimerType(s2.substring(688, 696)), String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(688, 696))));
        metadata.add(resolveTimerType(s2.substring(696, 704)), String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(696, 704))));
        metadata.add(resolveTimerType(s2.substring(704, 712)), String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(703, 712))));
        metadata.add(resolveTimerType(s2.substring(712, 720)), String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(712, 720))));
        metadata.add(resolveTimerType(s2.substring(720, 728)), String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(720, 728))));
        metadata.add(resolveTimerType(s2.substring(728, 736)), String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(728, 736))));
        metadata.add(resolveTimerType(s2.substring(736, 744)), String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(736, 744))));

    }

    private String resolveTimerType(String timer) {
        if (!Strings.isNullOrEmpty(timer.substring(0, 4))) {
            if (Strings.isNullOrEmpty(metadataMap.get(timer.substring(0, 4).trim()))) {
                return EMPTY_TIMER;
            }
            return metadataMap.get(timer.substring(0, 4));
        } else {
            return EMPTY_TIMER;
        }
    }

    private long convertCartChunkTimerToSampleOffset(String timer) {
        return ToUInt32(timer.substring(4).getBytes(), 0);

    }

    public static long ToUInt32(byte[] bytes, int offset) {
        long result = (int) bytes[offset] & 0xff;
        result |= ((int) bytes[offset + 1] & 0xff) << 8;
        result |= ((int) bytes[offset + 2] & 0xff) << 16;
        result |= ((int) bytes[offset + 3] & 0xff) << 24;
        return result & 0xFFFFFFFFL;
    }
}
