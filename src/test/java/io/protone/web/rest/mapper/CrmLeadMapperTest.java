package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.cor.thin.CoreUserThinDTO;
import io.protone.web.rest.dto.crm.CrmLeadDTO;
import org.assertj.core.util.Sets;
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
public class CrmLeadMapperTest {

    @Autowired
    private CrmLeadMapper customCrmLeadMapper;

    private CrmLead crmLead;

    private CrmLeadDTO crmLeadDTO;

    private List<CrmLeadDTO> crmLeadDTOS = new ArrayList<>();

    private List<CrmLead> crmLeads = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(1L);
        crmLead.setArea(factory.manufacturePojo(CorDictionary.class));
        crmLead.setLeadSource(factory.manufacturePojo(CorDictionary.class));
        crmLead.setLeadStatus(factory.manufacturePojo(CorDictionary.class));
        crmLead.setPerson(factory.manufacturePojo(CorPerson.class));
        crmLead.setAddres(factory.manufacturePojo(CorAddress.class));
        crmLead.setIndustry(factory.manufacturePojo(CorDictionary.class));
        crmLead.setKeeper(factory.manufacturePojo(CorUser.class));
        crmLead.setShortname("fefe");
        crmLead.setName("fwafwafwa");
        crmLead.setTasks(Sets.newLinkedHashSet(factory.manufacturePojo(CrmTask.class)));
        crmLead.setNetwork(factory.manufacturePojo(CorNetwork.class));
        crmLead.setAddres(factory.manufacturePojo(CorAddress.class));
        crmLeads.add(crmLead);
        crmLeadDTO = factory.manufacturePojo(CrmLeadDTO.class);
        crmLeadDTO.setOwner(factory.manufacturePojo(CoreUserThinDTO.class));
        crmLeadDTOS.add(crmLeadDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }

    @Test
    public void DB2DTO() throws Exception {
        CrmLeadDTO dto = customCrmLeadMapper.DB2DTO(crmLead);

        assertNotNull(dto.getArea());
        assertNotNull(dto.getStatus());
        assertNotNull(dto.getSource());
        assertNotNull(dto.getPerson());
        assertNotNull(dto.getOwner());
        assertNotNull(dto.getAddres());
        assertNotNull(dto.getIndustry());
        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getShortname());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getTasks());
        assertNotNull(dto.getName());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CrmLeadDTO> dtos = customCrmLeadMapper.DBs2DTOs(crmLeads);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getArea());
            assertNotNull(dto.getStatus());
            assertNotNull(dto.getSource());
            assertNotNull(dto.getPerson());
            assertNotNull(dto.getOwner());
            assertNotNull(dto.getAddres());
            assertNotNull(dto.getIndustry());
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getShortname());
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getTasks());
            assertNotNull(dto.getName());

        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmLead> entities = customCrmLeadMapper.DTOs2DBs(crmLeadDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getId());
            assertNotNull(entity.getKeeper());
            assertNotNull(entity.getArea());
            assertNotNull(entity.getPerson());
            assertNotNull(entity.getAddres());
            assertNotNull(entity.getIndustry());
            assertNotNull(entity.getLeadSource());
            assertNotNull(entity.getLeadStatus());
            assertNotNull(entity.getShortname());
            assertNotNull(entity.getName());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getTasks());
            assertNotNull(entity.getNetwork());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CrmLead entity = customCrmLeadMapper.DTO2DB(crmLeadDTO, corNetwork);

        assertNotNull(entity.getId());
        assertNotNull(entity.getKeeper());
        assertNotNull(entity.getArea());
        assertNotNull(entity.getPerson());
        assertNotNull(entity.getAddres());
        assertNotNull(entity.getIndustry());
        assertNotNull(entity.getLeadSource());
        assertNotNull(entity.getLeadStatus());
        assertNotNull(entity.getShortname());
        assertNotNull(entity.getName());
        assertNotNull(entity.getDescription());
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
