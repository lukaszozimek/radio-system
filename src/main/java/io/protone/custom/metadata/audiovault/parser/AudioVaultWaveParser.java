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
        listChunkAV(stream, metadata);
        XHTMLContentHandler xhtml = new XHTMLContentHandler(handler, metadata);
        xhtml.startDocument();
        xhtml.endDocument();
        //  listBextAV(stream, metadata);
        //  av10(stream, metadata);

    }

    private void listChunkAV(InputStream stream, Metadata metadata) throws IOException {
        byte[] inputStream = new byte[stream.available()];
        stream.read(inputStream);
        String file = new String(inputStream);
        int index = file.indexOf("LIST");
        String listChunkMetadata = file.substring(index, index + 1140);
        metadata.add("AVListChunkDateModified", "");
        metadata.add("AVListChunkDateAdd", "");
        metadata.add("AVListChunkDateAdd", "");
        metadata.add("AVListChunkCategory", "");
        metadata.add("AVListChunkCategory", "");
        metadata.add("AVListChunkClass", "");
        metadata.add("AVListChunkClass", "");
        metadata.add("AVListChunkIntroTime", "");
        metadata.add("AVListChunkSourceDisk", "");
        metadata.add("AVListChunkSourceDisk", "");
        metadata.add("AVListChunkSourceTrack", "");
        metadata.add("AVListChunkSongID", "");
        metadata.add("AVListLiricist", "");
        metadata.add("AVListLiricist", "");
        metadata.add("AVListClickUrl", "");
        metadata.add("AVListTempo", "");
        metadata.add("AVListGenre", "");
        metadata.add("AVListSubcategory", "");
        metadata.add("AVListLanguage", "");
        metadata.add("AVListAlbumName", "");
        metadata.add("AVListAlbumTrackName", "");
        metadata.add("AVListAlbumArtLink", "");
        metadata.add("AVListAlbumRelYear", "");
        metadata.add("AVListAlbumOrignYear", "");
        metadata.add("AVListAlbumMuzeNumber", "");
        metadata.add("AVListAlbumRecordLabel", "");
        metadata.add("AVListAlbumPublisher", "");
        metadata.add("AVListAlbumLicensee", "");
        metadata.add("AVListAlbumCatalogNumber", "");
        metadata.add("AVListAlbumUPCNumber", "");
        metadata.add("AVListAlbumISRC", "");
        metadata.add("AVListSoundtrackProducer", "");
        metadata.add("AVListSoundtrackDirector", "");
        metadata.add("AVListSoundtrackBookAuthor", "");
        metadata.add("AVListSoundtrackScreenPlay", "");
        metadata.add("AVListSoundtrackCast", "");


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
        metadata.add("CartChunkTimerSEGs",  String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(692, 699))));
        metadata.add("CartChunkTimerSEGe",  String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(699, 707))));
        metadata.add("CartChunkTimerINT",  String.valueOf(convertCartChunkTimerToSampleOffset(s2.substring(707, 715))));
        metadata.add("CartChunkFifthTimer", s2.substring(692, 700).trim());
        metadata.add("CartChunkSixthTimer", s2.substring(692, 700).trim());
        metadata.add("CartChunkSeventhTimer", s2.substring(692, 700).trim());
        metadata.add("CartChunkEightTimer", s2.substring(692, 700).trim());

    }

    private long convertCartChunkTimerToSampleOffset(String timerValuePosition) {
        return ToUInt32(timerValuePosition.substring(4).getBytes(), 0);

    }

    public static long ToUInt32(byte[] bytes, int offset) {
        long result = (int)bytes[offset]&0xff;
        result |= ((int)bytes[offset+1]&0xff) << 8;
        result |= ((int)bytes[offset+2]&0xff) << 16;
        result |= ((int)bytes[offset+3]&0xff) << 24;
        return result & 0xFFFFFFFFL;
    }

}
