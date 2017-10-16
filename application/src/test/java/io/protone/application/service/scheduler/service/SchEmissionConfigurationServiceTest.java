package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.domain.SchEmissionConfiguration;
import io.protone.scheduler.repository.SchEmissionConfigurationRepository;
import io.protone.scheduler.service.SchEmissionConfigurationService;
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

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchEmissionConfigurationServiceTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchEmissionConfigurationService schEmissionConfigurationService;

    @Autowired
    private SchEmissionConfigurationRepository schEmissionConfigurationRepository;

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
    public void shouldSaveSchEmissionConfiguration() throws Exception {
        //when
        SchEmissionConfiguration schEmissionConfiguration = factory.manufacturePojo(SchEmissionConfiguration.class);
        schEmissionConfiguration.setNetwork(corNetwork);
        schEmissionConfiguration.setChannel(corChannel);
        //then
        Set<SchEmissionConfiguration> fetchedEntity = schEmissionConfigurationService.saveEmissionEvent(Sets.newSet(schEmissionConfiguration), null);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
        assertEquals(schEmissionConfiguration.getNetwork(), fetchedEntity.stream().findFirst().get().getNetwork());
    }

    @Test
    public void shouldSaveSchEmissionConfigurationWithAttachment() throws Exception {

    }

    @Test
    public void shouldDeleteSchEmissionConfiguration() throws Exception {
        //when
        SchEmissionConfiguration schEmissionConfiguration = factory.manufacturePojo(SchEmissionConfiguration.class);
        schEmissionConfiguration.setNetwork(corNetwork);
        schEmissionConfiguration.setChannel(corChannel);
        schEmissionConfiguration = schEmissionConfigurationRepository.saveAndFlush(schEmissionConfiguration);
        //then
        schEmissionConfigurationService.deleteEmissions(Sets.newSet(schEmissionConfiguration));
        SchEmissionConfiguration fetchedEntity = schEmissionConfigurationRepository.findOne(schEmissionConfiguration.getId());

        //assert
        assertNull(fetchedEntity);
    }


    @Test
    public void shouldDeleteSchEmissionConfigurationWithAttachment() throws Exception {

    }

}