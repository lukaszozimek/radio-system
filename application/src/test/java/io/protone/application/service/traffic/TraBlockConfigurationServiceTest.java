package io.protone.application.service.traffic;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.traffic.domain.TraBlockConfiguration;
import io.protone.traffic.repository.TraBlockConfigurationRepository;
import io.protone.traffic.service.TraBlockConfigurationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;

import static io.protone.application.util.TestConstans.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 16/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraBlockConfigurationServiceTest {

    @Autowired
    private TraBlockConfigurationService traBlockConfigurationService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private TraBlockConfigurationRepository traBlockRepository;

    private CorChannel corChannel;

    private CorOrganization corOrganization;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.setOrganization(corOrganization);

    }

    @Test
    public void shouldGetBlocksConfiguration() throws Exception {

        //when
        TraBlockConfiguration traBlockConfiguration = factory.manufacturePojo(TraBlockConfiguration.class);
        traBlockConfiguration.setChannel(corChannel);
        traBlockRepository.save(traBlockConfiguration);

        //then
        List<TraBlockConfiguration> fetchedEntity = traBlockConfigurationService.getAllBlockConfigurations(corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);

    }

    @Test
    public void shouldSaveBlockConfiguration() throws Exception {
        //when
        TraBlockConfiguration traBlockConfiguration = factory.manufacturePojo(TraBlockConfiguration.class);
        traBlockConfiguration.setChannel(corChannel);

        //then
        TraBlockConfiguration fetchedEntity = traBlockConfigurationService.saveBlockConfiguration(traBlockConfiguration);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(fetchedEntity.getName(), traBlockConfiguration.getName());
    }

    @Test
    public void shouldDeleteBlockConfiguration() throws Exception {
        //when
        TraBlockConfiguration traBlockConfiguration = factory.manufacturePojo(TraBlockConfiguration.class);
        traBlockConfiguration.setChannel(corChannel);
        traBlockConfiguration = traBlockRepository.save(traBlockConfiguration);
        //then
        traBlockConfigurationService.deleteBlockConfiguration(traBlockConfiguration.getId(), corOrganization.getShortcut(), corChannel.getShortcut());
        TraBlockConfiguration fetchedEntity = traBlockConfigurationService.findConfigurationBlock(traBlockConfiguration.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetBlockConfiguration() throws Exception {
        //when
        TraBlockConfiguration traBlockConfiguration = factory.manufacturePojo(TraBlockConfiguration.class);
        traBlockConfiguration.setChannel(corChannel);
        traBlockConfiguration = traBlockRepository.save(traBlockConfiguration);

        //then
        List<TraBlockConfiguration> fetchedEntity = traBlockConfigurationService.getAllBlockConfigurationsByDay(corOrganization.getShortcut(), corChannel.getShortcut(), traBlockConfiguration.getDay());

        //assert
        assertNotNull(fetchedEntity);

    }

}
