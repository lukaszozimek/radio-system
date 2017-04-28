package io.protone.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibItemStateEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 28.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibItemMapperTest {

    private static final String TEST_TAG = "SimpleTag";
    @Autowired
    private LibItemMapper customItemMapperExt;

    private LibMediaItem libMediaItem;

    private LibItemPT libItemPT;

    private CorTag corTag;

    private CorNetwork corNetwork;

    private List<LibItemPT> libItemPTS = new ArrayList<>();

    private List<LibMediaItem> libMediaItems = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItems.add(libMediaItem);
        libItemPT = factory.manufacturePojo(LibItemPT.class);
        libItemPTS.add(libItemPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corTag = factory.manufacturePojo(CorTag.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        LibItemPT dto = customItemMapperExt.DB2DTO(libMediaItem);


        assertNotNull(dto.getId());
        assertNotNull(dto.getIdx());
        assertNotNull(dto.getName());
        assertNotNull(dto.getLength());
        assertNotNull(dto.getState());
        assertNotNull(dto.getLibrary());
        assertNotNull(dto.getLabel());
        assertNotNull(dto.getArtist());
        assertNotNull(dto.getAlbum());
        assertNotNull(dto.getTrack());
        assertNotNull(dto.getAuthors());
        assertNotNull(dto.getComposers());
        assertNotNull(dto.getMarkers());
        assertNotNull(dto.getTags());
        assertNotNull(dto.getId());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<LibItemPT> dtos = customItemMapperExt.DBs2DTOs(libMediaItems);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getIdx());
            assertNotNull(dto.getName());
            assertNotNull(dto.getLength());
            assertNotNull(dto.getState());
            assertNotNull(dto.getLibrary());
            assertNotNull(dto.getLabel());
            assertNotNull(dto.getArtist());
            assertNotNull(dto.getAlbum());
            assertNotNull(dto.getTrack());
            assertNotNull(dto.getAuthors());
            assertNotNull(dto.getComposers());
            assertNotNull(dto.getMarkers());
            assertNotNull(dto.getTags());
            assertNotNull(dto.getId());
        });
    }


    @Test
    public void DTO2DB() throws Exception {
        ///TODO: Network Constraing
        LibMediaItem entity = customItemMapperExt.DTO2DB(libItemPT, corNetwork);

        assertNotNull(entity.getId());
        assertNotNull(entity.getAlbum());
        assertNotNull(entity.getArtist());
        assertNotNull(entity.getAuthors());
        assertNotNull(entity.getComposers());
        assertNotNull(entity.getIdx());
        assertNotNull(entity.getLabel());
        assertNotNull(entity.getLength().intValue());
        assertNotNull(entity.getLibrary());
        assertNotNull(entity.getMarkers());
        assertNotNull(entity.getName());
        assertNotNull(entity.getState());
        assertNotNull(entity.getTags());
        assertNotNull(entity.getTrack());
        assertNotNull(entity.getId());


    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<LibMediaItem> entities = customItemMapperExt.DTOs2DBs(libItemPTS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getAlbum());
            assertNotNull(entity.getArtist());
            assertNotNull(entity.getAuthors());
            assertNotNull(entity.getComposers());
            assertNotNull(entity.getIdx());
            assertNotNull(entity.getLabel());
            assertNotNull(entity.getLength().intValue());
            assertNotNull(entity.getLibrary());
            assertNotNull(entity.getMarkers());
            assertNotNull(entity.getName());
            assertNotNull(entity.getState());
            assertNotNull(entity.getTags());
            assertNotNull(entity.getTrack());
            assertNotNull(entity.getId());
        });
    }


    @Test
    public void mapState() throws Exception {
        LibItemStateEnum archived = customItemMapperExt.mapState(LibItemPT.StateEnum.ARCHIVED);
        assertEquals(archived, archived.IS_ARCHIVED);

        LibItemStateEnum deleted = customItemMapperExt.mapState(LibItemPT.StateEnum.DELETED);
        assertEquals(deleted, archived.IS_DELETED);

        LibItemStateEnum disabled = customItemMapperExt.mapState(LibItemPT.StateEnum.DISABLED);
        assertEquals(disabled, archived.IS_DISABLED);

        LibItemStateEnum enabled = customItemMapperExt.mapState(LibItemPT.StateEnum.ENABLED);
        assertEquals(enabled, enabled.IS_ENABLED);

        LibItemStateEnum newState = customItemMapperExt.mapState(LibItemPT.StateEnum.NEW);
        assertEquals(newState, newState.IS_NEW);

        LibItemStateEnum other = customItemMapperExt.mapState(LibItemPT.StateEnum.OTHER);
        assertEquals(other, other.IS_OTHER);

        LibItemStateEnum postProcess = customItemMapperExt.mapState(LibItemPT.StateEnum.POSTPROCESS);
        assertEquals(postProcess, postProcess.IS_POSTPROCESS);

    }

    @Test
    public void corTagFromString() throws Exception {
        CorTag corsTag = customItemMapperExt.corTagFromString(TEST_TAG, corNetwork);
        assertEquals(TEST_TAG, corsTag.getTag());
        assertNotNull(TEST_TAG, corsTag.getNetwork());

    }

    @Test
    public void stringFromCorTag() throws Exception {
        corTag.setTag(TEST_TAG);
        String tag = customItemMapperExt.stringFromCorTag(corTag);
        assertEquals(TEST_TAG, tag);
    }

    @Test
    public void mapResourceType() throws Exception {
    }

    @Test
    public void mapItemType() throws Exception {
    }

}
