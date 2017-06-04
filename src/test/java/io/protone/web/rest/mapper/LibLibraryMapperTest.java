package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.library.LibLibraryDTO;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLibrary;
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
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibLibraryMapperTest {
    @Autowired
    private LibLibraryMapper libLibraryMapper;

    private LibLibrary libLibrary;

    private LibLibraryDTO libLibraryDTO;

    private List<LibLibraryDTO> libLibraryDTOS = new ArrayList<>();

    private List<LibLibrary> libLibraries = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setId(1L);
        libLibraries.add(libLibrary);
        libLibraryDTO = factory.manufacturePojo(LibLibraryDTO.class);
        libLibraryDTOS.add(libLibraryDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }

    @Test
    public void DB2DTO() throws Exception {
        LibLibraryDTO dto = libLibraryMapper.DB2DTO(libLibrary);

        assertNotNull(dto.getId());
        assertNotNull(dto.getPrefix());
        assertNotNull(dto.getName());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getCounter());
        assertNotNull(dto.getShortcut());


    }

    @Test
    public void DBs2DTOs() throws Exception {

        List<LibLibraryDTO> dtos = libLibraryMapper.DBs2DTOs(libLibraries);

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
        LibLibrary entity = libLibraryMapper.DTO2DB(libLibraryDTO, corNetwork);


        LibLibrary libLibrary = new LibLibrary();

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
        List<LibLibrary> entities = libLibraryMapper.DTOs2DBs(libLibraryDTOS, corNetwork);

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
