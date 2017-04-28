package io.protone.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.domain.CrmTask;
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
import static org.junit.Assert.assertNull;

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

    private CrmTaskPT crmTaskPT;

    private List<CrmTaskPT> crmTaskPTS = new ArrayList<>();

    private Set<CrmTask> crmTasks = new HashSet<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmTask = factory.manufacturePojo(CrmTask.class);
        crmTasks.add(crmTask);
        crmTaskPT = factory.manufacturePojo(CrmTaskPT.class);
        crmTaskPTS.add(crmTaskPT);

    }

    @Test
    public void DB2DTO() throws Exception {
        CrmTaskPT dto = customCrmTaskMapper.DB2DTO(crmTask);

        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getAssignedTo());
        assertNotNull(dto.getId());
        assertNotNull(dto.getSubject());
        assertNotNull(dto.getActivityDate());
        assertNotNull(dto.getComment());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmTaskPT> dtos = customCrmTaskMapper.DBs2DTOs(crmTasks);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getCreatedBy());
            assertNotNull(dto.getAssignedTo());
            assertNotNull(dto.getId());
            assertNotNull(dto.getSubject());
            assertNotNull(dto.getActivityDate());
            assertNotNull(dto.getComment());

        });
    }


    @Test
    public void DTO2DB() throws Exception {
        CrmTask entity = customCrmTaskMapper.DTO2DB(crmTaskPT);


        assertNotNull(entity.getCreatedBy());
        assertNotNull(entity.getAssignedTo());
        assertNotNull(entity.getId());
        assertNotNull(entity.getSubject());
        assertNotNull(entity.getActivityDate());
        assertNotNull(entity.getComment());
        assertNotNull(entity.getId());

        assertNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        Set<CrmTask> entities = customCrmTaskMapper.DTOs2DBs(crmTaskPTS);

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

            assertNull(entity.getNetwork());

        });
    }
}
