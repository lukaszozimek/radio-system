package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.core.repository.cor.CorChannelRepository;
import io.protone.core.repository.cor.CorNetworkRepository;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraBlockConfiguration;
import io.protone.repository.traffic.TraBlockConfigurationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

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

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

        corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel.setId(null);
        corChannel = corChannelRepository.save(corChannel);

    }

    @Test
    public void shouldGetBlocksConfiguration() throws Exception {
        //when
        TraBlockConfiguration traBlockConfiguration = factory.manufacturePojo(TraBlockConfiguration.class);
        traBlockConfiguration.setNetwork(corNetwork);
        traBlockConfiguration.setChannel(corChannel);
        traBlockConfiguration = traBlockRepository.save(traBlockConfiguration);

        //then
        List<TraBlockConfiguration> fetchedEntity = traBlockConfigurationService.getAllBlockConfigurations(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(traBlockConfiguration.getId(), fetchedEntity.get(0).getId());
        assertEquals(traBlockConfiguration.getNetwork(), fetchedEntity.get(0).getNetwork());

    }

    @Test
    public void shouldSaveBlockConfiguration() throws Exception {
        //when
        TraBlockConfiguration traBlockConfiguration = factory.manufacturePojo(TraBlockConfiguration.class);
        traBlockConfiguration.setChannel(corChannel);
        traBlockConfiguration.setNetwork(corNetwork);

        //then
        TraBlockConfiguration fetchedEntity = traBlockConfigurationService.saveBlockConfiguration(traBlockConfiguration);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(fetchedEntity.getName(), traBlockConfiguration.getName());
        assertEquals(traBlockConfiguration.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteBlockConfiguration() throws Exception {
        //when
        TraBlockConfiguration traBlockConfiguration = factory.manufacturePojo(TraBlockConfiguration.class);
        traBlockConfiguration.setChannel(corChannel);
        traBlockConfiguration.setNetwork(corNetwork);
        traBlockConfiguration = traBlockRepository.save(traBlockConfiguration);
        //then
        traBlockConfigurationService.deleteBlockConfiguration(traBlockConfiguration.getId(), corNetwork.getShortcut());
        TraBlockConfiguration fetchedEntity = traBlockConfigurationService.findConfigurationBlock(traBlockConfiguration.getId(), corNetwork.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetBlockConfiguration() throws Exception {
        //when
        TraBlockConfiguration traBlockConfiguration = factory.manufacturePojo(TraBlockConfiguration.class);
        traBlockConfiguration.setChannel(corChannel);
        traBlockConfiguration.setNetwork(corNetwork);
        traBlockConfiguration = traBlockRepository.save(traBlockConfiguration);

        //then
        List<TraBlockConfiguration> fetchedEntity = traBlockConfigurationService.getAllBlockConfigurationsByDay(corNetwork.getShortcut(), traBlockConfiguration.getDay());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(traBlockConfiguration.getId(), fetchedEntity.get(0).getId());
        assertEquals(traBlockConfiguration.getNetwork(), fetchedEntity.get(0).getNetwork());

    }

}
