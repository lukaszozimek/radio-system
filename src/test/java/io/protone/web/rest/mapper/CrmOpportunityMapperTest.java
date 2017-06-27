package io.protone.web.rest.mapper;

import com.google.common.collect.Sets;
import io.protone.ProtoneApp;
import io.protone.web.rest.dto.crm.CrmOpportunityDTO;
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
public class CrmOpportunityMapperTest {


    @Autowired
    private CrmOpportunityMapper customCrmOpportunityMapper;

    private CrmOpportunity crmOpportunity;

    private CrmOpportunityDTO crmOpportunityDTO;

    private List<CrmOpportunityDTO> crmOpportunityDTOS = new ArrayList<>();

    private List<CrmOpportunity> crmOpportunities = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(1L);
        crmOpportunity.setAccount(factory.manufacturePojo(CrmAccount.class));
        crmOpportunity.getAccount().setId(1L);
        crmOpportunity.setStage(factory.manufacturePojo(CorDictionary.class));
        crmOpportunity.getStage().setId(1L);
        crmOpportunity.setKeeper(factory.manufacturePojo(CorUser.class));
        crmOpportunity.getKeeper().setId(1L);
        crmOpportunity.setContact(factory.manufacturePojo(CrmContact.class));
        crmOpportunity.getContact().setId(1L);
        crmOpportunity.setLead(factory.manufacturePojo(CrmLead.class));
        crmOpportunity.getLead().setId(1L);
        crmOpportunity.setTasks(Sets.newHashSet(factory.manufacturePojo(CrmTask.class)));
        crmOpportunities.add(crmOpportunity);
        crmOpportunityDTO = factory.manufacturePojo(CrmOpportunityDTO.class);
        crmOpportunityDTOS.add(crmOpportunityDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);


    }

    @Test
    public void DB2DTO() throws Exception {
        CrmOpportunityDTO dto = customCrmOpportunityMapper.DB2DTO(crmOpportunity);

        assertNotNull(dto.getId());
        assertNotNull(dto.getShortName());
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
        List<CrmOpportunityDTO> dtos = customCrmOpportunityMapper.DBs2DTOs(crmOpportunities);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getShortName());
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
        List<CrmOpportunity> entities = customCrmOpportunityMapper.DTOs2DBs(crmOpportunityDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getId());

            assertNotNull(entity.getShortName());
            assertNotNull(entity.getAccount());
            assertNotNull(entity.getStage());
            assertNotNull(entity.getKeeper());
            assertNotNull(entity.getContact());
            assertNotNull(entity.getLead());
            assertNotNull(entity.getName());
            assertNotNull(entity.getLastTry());
            assertNotNull(entity.getCloseDate());
            assertNotNull(entity.getTasks());
            assertNotNull(entity.getNetwork());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CrmOpportunity entity = customCrmOpportunityMapper.DTO2DB(crmOpportunityDTO, corNetwork);


        assertNotNull(entity.getId());
        assertNotNull(entity.getShortName());
        assertNotNull(entity.getAccount());
        assertNotNull(entity.getStage());
        assertNotNull(entity.getKeeper());
        assertNotNull(entity.getContact());
        assertNotNull(entity.getLead());
        assertNotNull(entity.getName());
        assertNotNull(entity.getLastTry());
        assertNotNull(entity.getCloseDate());
        assertNotNull(entity.getTasks());
        assertNotNull(entity.getNetwork());
    }
    @Test
    public void DB2ThinDTO() throws Exception {
    }

    @Test
    public void DBs2ThinDTOs() throws Exception {
    }

}
