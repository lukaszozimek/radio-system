package io.protone.library.service.metadata;

import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.metadata.XMPDM;

/**
 * Created by lukaszozimek on 18/03/2017.
 */
public interface ProtoneMetadataProperty extends TikaCoreProperties, XMPDM {
    String DATE_MODIFIED = "DateModified";
    String DATE_ADD = "DateAdd";
    String CATEGORY = "Category";
    String CLASS = "Class";
    String INTRO_TIME = "IntroTime";
    String SOURCE_DISK = "SourceDisk";
    String SOURCE_TRACK = "sourcetrack";
    String SONG_ID = "SongID";
    String COMPOSER = "Composer";
    String LYRICIST = "Liricist";
    String CLICK_URL = "ClickUrl";
    String TEMPO = "Tempo";
    String GENRE = "Genre";
    String SUBCATEGORY = "Subcategory";
    String LANGUAGE = "Language";
    String ALBUM_NAME = "AlbumName";
    String ALBUM_ART_LINK = "AlbumArtLink";
    String ALBUM_REL_YEAR = "AlbumRelYear";
    String ALBUM_ORIGIN_YEAR = "AlbumOrignYear";
    String ALBUM_MUZE_NUMBER = "AlbumMuzeNumber";
    String ALBUM_RECORD_LABEL = "AlbumRecordLabel";
    String ALBUM_PUBLISHER = "AlbumPublisher";
    String ALBUM_LICENSEE = "AlbumLicensee";
    String ALBUM_CATALOG_NUMBER = "AlbumCatalogNumber";
    String ALBUM_UPC_NUMBER = "AlbumUPCNumber";
    String ALBUM_ISRC = "AlbumISRC";
    String SOUNDTRACK_PRODUCER = "SoundtrackProducer";
    String SOUNDTRACK_DIRECTOR = "SoundtrackDirector";
    String SOUNDTRACK_BOOK_AUTHOR = "SoundtrackBookAuthor";
    String SOUNDTRACK_SCREEN_PLAY = "SoundtrackScreenPlay";
    String SOUNDTRACK_CAST = "SoundtrackCast";
    String CART_CHUNK_TITLE ="CartChunkTitle";
    String CART_CHUNK_ARTIST ="CartChunkArtist";
    String CART_CHUNK_AUDIOVAULTID ="CartChunkAudioVaultID";
    String CART_CHUNK_CLIENTID ="CartChunkClientID";
    String CART_CHUNK_CATEGORYID ="CartChunkCategoryID";
    String CART_CHUNK_CLASSYFICATION ="CartChunkClassyfication";
    String CART_CHUNK_OUT_CUE ="CartChunkOutCue";
    String CART_CHUNK_START_DATE ="CartChunkStartDate";
    String CART_CHUNK_START_TIME ="CartChunkStartTime";
    String CART_CHUNK_END_DATE ="CartChunkEndDate";
    String CART_CHUNK_END_TIME ="CartChunkEndTime";
    String CART_CHUNK_PRODUCER_APP_ID ="CartChunkProducerAppID";
    String CART_CHUNK_PRODUCER_APP_VERSION ="CartChunkProducerAppVersion";
    String CART_CHUNK_TIMER_AUD_S ="CartChunkTimerAUDs";
    String CART_CHUNK_TIMER_AUD_E ="CartChunkTimerAUDe";
    String CART_CHUNK_TIMER_SEG_S ="CartChunkTimerSEGs";
    String CART_CHUNK_TIMER_SEG_E ="CartChunkTimerSEGe";
    String CART_CHUNK_TIMER_INT ="CartChunkTimerINT";
    String CART_CHUNK_FIFTH_TIMER ="CartChunkFifthTimer";
    String CART_CHUNK_SIXTH_TIMER ="CartChunkSixthTimer";
    String CART_CHUNK_SEVENTH_TIMER ="CartChunkSeventhTimer";
    String CART_CHUNK_EIGHT_TIMER ="CartChunkEightTimer";
}
