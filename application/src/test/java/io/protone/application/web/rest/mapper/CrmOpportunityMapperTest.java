package io.protone.application.web.rest.mapper;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorUser;
import io.protone.crm.api.dto.CrmOpportunityDTO;
import io.protone.crm.api.dto.thin.CrmOpportunityThinDTO;
import io.protone.crm.domain.*;
import io.protone.crm.mapper.CrmOpportunityMapper;
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

    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(1L);
        crmOpportunity.setAccount(factory.manufacturePojo(CrmAccount.class));
        crmOpportunity.getAccount().setShortName("test");
        crmOpportunity.setStage(factory.manufacturePojo(CorDictionary.class));
        crmOpportunity.getStage().setId(1L);
        crmOpportunity.setKeeper(factory.manufacturePojo(CorUser.class));
        crmOpportunity.getKeeper().setId(1L);
        crmOpportunity.setContact(factory.manufacturePojo(CrmContact.class));
        crmOpportunity.getContact().setShortName("test");
        crmOpportunity.setLead(factory.manufacturePojo(CrmLead.class));
        crmOpportunity.getLead().setShortname("test");
        crmOpportunity.setTasks(Sets.newHashSet(factory.manufacturePojo(CrmTask.class)));
        crmOpportunity.setCreatedBy(factory.manufacturePojo(CorUser.class));
        crmOpportunity.setLastModifiedBy(factory.manufacturePojo(CorUser.class));
        crmOpportunities.add(crmOpportunity);
        crmOpportunityDTO = factory.manufacturePojo(CrmOpportunityDTO.class);
        crmOpportunityDTOS.add(crmOpportunityDTO);
        corChannel = factory.manufacturePojo(CorChannel.class);


    }

    @Test
    public void DB2DTO() throws Exception {
        CrmOpportunityDTO dto = customCrmOpportunityMapper.DB2DTO(crmOpportunity);

        assertNotNull(dto.getId());
        assertNotNull(dto.getShortName());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getStage());
        assertNotNull(dto.getAccountId());
        assertNotNull(dto.getLeadId());
        assertNotNull(dto.getOpportunityOwner());
        assertNotNull(dto.getContactId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getLastTry());
        assertNotNull(dto.getCloseDate());
        assertNotNull(dto.getTasks());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getCreatedDate());
        assertNotNull(dto.getLastModifiedBy());
        assertNotNull(dto.getLastModifiedDate());
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
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getAccountId());
            assertNotNull(dto.getLeadId());
            assertNotNull(dto.getOpportunityOwner());
            assertNotNull(dto.getContactId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getLastTry());
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getCloseDate());
            assertNotNull(dto.getTasks());
            assertNotNull(dto.getCreatedBy());
            assertNotNull(dto.getCreatedDate());
            assertNotNull(dto.getLastModifiedBy());
            assertNotNull(dto.getLastModifiedDate());
        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmOpportunity> entities = customCrmOpportunityMapper.DTOs2DBs(crmOpportunityDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getId());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getShortName());
            assertNotNull(entity.getStage());
            assertNotNull(entity.getKeeper());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getName());
            assertNotNull(entity.getLastTry());
            assertNotNull(entity.getCloseDate());
            assertNotNull(entity.getTasks());
            assertNotNull(entity.getChannel());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CrmOpportunity entity = customCrmOpportunityMapper.DTO2DB(crmOpportunityDTO, corChannel);


        assertNotNull(entity.getId());
        assertNotNull(entity.getShortName());
        assertNotNull(entity.getDescription());
        assertNotNull(entity.getStage());
        assertNotNull(entity.getKeeper());
        assertNotNull(entity.getDescription());
        assertNotNull(entity.getName());
        assertNotNull(entity.getLastTry());
        assertNotNull(entity.getCloseDate());
        assertNotNull(entity.getTasks());
        assertNotNull(entity.getChannel());
    }

    @Test
    public void DB2ThinDTO() throws Exception {
        CrmOpportunityThinDTO dto = customCrmOpportunityMapper.DB2ThinDTO(crmOpportunity);

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
        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getCreatedDate());
        assertNotNull(dto.getLastModifiedBy());
        assertNotNull(dto.getLastModifiedDate());
    }

    @Test
    public void DBs2ThinDTOs() throws Exception {
        List<CrmOpportunityThinDTO> dtos = customCrmOpportunityMapper.DBs2ThinDTOs(crmOpportunities);

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
            assertNotNull(dto.getCreatedBy());
            assertNotNull(dto.getCreatedDate());
            assertNotNull(dto.getLastModifiedBy());
            assertNotNull(dto.getLastModifiedDate());
        });
    }

}
