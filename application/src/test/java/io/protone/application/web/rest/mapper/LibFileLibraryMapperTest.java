package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.LibFileLibraryDTO;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.mapper.LibFileLibraryMapper;
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
 * Created by lukaszozimek on 04/09/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibFileLibraryMapperTest {

    @Autowired
    private LibFileLibraryMapper libLibraryMapper;

    private LibFileLibrary libLibrary;

    private LibFileLibraryDTO libLibraryDTO;

    private List<LibFileLibraryDTO> libLibraryDTOS = new ArrayList<>();

    private List<LibFileLibrary> libLibraries = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libLibrary.setId(1L);
        libLibraries.add(libLibrary);
        libLibraryDTO = factory.manufacturePojo(LibFileLibraryDTO.class);
        libLibraryDTOS.add(libLibraryDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }

    @Test
    public void DB2DTO() throws Exception {
        LibFileLibraryDTO dto = libLibraryMapper.DB2DTO(libLibrary);

        assertNotNull(dto.getId());
        assertNotNull(dto.getPrefix());
        assertNotNull(dto.getName());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getCounter());
        assertNotNull(dto.getShortcut());


    }

    @Test
    public void DBs2DTOs() throws Exception {

        List<LibFileLibraryDTO> dtos = libLibraryMapper.DBs2DTOs(libLibraries);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getPrefix());
            assertNotNull(dto.getName());
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getCounter());
            assertNotNull(dto.getShortcut());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        LibFileLibrary entity = libLibraryMapper.DTO2DB(libLibraryDTO, corNetwork);

        assertNotNull(entity.getId());
        assertNotNull(entity.getPrefix());
        assertNotNull(entity.getShortcut());
        assertNotNull(entity.getName());
        assertNotNull(entity.getCounter());
        assertNotNull(entity.getDescription());

        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<LibFileLibrary> entities = libLibraryMapper.DTOs2DBs(libLibraryDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getPrefix());
            assertNotNull(entity.getShortcut());
            assertNotNull(entity.getName());
            assertNotNull(entity.getCounter());
            assertNotNull(entity.getDescription());

            assertNotNull(entity.getNetwork());

        });
    }
}