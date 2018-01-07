package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
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
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.Set;

import static io.protone.application.util.TestConstans.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchLogColumnServiceTest {
    protected PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchLogColumnService schColumnConfigurationService;

    @Autowired
    private SchLogColumnRepostiory schLogColumnRepostiory;

    private CorChannel corChannel;

    private CorOrganization corOrganization;


    @Before
    public void setUp() throws Exception {

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

    }


    @Test
    public void shouldSaveColumnConfiguration() throws Exception {
        //when
        SchLogColumn schColumnConfiguration = factory.manufacturePojo(SchLogColumn.class);
        schColumnConfiguration.setChannel(corChannel);
        Set<SchLogColumn> schLogColumnSet = Sets.newSet(schColumnConfiguration);
        //then
        Set<SchLogColumn> fetchedEntity = schColumnConfigurationService.saveLogColumn(schLogColumnSet);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
    }

    @Test
    public void shouldDeleteColumnConfiguration() throws Exception {
        //when
        long numberOfColumns = schLogColumnRepostiory.count();

        SchLogColumn schColumnConfiguration = factory.manufacturePojo(SchLogColumn.class);
        schColumnConfiguration.setChannel(corChannel);
        schColumnConfiguration = schLogColumnRepostiory.saveAndFlush(schColumnConfiguration);
        Set<SchLogColumn> schLogColumnSet = Sets.newSet(schColumnConfiguration);
        //then
        schColumnConfigurationService.deleteColumns(schLogColumnSet);
        long numberColumnsAfterDelete = schLogColumnRepostiory.count();


        assertEquals(numberOfColumns, numberColumnsAfterDelete);
    }

}