package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorUser;
import io.protone.crm.api.dto.CrmTaskDTO;
import io.protone.crm.api.dto.thin.CrmTaskThinDTO;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.mapper.CrmTaskMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmTaskMapperTest {
    @Autowired
    private CrmTaskMapper customCrmTaskMapper;

    private CrmTask crmTask;

    private CrmTaskDTO crmTaskDTO;

    private List<CrmTaskDTO> crmTaskDTOS = new ArrayList<>();

    private Set<CrmTask> crmTasks = new HashSet<>();

    private CorChannel corChannel;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setCreatedBy(factory.manufacturePojo(CorUser.class));
        crmTask.setLastModifiedBy(factory.manufacturePojo(CorUser.class));
        crmTask.setAssignedTo(factory.manufacturePojo(CorUser.class));
        crmTask.setId(1L);
        crmTasks.add(crmTask);
        crmTaskDTO = factory.manufacturePojo(CrmTaskDTO.class);
        crmTaskDTOS.add(crmTaskDTO);
        corChannel = factory.manufacturePojo(CorChannel.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        CrmTaskDTO dto = customCrmTaskMapper.DB2DTO(crmTask);

        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getAssignedTo());
        assertNotNull(dto.getId());
        assertNotNull(dto.getSubject());
        assertNotNull(dto.getActivityDate());
        assertNotNull(dto.getComment());
        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getCreatedDate());
        assertNotNull(dto.getLastModifiedBy());
        assertNotNull(dto.getLastModifiedDate());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmTaskDTO> dtos = customCrmTaskMapper.DBs2DTOs(crmTasks);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getCreatedBy());
            assertNotNull(dto.getAssignedTo());
            assertNotNull(dto.getId());
            assertNotNull(dto.getSubject());
            assertNotNull(dto.getActivityDate());
            assertNotNull(dto.getComment());
            assertNotNull(dto.getCreatedBy());
            assertNotNull(dto.getCreatedDate());
            assertNotNull(dto.getLastModifiedBy());
            assertNotNull(dto.getLastModifiedDate());

        });
    }


    @Test
    public void DTO2DB() throws Exception {
        CrmTask entity = customCrmTaskMapper.DTO2DB(crmTaskDTO, corChannel);


        assertNotNull(entity.getCreatedBy());
        assertNotNull(entity.getAssignedTo());
        assertNotNull(entity.getId());
        assertNotNull(entity.getSubject());
        assertNotNull(entity.getActivityDate());
        assertNotNull(entity.getComment());
        assertNotNull(entity.getId());
        assertNotNull(entity.getChannel());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        Set<CrmTask> entities = customCrmTaskMapper.DTOs2DBs(crmTaskDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getCreatedBy());
            assertNotNull(entity.getAssignedTo());
            assertNotNull(entity.getId());
            assertNotNull(entity.getSubject());
            assertNotNull(entity.getActivityDate());
            assertNotNull(entity.getComment());
            assertNotNull(entity.getId());
            assertNotNull(entity.getChannel());

        });
    }

    @Test
    public void DB2ThinDTO() throws Exception {
        CrmTaskThinDTO dto = customCrmTaskMapper.DB2ThinDTO(crmTask);

        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getAssignedTo());
        assertNotNull(dto.getId());
        assertNotNull(dto.getSubject());
        assertNotNull(dto.getActivityDate());
        assertNotNull(dto.getComment());
        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getCreatedDate());
        assertNotNull(dto.getLastModifiedBy());
        assertNotNull(dto.getLastModifiedDate());
    }

    @Test
    public void DBs2ThinDTOs() throws Exception {
        List<CrmTaskThinDTO> dtos = customCrmTaskMapper.DBs2ThinDTOs(crmTasks);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getCreatedBy());
            assertNotNull(dto.getAssignedTo());
            assertNotNull(dto.getId());
            assertNotNull(dto.getSubject());
            assertNotNull(dto.getActivityDate());
            assertNotNull(dto.getComment());
            assertNotNull(dto.getCreatedBy());
            assertNotNull(dto.getCreatedDate());
            assertNotNull(dto.getLastModifiedBy());
            assertNotNull(dto.getLastModifiedDate());

        });
    }
}
