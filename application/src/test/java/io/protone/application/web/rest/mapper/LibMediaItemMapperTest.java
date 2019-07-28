package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorPerson;
import io.protone.core.domain.CorTag;
import io.protone.library.api.dto.LibMediaItemDTO;
import io.protone.library.domain.*;
import io.protone.library.mapper.LibMediaItemMapper;
import org.assertj.core.util.Sets;
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
public class LibMediaItemMapperTest {

    private static final String TEST_TAG = "SimpleTag";

    @Autowired
    private LibMediaItemMapper customItemMapperExt;

    private LibMediaItem libMediaItem;

    private LibMediaItemDTO libMediaItemDTO;

    private CorTag corTag;

    private CorNetwork corNetwork;

    private List<LibMediaItemDTO> libMediaItemDTOS = new ArrayList<>();

    private List<LibMediaItem> libMediaItems = new ArrayList<>();


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setId(1L);

        libMediaItem.setAlbum(factory.manufacturePojo(LibAlbum.class));
        libMediaItem.setArtist(factory.manufacturePojo(LibArtist.class));
        libMediaItem.setAuthors(Sets.newLinkedHashSet(factory.manufacturePojo(CorPerson.class)));
        libMediaItem.setComposers(Sets.newLinkedHashSet(factory.manufacturePojo(CorPerson.class)));
        libMediaItem.setLabel(factory.manufacturePojo(LibLabel.class));
        libMediaItem.setLibrary(factory.manufacturePojo(LibMediaLibrary.class));
        libMediaItem.setMarkers(Sets.newLinkedHashSet(factory.manufacturePojo(LibMarker.class)));
        libMediaItem.setTags(Sets.newLinkedHashSet(factory.manufacturePojo(CorTag.class)));
        libMediaItem.setTrack(factory.manufacturePojo(LibTrack.class));
        libMediaItems.add(libMediaItem);
        libMediaItemDTO = factory.manufacturePojo(LibMediaItemDTO.class);
        libMediaItemDTOS.add(libMediaItemDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corTag = factory.manufacturePojo(CorTag.class);

    }

    @Test
    public void DB2DTO() throws Exception {
        LibMediaItemDTO dto = customItemMapperExt.DB2DTO(libMediaItem);


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
        List<LibMediaItemDTO> dtos = customItemMapperExt.DBs2DTOs(libMediaItems);

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
        LibMediaItem entity = customItemMapperExt.DTO2DB(libMediaItemDTO, corNetwork);

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
        assertNotNull(entity.getNetwork());


    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<LibMediaItem> entities = customItemMapperExt.DTOs2DBs(libMediaItemDTOS, corNetwork);

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
            assertNotNull(entity.getNetwork());
        });
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


}
