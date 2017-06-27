package io.protone.web.rest.mapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.protone.ProtoneApp;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.traffic.TraCampaignDTO;
import io.protone.web.rest.dto.traffic.TraOrderDTO;
import io.protone.web.rest.dto.traffic.thin.TraCustomerThinDTO;
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
public class TraCampaignMapperTest {
    @Autowired
    private TraCampaignMapper traCampaignMapper;

    private TraCampaign traCampaign;

    private TraCampaignDTO campaignPT;

    private List<TraCampaignDTO> traCampaignDTOS = new ArrayList<>();

    private List<TraCampaign> traCampaigns = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traCampaign = factory.manufacturePojo(TraCampaign.class);
        traCampaign.shortName("xxx");
        traCampaign.setId(1L);
        traCampaign.setCustomer(factory.manufacturePojo(CrmAccount.class));
        traCampaign.getCustomer().setId(1L);
        traCampaign.setOrders(Sets.newHashSet(factory.manufacturePojo(TraOrder.class)));
        traCampaign.setStatus(factory.manufacturePojo(CorDictionary.class));
        traCampaigns.add(traCampaign);
        campaignPT = factory.manufacturePojo(TraCampaignDTO.class);
        campaignPT.shortName("xxx");
        campaignPT.setCustomerId(factory.manufacturePojo(TraCustomerThinDTO.class));
        campaignPT.setOrders(Lists.newArrayList(factory.manufacturePojo(TraOrderDTO.class)));
        campaignPT.setStatus(factory.manufacturePojo(CorDictionaryDTO.class));
        traCampaignDTOS.add(campaignPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }

    @Test
    public void DB2DTO() throws Exception {
        TraCampaignDTO dto = traCampaignMapper.DB2DTO(traCampaign);

        assertNotNull(dto.getOrders());
        assertNotNull(dto.getShortName());
        assertNotNull(dto.getCustomerId());
        assertNotNull(dto.getStatus());
        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getStartDate());
        assertNotNull(dto.getEndDate());
        assertNotNull(dto.getPrize());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraCampaignDTO> dtos = traCampaignMapper.DBs2DTOs(traCampaigns);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getShortName());
            assertNotNull(dto.getOrders());
            assertNotNull(dto.getCustomerId());
            assertNotNull(dto.getStatus());
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getStartDate());
            assertNotNull(dto.getEndDate());
            assertNotNull(dto.getPrize());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        TraCampaign entity = traCampaignMapper.DTO2DB(campaignPT, corNetwork);


        assertNotNull(entity.getCustomer());
        assertNotNull(entity.getShortName());
        assertNotNull(entity.getOrders());
        assertNotNull(entity.getStatus());
        assertNotNull(entity.getEndDate());
        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getPrize());
        assertNotNull(entity.getStartDate());

        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraCampaign> entities = traCampaignMapper.DTOs2DBs(traCampaignDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getCustomer());
            assertNotNull(entity.getShortName());
            assertNotNull(entity.getOrders());
            assertNotNull(entity.getStatus());
            assertNotNull(entity.getEndDate());
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getPrize());
            assertNotNull(entity.getStartDate());

            assertNotNull(entity.getNetwork());
        });

    }


}
