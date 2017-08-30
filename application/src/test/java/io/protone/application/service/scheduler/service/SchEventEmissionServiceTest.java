package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.scheduler.domain.SchEventEmission;
import io.protone.scheduler.repository.SchEventEmissionRepository;
import io.protone.scheduler.service.SchEventEmissionService;
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
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchEventEmissionServiceTest extends SchedulerBaseTest {
    @Autowired
    private SchEventEmissionService schEventEmissionService;

    @Autowired
    private SchEventEmissionRepository schEventEmissionRepository;



    @Before
    public void setUp() throws Exception {
        super.setUp();

    }


    @Test
    public void shouldSaveSchEventEmission() throws Exception {
        //when
        SchEventEmission schEventEmission = factory.manufacturePojo(SchEventEmission.class);
        schEventEmission.setNetwork(corNetwork);
        schEventEmission.setChannel(corChannel);
        //then
        Set<SchEventEmission> fetchedEntity = schEventEmissionService.saveEmission(Sets.newSet(schEventEmission));

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
        assertEquals(schEventEmission.getNetwork(), fetchedEntity.stream().findFirst().get().getNetwork());
    }

    @Test
    public void shouldDeleteSchEventEmission() throws Exception {
        //when
        SchEventEmission schEventEmission = factory.manufacturePojo(SchEventEmission.class);
        schEventEmission.setNetwork(corNetwork);
        schEventEmission.setChannel(corChannel);
        schEventEmission = schEventEmissionRepository.saveAndFlush(schEventEmission);
        //then
        schEventEmissionService.saveEmission(Sets.newSet(schEventEmission));
        SchEventEmission fetchedEntity = schEventEmissionRepository.findOne(schEventEmission.getId());

        //assert
        assertNull(fetchedEntity);
    }
}