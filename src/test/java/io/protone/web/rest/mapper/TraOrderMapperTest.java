package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.*;
import io.protone.web.rest.dto.thin.TraAdvertisementThinDTO;
import io.protone.web.rest.dto.thin.TraCustomerThinDTO;
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
 * Created by lukaszozimek on 28.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraOrderMapperTest {
    @Autowired
    private TraOrderMapper customTraOrderMapper;

    private TraOrder traOrder;

    private TraOrderPT traOrderPT;

    private List<TraOrderPT> orderPTS = new ArrayList<>();

    private List<TraOrder> traOrders = new ArrayList<>();

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void initPojos() {
        factory = new PodamFactoryImpl();
        traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setId(1L);
        traOrder.setAdvertisment(factory.manufacturePojo(TraAdvertisement.class));
        traOrder.getAdvertisment().setId(1L);
        traOrder.setStatus(factory.manufacturePojo(CorDictionary.class));
        traOrder.getStatus().setId(1L);
        traOrder.setCampaign(factory.manufacturePojo(TraCampaign.class));
        traOrder.getCampaign().setId(1L);
        traOrder.setCustomer(factory.manufacturePojo(CrmAccount.class));
        traOrder.getCustomer().setId(1L);
        traOrder.setInvoice(factory.manufacturePojo(TraInvoice.class));
        traOrder.getInvoice().setId(1L);
        traOrders.add(traOrder);
        traOrderPT = factory.manufacturePojo(TraOrderPT.class);
        traOrderPT.getId();
        traOrderPT.setAdvertismentId(factory.manufacturePojo(TraAdvertisementThinDTO.class));
        traOrderPT.setCampaignId(1L);
        traOrderPT.setCustomerId(factory.manufacturePojo(TraCustomerThinDTO.class));
        traOrderPT.setStatusId(factory.manufacturePojo(CorDictionaryDTO.class));
        traOrderPT.setInvoiceId(1L);
        orderPTS.add(traOrderPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }

    @Test
    public void DB2DTO() throws Exception {
        TraOrderPT dto = customTraOrderMapper.DB2DTO(traOrder);

        assertNotNull(dto.getId());
        assertNotNull(dto.getAdvertismentId());
        assertNotNull(dto.getCampaignId());
        assertNotNull(dto.getCustomerId());
        assertNotNull(dto.getStatusId());
        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getStartDate());
        assertNotNull(dto.getEndDate());
        assertNotNull(dto.getCalculatedPrize());


    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraOrderPT> dtos = customTraOrderMapper.DBs2DTOs(traOrders);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getAdvertismentId());
            assertNotNull(dto.getCampaignId());
            assertNotNull(dto.getCustomerId());
            assertNotNull(dto.getStatusId());
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getStartDate());
            assertNotNull(dto.getEndDate());
            assertNotNull(dto.getCalculatedPrize());
        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraOrder> entities = customTraOrderMapper.DTOs2DBs(orderPTS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getAdvertisment());
            assertNotNull(entity.getStatus());
            assertNotNull(entity.getCampaign());
            assertNotNull(entity.getCustomer());
            assertNotNull(entity.getInvoice());
            assertNotNull(entity.getCalculatedPrize());
            assertNotNull(entity.getEndDate());
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getStartDate());

            assertNotNull(entity.getNetwork());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        TraOrder entity = customTraOrderMapper.DTO2DB(traOrderPT, corNetwork);

        assertNotNull(entity.getAdvertisment());
        assertNotNull(entity.getStatus());
        assertNotNull(entity.getCampaign());
        assertNotNull(entity.getCustomer());
        assertNotNull(entity.getInvoice());
        assertNotNull(entity.getCalculatedPrize());
        assertNotNull(entity.getEndDate());
        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getStartDate());

        assertNotNull(entity.getNetwork());


    }


}
