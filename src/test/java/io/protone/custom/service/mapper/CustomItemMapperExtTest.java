package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.LibMediaItemPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibMediaItem;
import io.protone.domain.enumeration.LibItemTypeEnum;
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
