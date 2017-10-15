package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
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

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    @Before
    public void setUp() throws Exception {
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
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
        long numberOfColumns = schLogColumnRepostiory.count();

        SchLogColumn schColumnConfiguration = factory.manufacturePojo(SchLogColumn.class);
        schColumnConfiguration.setNetwork(corNetwork);
        schColumnConfiguration.setChannel(corChannel);
        schColumnConfiguration = schLogColumnRepostiory.saveAndFlush(schColumnConfiguration);
        Set<SchLogColumn> schLogColumnSet = Sets.newSet(schColumnConfiguration);
        //then
        schColumnConfigurationService.deleteColumns(schLogColumnSet);
        long numberColumnsAfterDelete = schLogColumnRepostiory.count();


        assertEquals(numberOfColumns, numberColumnsAfterDelete);
    }

}