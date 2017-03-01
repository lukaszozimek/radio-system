package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraCampaign;
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
public class CustomTRACampaignMapperTest {
    @Autowired
    private CustomTRACampaignMapper customTRACampaignMapper;
    private CorNetwork mockCorNetwork = null;
    private TraCampaign mockTraCampaing = null;
    private TraCampaignPT mockTraCampaignPT = null;

    @Before
    public void initialize() {

    }

    @Test
    public void transfromDTOToEntity() throws Exception {
        //then
        TraCampaign result = customTRACampaignMapper.transfromDTOToEntity(mockTraCampaignPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void transfromEntitytoDTO() throws Exception {
        //then
        TraCampaignPT result = customTRACampaignMapper.transfromEntitytoDTO(mockTraCampaing);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
