package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.domain.CorPropertyKey;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CustomLibLibraryMapperExtTest {
    @Autowired
    private CustomLibLibraryMapperExt customLibLibraryMapperExt;

    private LibLibrary libLibrary;

    private LibraryPT libraryPT;

    private List<LibraryPT> libraryPTS = new ArrayList<>();

    private List<LibLibrary> libLibraries = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibraries.add(libLibrary);
        libraryPT = factory.manufacturePojo(LibraryPT.class);
        libraryPTS.add(libraryPT);

    }

    @Test
    public void DB2DTO() throws Exception {
        LibraryPT dto = customLibLibraryMapperExt.DB2DTO(libLibrary);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());

    }

    @Test
    public void DBs2DTOs() throws Exception {
    }

    @Test
    public void DTO2DB() throws Exception {
        LibLibrary entity = customLibLibraryMapperExt.DTO2DB(libraryPT);

        assertNotNull(entity.getId());

        assertNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
    }

}
