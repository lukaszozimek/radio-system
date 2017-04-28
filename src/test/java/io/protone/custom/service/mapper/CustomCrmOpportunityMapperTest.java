package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmOpportunityPT;
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
public class CustomCrmOpportunityMapperTest {
    @Autowired
    private CustomCrmOpportunityMapper customCrmOpportunityMapper;

    private CrmOpportunity crmOpportunity;

    private CrmOpportunityPT crmOpportunityPT;

    private List<CrmOpportunityPT> crmOpportunityPTS = new ArrayList<>();

    private List<CrmOpportunity> crmOpportunities = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunities.add(crmOpportunity);
        crmOpportunityPT = factory.manufacturePojo(CrmOpportunityPT.class);
        crmOpportunityPTS.add(crmOpportunityPT);

    }

    @Test
    public void DB2DTO() throws Exception {
        CrmOpportunityPT dto = customCrmOpportunityMapper.DB2DTO(crmOpportunity);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CrmOpportunityPT> dtos = customCrmOpportunityMapper.DBs2DTOs(crmOpportunities);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getName());

        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmOpportunity> entities = customCrmOpportunityMapper.DTOs2DBs(crmOpportunityPTS);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());

            assertNull(entity.getNetwork());


        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CrmOpportunity entity = customCrmOpportunityMapper.DTO2DB(crmOpportunityPT);


        assertNotNull(entity.getId());
        assertNotNull(entity.getName());

        assertNull(entity.getNetwork());
    }

    @Test
    public void crmContactFromId() throws Exception {
    }

    @Test
    public void crmAccountFromId() throws Exception {
    }

    @Test
    public void crmLeadFromId() throws Exception {
    }

}
