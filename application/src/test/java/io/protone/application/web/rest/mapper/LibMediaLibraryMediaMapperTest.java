package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.LibMediaLibraryDTO;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.mapper.LibLibraryMediaMapper;
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
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibMediaLibraryMediaMapperTest {
    @Autowired
    private LibLibraryMediaMapper libLibraryMediaMapper;

    private LibMediaLibrary libMediaLibrary;

    private LibMediaLibraryDTO libMediaLibraryDTO;

    private List<LibMediaLibraryDTO> libMediaLibraryDTOS = new ArrayList<>();

    private List<LibMediaLibrary> libLibraries = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libMediaLibrary = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary.setId(1L);
        libLibraries.add(libMediaLibrary);
        libMediaLibraryDTO = factory.manufacturePojo(LibMediaLibraryDTO.class);
        libMediaLibraryDTOS.add(libMediaLibraryDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }

    @Test
    public void DB2DTO() throws Exception {
        LibMediaLibraryDTO dto = libLibraryMediaMapper.DB2DTO(libMediaLibrary);

        assertNotNull(dto.getId());
        assertNotNull(dto.getPrefix());
        assertNotNull(dto.getName());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getCounter());
        assertNotNull(dto.getShortcut());


    }

    @Test
    public void DBs2DTOs() throws Exception {

        List<LibMediaLibraryDTO> dtos = libLibraryMediaMapper.DBs2DTOs(libLibraries);

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
        LibMediaLibrary entity = libLibraryMediaMapper.DTO2DB(libMediaLibraryDTO, corNetwork);


        LibMediaLibrary libMediaLibrary = new LibMediaLibrary();

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
        List<LibMediaLibrary> entities = libLibraryMediaMapper.DTOs2DBs(libMediaLibraryDTOS, corNetwork);

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
