package io.protone.custom.service.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.ProtoneApp;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLibrary;
import io.protone.domain.enumeration.LibCounterTypeEnum;
import io.protone.domain.enumeration.LibObjectTypeEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
        mockCorNetwork = new CorNetwork();
        mockLibLibrary = new LibLibrary();
        mockLibLibrary.setId((long) 1);
        mockLibLibrary.name("test")
            .prefix("test")
            .idxLength(1)
            .shortcut("test")
            .counter((long) 1)
            .counterType(LibCounterTypeEnum.CT_COUNTER)
            .libraryType(LibObjectTypeEnum.OT_AUDIO)
            .description("test")
            .network(mockCorNetwork);
        mockLibraryPT = new LibraryPT().id((long) 1).name("test");
        mockLibraryPT.setCounter((long) 1);
        mockLibraryPT.setCounterType("CT_COUNTER");
        mockLibraryPT.setLibraryType("OT_AUDIO");
        mockLibraryPT.setIndexLength(1);
        mockLibraryPT.setShortcut("test");
        mockLibraryPT.setPrefix("test");
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
