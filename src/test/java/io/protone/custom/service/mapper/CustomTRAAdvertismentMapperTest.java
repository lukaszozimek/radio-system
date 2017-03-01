package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraAdvertisement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CustomTRAAdvertismentMapperTest {
    @Autowired
    private CustomTRAAdvertismentMapper customTRAAdvertismentMapper;
    private CorNetwork mockCorNetwork = null;
    private TraAdvertisement mockTraAdvertisment = null;
    private TraAdvertisementPT mockTraAdvertismentsPT = null;

    @Before
    public void initialize() {

    }

    @Test
    public void transformDTOToEntity() throws Exception {
        //then
        TraAdvertisement result = customTRAAdvertismentMapper.transformDTOToEntity(mockTraAdvertismentsPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void transformEntityToDTO() throws Exception {
        //then
        TraAdvertisementPT result = customTRAAdvertismentMapper.transformEntityToDTO(mockTraAdvertisment);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
