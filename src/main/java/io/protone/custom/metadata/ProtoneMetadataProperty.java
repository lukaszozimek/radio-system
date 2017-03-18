package io.protone.custom.metadata;

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
    String INTROTIME = "IntroTime";
    String SOURCEDISK = "SourceDisk";
    String SOURCE_TRACK = "sourcetrack";
    String SONG_ID = "SongID";
    String COMPOSER = "Composer";
    String LIRICIST = "Liricist";
    String CLICK_URL = "ClickUrl";
    String TEMPO = "Tempo";
    String GENRE = "Genre";
    String SUBCATEGORY = "Subcategory";
    String LANGUAGE = "Language";
    String ALBUM_NAME = "AlbumName";
    String ALBUM_ART_LINK = "AlbumArtLink";
    String ALBUM_REL_YEAR = "AlbumRelYear";
    String ALBUM_ORIGN_YEAR = "AlbumOrignYear";
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
}
