package io.protone.service.metadata.audiovault.parser;

import io.protone.service.constans.MarkerConstans;
import io.protone.service.metadata.ProtoneMetadataProperty;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp4.MP4Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.Supplier;

import static io.protone.service.metadata.ProtoneMetadataProperty.*;

/**
 * Created by lukaszozimek on 16/03/2017.
 */
public class AudioVaultMP2Parser extends MP4Parser {
    private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.audio("av-mp2"));

    public static final String HELLO_MIME_TYPE = "audio/av-mp2";


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

        metadata.add(MarkerConstans.AUDs, String.valueOf(getFindAudioStartMarker(inputStream, MarkerConstans.AUDs)));
        metadata.add(MarkerConstans.AUDe, String.valueOf(getFindAudioStartMarker(inputStream, MarkerConstans.AUDe)));
        metadata.add(MarkerConstans.INT, String.valueOf(getFindAudioStartMarker(inputStream, MarkerConstans.INT)));
        metadata.add(MarkerConstans.SEGs, String.valueOf(getFindAudioStartMarker(inputStream, MarkerConstans.SEGs)));
        metadata.add(MarkerConstans.SEGe, String.valueOf(getFindAudioStartMarker(inputStream, MarkerConstans.SEGe)));
        metadata.add(MarkerConstans.TERe, String.valueOf(getFindAudioStartMarker(inputStream, MarkerConstans.TERe)));
        metadata.add(MarkerConstans.TERs, String.valueOf(getFindAudioStartMarker(inputStream, MarkerConstans.TERs)));


    }

    private long getFindAudioStartMarker(byte[] inputStream, String marker) {
        int markerStartIndex = indexOf(inputStream, marker.getBytes());
        if (markerStartIndex != -1) {
            byte[] timerValue = Arrays.copyOfRange(inputStream, markerStartIndex + marker.getBytes().length, markerStartIndex + marker.getBytes().length + 4);
            return convertCartChunkTimerToSampleOffset(timerValue);
        }
        return 0;
    }


    private Long convertCartChunkTimerToSampleOffset(byte[] timer) {
        return Long.valueOf(ByteBuffer.wrap(timer).order(ByteOrder.LITTLE_ENDIAN).getInt());
    }

    public int indexOf(byte[] data, byte[] pattern) {
        int[] failure = computeFailure(pattern);

        int j = 0;
        if (data.length == 0) return -1;

        for (int i = 0; i < data.length; i++) {
            while (j > 0 && pattern[j] != data[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == data[i]) {
                j++;
            }
            if (j == pattern.length) {
                return i - pattern.length + 1;
            }
        }
        return -1;
    }

    private int[] computeFailure(byte[] pattern) {
        int[] failure = new int[pattern.length];

        int j = 0;
        for (int i = 1; i < pattern.length; i++) {
            while (j > 0 && pattern[j] != pattern[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == pattern[i]) {
                j++;
            }
            failure[i] = j;
        }

        return failure;
    }

}
