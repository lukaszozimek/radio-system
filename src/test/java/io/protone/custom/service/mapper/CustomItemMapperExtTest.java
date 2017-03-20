package io.protone.custom.service.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.ProtoneApp;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.LibMediaItemPT;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;
import org.hibernate.annotations.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;
import javax.persistence.Cache;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CustomItemMapperExtTest {
    @Autowired
    private CustomItemMapperExt itemMapperExt;
    private CorNetwork mockCorNetwork = null;

    private LibMediaItem mockLibMediaItem = null;

    private LibMediaItemPT mockLibMediaItemPt = null;

    private LibItemPT libItemPT = null;

    private List<LibMediaItem> mockListLibMediaItem = null;

    private List<LibItemPT> mockListLibMediaItemPt = null;

    @Before
    public void initialize() {
        mockLibMediaItem = new LibMediaItem();
        mockLibMediaItem.setId((long) 1);
        mockLibMediaItem.idx("test")
            .name("test")
            .itemType(LibItemTypeEnum.IT_AUDIO)
            .length((long) 1)
            .state(LibItemStateEnum.IS_ARCHIVED)
            .description("test")
            .command("test")
            .library(new LibLibrary())
            .label(new LibLabel())
            .artist(new LibArtist())
            .album(new LibAlbum())
            .track(new LibTrack())
            .addAuthor(new CorPerson())
            .addComposer(new CorPerson())
            .addMarkers(new LibMarker())
            .addTags(new CorTag())
            .addProperites(new CorPropertyValue());
        mockLibMediaItemPt = new LibMediaItemPT();
        mockLibMediaItemPt.setId((long) 1);
        mockLibMediaItemPt.setIdx("tets");
        mockLibMediaItemPt.setName("test");
        mockLibMediaItemPt.setItemType(LibItemTypeEnum.IT_AUDIO);
        mockLibMediaItemPt.setLength((long) 1);
        mockLibMediaItemPt.setState(LibItemStateEnum.IS_ARCHIVED);
        mockLibMediaItemPt.setCommand("test");
        mockLibMediaItemPt.setDescription("test");
        mockLibMediaItemPt.setLibraryId((long) 1);
        mockLibMediaItemPt.setLabelId((long) 3);
        mockLibMediaItemPt.setArtistId((long) 2);
        mockLibMediaItemPt.setAlbumId((long) 1);
        mockLibMediaItemPt.setTrackId((long) 2);
    }

    @Test
    public void DB2DTO() throws Exception {
        //then
        LibItemPT result = itemMapperExt.DB2DTO(mockLibMediaItem);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void DBs2DTOs() throws Exception {
        //then

        List<LibItemPT> result = itemMapperExt.DBs2DTOs(mockListLibMediaItem);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void DTO2DB() throws Exception {
        //then
        LibMediaItem result = itemMapperExt.DTO2DB(libItemPT);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void mapItemType() throws Exception {
        //then
        LibItemTypeEnum result = itemMapperExt.mapItemType(null);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void DTOs2DBs() throws Exception {
        //then
        List<LibMediaItem> result = itemMapperExt.DTOs2DBs(mockListLibMediaItemPt);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
