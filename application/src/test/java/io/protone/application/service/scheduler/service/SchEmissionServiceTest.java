package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.repository.SchEmissionRepository;
import io.protone.scheduler.service.SchEmissionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchEmissionServiceTest extends SchedulerBaseTest {

    @Autowired
    private SchEmissionService schEmissionService;

    @Autowired
    private SchEmissionRepository schEmissionRepository;

    @Before
    public void setUp() throws Exception {
        super.setUp();

    }


    @Test
    public void shouldSaveSchEmission() throws Exception {
        //when
        SchEmission schEmission = factory.manufacturePojo(SchEmission.class);
        schEmission.setNetwork(corNetwork);
        schEmission.setChannel(corChannel);
        //then
        Set<SchEmission> fetchedEntity = schEmissionService.saveEmission(Sets.newSet(schEmission));

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
        assertEquals(schEmission.getNetwork(), fetchedEntity.stream().findFirst().get().getNetwork());
    }

    @Test
    public void shouldSaveSchEmissionWithAttachment() throws Exception {
        //when
        Set<SchEmission> schEmissions = Sets.newSet(buildEmissionForWithAttachment());

        //then
        Set<SchEmission> savedEmissions = schEmissionService.saveEmission(schEmissions);

        //assert
        assertNotNull(savedEmissions);
        assertNotNull(savedEmissions.stream().findFirst().get().getId());
        assertEquals(schEmissions.stream().findFirst().get().getNetwork(), savedEmissions.stream().findFirst().get().getNetwork());
        assertNotNull(savedEmissions.stream().findFirst().get().getAttachments());
        assertNotNull(savedEmissions.stream().findFirst().get().getAttachments().stream().findFirst().get().getId());
    }

    @Test
    public void shouldDeleteSchEmission() throws Exception {
        //when
        SchEmission schEmission = factory.manufacturePojo(SchEmission.class);
        schEmission.setNetwork(corNetwork);
        schEmission.setChannel(corChannel);
        schEmission = schEmissionRepository.saveAndFlush(schEmission);
        //then
        schEmissionService.deleteEmissions(Sets.newSet(schEmission));
        SchEmission fetchedEntity = schEmissionRepository.findOne(schEmission.getId());

        //assert
        assertNull(fetchedEntity);
    }

}