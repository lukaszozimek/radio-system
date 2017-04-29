package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.domain.TraCampaign;
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
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 28.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraCampaignMapperTest {
    @Autowired
    private TraCampaignMapper customLibMarkerMapperExt;

    private TraCampaign traCampaign;

    private TraCampaignPT campaignPT;

    private List<TraCampaignPT> traCampaignPTS = new ArrayList<>();

    private List<TraCampaign> traCampaigns = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traCampaign = factory.manufacturePojo(TraCampaign.class);
        traCampaigns.add(traCampaign);
        campaignPT = factory.manufacturePojo(TraCampaignPT.class);
        traCampaignPTS.add(campaignPT);

    }

    @Test
    public void DB2DTO() throws Exception {
        TraCampaignPT dto = customLibMarkerMapperExt.DB2DTO(traCampaign);

        assertNotNull(dto.getOrders());
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
        List<TraCampaignPT> dtos = customLibMarkerMapperExt.DBs2DTOs(traCampaigns);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

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
        TraCampaign entity = customLibMarkerMapperExt.DTO2DB(campaignPT);


        assertNotNull(entity.getCustomer());
        assertNotNull(entity.getOrders());
        assertNotNull(entity.getStatus());
        assertNotNull(entity.getEndDate());
        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getPrize());
        assertNotNull(entity.getStartDate());

        assertNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraCampaign> entities = customLibMarkerMapperExt.DTOs2DBs(traCampaignPTS);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getCustomer());
            assertNotNull(entity.getOrders());
            assertNotNull(entity.getStatus());
            assertNotNull(entity.getEndDate());
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getPrize());
            assertNotNull(entity.getStartDate());

            assertNull(entity.getNetwork());
        });

    }


}
