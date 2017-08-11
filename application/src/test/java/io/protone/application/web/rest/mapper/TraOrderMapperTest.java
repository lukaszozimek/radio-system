package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.crm.domain.CrmAccount;
import io.protone.traffic.api.dto.TraOrderDTO;
import io.protone.traffic.api.dto.thin.TraAdvertisementThinDTO;
import io.protone.traffic.api.dto.thin.TraCustomerThinDTO;
import io.protone.traffic.api.dto.thin.TraOrderThinDTO;
import io.protone.traffic.domain.TraAdvertisement;
import io.protone.traffic.domain.TraCampaign;
import io.protone.traffic.domain.TraInvoice;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.mapper.TraOrderMapper;
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

    private TraOrderDTO traOrderDTO;

    private TraOrderThinDTO traOrderThinDTO;

    private List<TraOrderDTO> orderPTS = new ArrayList<>();

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
        traOrder.setCreatedBy(factory.manufacturePojo(CorUser.class));
        traOrder.setLastModifiedBy(factory.manufacturePojo(CorUser.class));
        traOrders.add(traOrder);
        traOrderThinDTO = factory.manufacturePojo(TraOrderThinDTO.class);
        traOrderDTO = factory.manufacturePojo(TraOrderDTO.class);
        traOrderDTO.getId();
        traOrderDTO.setAdvertismentId(factory.manufacturePojo(TraAdvertisementThinDTO.class));
        traOrderDTO.setCampaignId(1L);
        traOrderDTO.setCustomerId(factory.manufacturePojo(TraCustomerThinDTO.class));
        traOrderDTO.setStatusId(factory.manufacturePojo(CorDictionaryDTO.class));
        traOrderDTO.setInvoiceId(1L);
        orderPTS.add(traOrderDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }

    @Test
    public void DB2DTO() throws Exception {
        TraOrderDTO dto = customTraOrderMapper.DB2DTO(traOrder);

        assertNotNull(dto.getId());
        assertNotNull(dto.getAdvertismentId());
        assertNotNull(dto.getCampaignId());
        assertNotNull(dto.getCustomerId());
        assertNotNull(dto.getStatusId());
        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getStartDate());
        assertNotNull(dto.getEndDate());
        assertNotNull(dto.getPrice());
        assertNotNull(dto.getCalculatedPrize());
        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getCreatedDate());
        assertNotNull(dto.getLastModifiedBy());
        assertNotNull(dto.getLastModifiedDate());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraOrderDTO> dtos = customTraOrderMapper.DBs2DTOs(traOrders);

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
            assertNotNull(dto.getPrice());
            assertNotNull(dto.getCalculatedPrize());
            assertNotNull(dto.getCreatedBy());
            assertNotNull(dto.getCreatedDate());
            assertNotNull(dto.getLastModifiedBy());
            assertNotNull(dto.getLastModifiedDate());
        });
    }

    @Test
    public void ThinDTO2DB() throws Exception {
        TraOrder entity = customTraOrderMapper.DTO2DB(traOrderThinDTO);


        assertNotNull(entity.getId());
        assertNotNull(entity.getAdvertisment());
        assertNotNull(entity.getCampaign());
        assertNotNull(entity.getCustomer());
        assertNotNull(entity.getStatus());
        assertNotNull(entity.getId());
        assertNotNull(entity.getPrice());
        assertNotNull(entity.getName());
        assertNotNull(entity.getStartDate());
        assertNotNull(entity.getEndDate());
        assertNotNull(entity.getCalculatedPrize());
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
            assertNotNull(entity.getPrice());
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
        TraOrder entity = customTraOrderMapper.DTO2DB(traOrderDTO, corNetwork);

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
