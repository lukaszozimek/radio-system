package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmOpportunity;
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
public class CrmOpportunityMapperTest {
    @Autowired
    private CrmOpportunityMapper customCrmOpportunityMapper;

    private CrmOpportunity crmOpportunity;

    private CrmOpportunityPT crmOpportunityPT;

    private List<CrmOpportunityPT> crmOpportunityPTS = new ArrayList<>();

    private List<CrmOpportunity> crmOpportunities = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunities.add(crmOpportunity);
        crmOpportunityPT = factory.manufacturePojo(CrmOpportunityPT.class);
        crmOpportunityPTS.add(crmOpportunityPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);


    }

    @Test
    public void DB2DTO() throws Exception {
        CrmOpportunityPT dto = customCrmOpportunityMapper.DB2DTO(crmOpportunity);

        assertNotNull(dto.getId());
        assertNotNull(dto.getStage());
        assertNotNull(dto.getAccountId());
        assertNotNull(dto.getLeadId());
        assertNotNull(dto.getOpportunityOwner());
        assertNotNull(dto.getContactId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getLastTry());
        assertNotNull(dto.getCloseDate());
        assertNotNull(dto.getTasks());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CrmOpportunityPT> dtos = customCrmOpportunityMapper.DBs2DTOs(crmOpportunities);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getStage());
            assertNotNull(dto.getAccountId());
            assertNotNull(dto.getLeadId());
            assertNotNull(dto.getOpportunityOwner());
            assertNotNull(dto.getContactId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getLastTry());
            assertNotNull(dto.getCloseDate());
            assertNotNull(dto.getTasks());
        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmOpportunity> entities = customCrmOpportunityMapper.DTOs2DBs(crmOpportunityPTS,corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getId());
            assertNotNull(entity.getAccount());
            assertNotNull(entity.getStage());
            assertNotNull(entity.getKeeper());
            assertNotNull(entity.getContact());
            assertNotNull(entity.getLead());
            assertNotNull(entity.getName());
            assertNotNull(entity.getLastTry());
            assertNotNull(entity.getCloseDate());
            assertNotNull(entity.getTasks());

            assertNull(entity.getNetwork());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CrmOpportunity entity = customCrmOpportunityMapper.DTO2DB(crmOpportunityPT,corNetwork);


        assertNotNull(entity.getId());
        assertNotNull(entity.getAccount());
        assertNotNull(entity.getStage());
        assertNotNull(entity.getKeeper());
        assertNotNull(entity.getContact());
        assertNotNull(entity.getLead());
        assertNotNull(entity.getName());
        assertNotNull(entity.getLastTry());
        assertNotNull(entity.getCloseDate());
        assertNotNull(entity.getTasks());

        assertNull(entity.getNetwork());
    }


}
