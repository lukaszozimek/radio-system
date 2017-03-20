package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        mockTraCampaignPT.setEmission(new ArrayList<>());
        mockTraCampaignPT.setStartDate(LocalDate.now());
        mockTraCampaignPT.setEndDate(LocalDate.now());
        mockTraCampaignPT.setStatus(new TraCampaingStatus());
        mockTraCampaignPT.setOrderPT(new TraOrderPT());

        mockTraCampaing = new TraCampaign();
        mockTraCampaing.setId((long) 1);
        mockTraCampaing.setName("test");
        mockTraCampaing.setCustomer(new CrmAccount());
        mockTraCampaing.setPrize((long) 122412);
        mockTraCampaing.setEmissions(new HashSet<>());
        mockTraCampaing.setStartDate(LocalDate.now());
        mockTraCampaing.setEndDate(LocalDate.now());
        mockTraCampaing.setStatus(new TraCampaingStatus());
        mockTraCampaing.setOrders(new TraOrder());

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
