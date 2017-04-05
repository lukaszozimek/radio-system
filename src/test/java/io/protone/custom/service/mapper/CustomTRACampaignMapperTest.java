package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProtoneApp.class)
public class CustomTRACampaignMapperTest {
    @Autowired
    private CustomTRACampaignMapper customTRACampaignMapper;
    private CorNetwork mockCorNetwork = null;
    private TraCampaign mockTraCampaing = null;
    private TraCampaignPT mockTraCampaignPT = null;

    //@Before
    public void initialize() {
        mockCorNetwork = new CorNetwork();
        mockTraCampaignPT = new TraCampaignPT();
        mockTraCampaignPT.setId((long) 1);
        mockTraCampaignPT.setName("test");
        mockTraCampaignPT.setCustomerId(new TraCustomerPT());
        mockTraCampaignPT.setPrize((long) 122412);
        mockTraCampaignPT.setOrders(new ArrayList<>());
        mockTraCampaignPT.setStartDate(LocalDate.now());
        mockTraCampaignPT.setEndDate(LocalDate.now());
        mockTraCampaignPT.setStatus(new TraCampaingStatus());
        mockTraCampaignPT.setOrders(new ArrayList<>());

        mockTraCampaing = new TraCampaign();
        mockTraCampaing.setId((long) 1);
        mockTraCampaing.setName("test");
        mockTraCampaing.setCustomer(new CrmAccount());
        mockTraCampaing.setPrize((long) 122412);
        mockTraCampaing.setStartDate(LocalDate.now());
        mockTraCampaing.setEndDate(LocalDate.now());
        mockTraCampaing.setStatus(new TraCampaingStatus());
        mockTraCampaing.setOrders(new HashSet<>());

    }

  //  @Test
    public void transfromDTOToEntity() throws Exception {
        // TODO: changes TRAPRice to long
        //then
        TraCampaign result = customTRACampaignMapper.transfromDTOToEntity(mockTraCampaignPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

//    @Test
    public void transfromEntitytoDTO() throws Exception {
        //then
        TraCampaignPT result = customTRACampaignMapper.transfromEntitytoDTO(mockTraCampaing);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
