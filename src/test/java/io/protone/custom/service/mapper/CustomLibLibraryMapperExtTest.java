package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLibrary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CustomLibLibraryMapperExtTest {
    @Autowired
    private CustomLibLibraryMapperExt libLibraryMapperExt;
    private CorNetwork mockCorNetwork = null;
    private LibLibrary mockLibLibrary = null;
    private LibraryPT mockLibraryPT = null;

    private List<LibLibrary> mockListLibLibrary = null;
    private List<LibraryPT> mockListLibraryPT = null;

    @Before
    public void initialize() {

    }

    @Test
    public void DB2DTO() throws Exception {
        //then
        LibraryPT result = libLibraryMapperExt.DB2DTO(mockLibLibrary);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void DBs2DTOs() throws Exception {
        //then
        List<LibraryPT> result = libLibraryMapperExt.DBs2DTOs(mockListLibLibrary);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void DTO2DB() throws Exception {
        //then
        LibLibrary result = libLibraryMapperExt.DTO2DB(mockLibraryPT);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void DTOs2DBs() throws Exception {
        //then
        List<LibLibrary> result = libLibraryMapperExt.DTOs2DBs(mockListLibraryPT);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
