package io.protone.custom.service.mapper;

import com.google.common.collect.Lists;
import io.protone.ProtoneApp;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProtoneApp.class)
public class CustomTraOrderMapperTest {
    @Autowired
    private CustomTraOrderMapper customTraOrderMapper;
    private CorNetwork mockCorNetwork = null;
    private TraOrder mockTraOrder = null;
    private TraOrderPT mockTraOrderPT = null;
    private Set<TraOrder> mockListTraOrder = null;
    private List<TraOrderPT> mockListTraOrderPT = null;

   // @Before
    public void initialize() {
        mockCorNetwork = new CorNetwork();

        mockTraOrderPT = new TraOrderPT();
        mockTraOrderPT.setId((long) 1);
        mockTraOrderPT.name("test");
        mockTraOrderPT.calculatedPrize((long) 212);
        mockTraOrderPT.traCampaign(new TraCampaignPT());
        mockTraOrderPT.customerId(new TraCustomerPT());
        mockTraOrderPT.endDate(LocalDate.now());
        mockTraOrderPT.startDate(LocalDate.now());
        mockTraOrderPT.traPrice(new TraPrice());

        mockTraOrder = new TraOrder();
        mockTraOrder.setId((long) 1);
        mockTraOrder.name("test")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now())
            .calculatedPrize((long) 12)
            .customer(new CrmAccount())
            .campaign(new TraCampaign())
            .price(new TraPrice())
            .network(new CorNetwork())
            .status(new TraOrderStatus())
            .invoice(new TraInvoice());

    }

   // @Test
    public void trasnformDTOtoEntity() throws Exception {
        //then
        TraOrder result = customTraOrderMapper.trasnformDTOtoEntity(mockTraOrderPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

 //   @Test
    public void trasnformDTOtoEntity1() throws Exception {
        ///when
        mockListTraOrderPT = Lists.newArrayList(mockTraOrderPT);
        //then
        List<TraOrder> result = customTraOrderMapper.trasnformDTOtoEntity(mockListTraOrderPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

  //  @Test
    public void transfromEntitesToDTO() throws Exception {
        //when
        mockListTraOrder = Sets.newSet(mockTraOrder);
        //then
        List<TraOrderPT> result = customTraOrderMapper.transfromEntitesToDTO(mockListTraOrder);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

//    @Test
    public void transfromEntiteToDTO() throws Exception {
        //then
        TraOrderPT result = customTraOrderMapper.transfromEntiteToDTO(mockTraOrder);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
