package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.repository.SchLogColumnRepostiory;
import io.protone.scheduler.service.SchLogColumnService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchLogColumnServiceTest extends SchedulerBaseTest {

    @Autowired
    private SchLogColumnService schColumnConfigurationService;

    @Autowired
    private SchLogColumnRepostiory schLogColumnRepostiory;


    @Before
    public void setUp() throws Exception {
        super.setUp();

    }



    @Test
    public void shouldSaveColumnConfiguration() throws Exception {
        //when
        SchLogColumn schColumnConfiguration = factory.manufacturePojo(SchLogColumn.class);
        schColumnConfiguration.setNetwork(corNetwork);
        schColumnConfiguration.setChannel(corChannel);
        Set<SchLogColumn> schLogColumnSet = Sets.newSet(schColumnConfiguration);
        //then
        Set<SchLogColumn> fetchedEntity = schColumnConfigurationService.saveLogColumn(schLogColumnSet);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
        assertEquals(schColumnConfiguration.getNetwork(), fetchedEntity.stream().findFirst().get().getNetwork());
    }

    @Test
    public void shouldDeleteColumnConfiguration() throws Exception {
        //when
        SchLogColumn schColumnConfiguration = factory.manufacturePojo(SchLogColumn.class);
        schColumnConfiguration.setNetwork(corNetwork);
        schColumnConfiguration.setChannel(corChannel);
        schColumnConfiguration = schLogColumnRepostiory.saveAndFlush(schColumnConfiguration);
        Set<SchLogColumn> schLogColumnSet = Sets.newSet(schColumnConfiguration);
        //then
        schColumnConfigurationService.deleteColumns(schLogColumnSet);
//        SchLogColumn fetchedEntity = schLogColumnRepostiory.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schColumnConfiguration.getShortName());

        //assert
        //assertNull(fetchedEntity);
    }

}