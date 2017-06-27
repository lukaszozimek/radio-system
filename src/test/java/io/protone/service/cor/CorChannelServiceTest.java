package io.protone.service.cor;

import io.protone.ProtoneApp;
import io.protone.core.repository.cor.CorChannelRepository;
import io.protone.core.repository.cor.CorNetworkRepository;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 28.04.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CorChannelServiceTest {

    private static final String TEST_NAME = "Tes";
    @Autowired
    private CorChannelService corChannelService;
    @Autowired
    private CorChannelRepository corChannelRepository;
    @Autowired
    private CorNetworkRepository corNetworkRepository;

    private CorChannel corChannel;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void initPojos() {
        factory = new PodamFactoryImpl();
        corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel.setId(null);

        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

        corChannel.setNetwork(corNetwork);
        corChannel = corChannelRepository.saveAndFlush(corChannel);
    }

    @Test
    public void findAllChannel() throws Exception {
        List<CorChannel> corChannels = corChannelService.findAllChannel(corNetwork.getShortcut(), new PageRequest(0, 10));
        assertNotNull(corChannels);
        assertEquals(corChannels.size(), 1);
        assertEquals(corChannels.get(0).getId(), corChannel.getId());

        assertNotNull(corChannels.get(0).getCreatedBy());
    }

    @Test
    public void findChannel() throws Exception {
        CorChannel fetechedEntity = corChannelService.findChannel(corNetwork.getShortcut(), corChannel.getShortcut());
        assertEquals(fetechedEntity.getDescription(), corChannel.getDescription());
        assertEquals(fetechedEntity.getId(), corChannel.getId());
        assertEquals(fetechedEntity.getName(), corChannel.getName());
        assertEquals(fetechedEntity.getNetwork().getId(), corChannel.getNetwork().getId());

    }


    @Test
    public void save() throws Exception {
        CorChannel localChannel = factory.manufacturePojo(CorChannel.class);
        localChannel.setNetwork(corNetwork);
        localChannel = corChannelService.save(localChannel);
        assertNotNull(localChannel);
        assertNotNull(localChannel.getId());
        assertNotNull(localChannel.getCreatedBy());

    }

    @Test
    public void deleteChannel() throws Exception {
        corChannelService.deleteChannel(corNetwork.getShortcut(), corChannel.getShortcut());
        CorChannel fetechedEntity = corChannelService.findChannel(corNetwork.getShortcut(), corChannel.getShortcut());

        assertNull(fetechedEntity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoCorChannelWithSameShortNameInOneNetwork() {

        /// /when
        CorChannel corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel.setId(null);
        corChannel.setShortcut(TEST_NAME);
        corChannel.setNetwork(corNetwork);
        CorChannel corChannel1 = factory.manufacturePojo(CorChannel.class);
        corChannel1.setId(null);
        corChannel1.setShortcut(TEST_NAME);
        corChannel1.setNetwork(corNetwork);

        corChannel = corChannelService.save(corChannel);
        corChannel1 = corChannelService.save(corChannel1);

    }

    @Test
    public void shouldSaveTwoCorChannelWithSameNameInDifferentNetwork() {
        //given
        CorNetwork corNetworkSecond = factory.manufacturePojo(CorNetwork.class);
        corNetworkSecond.setId(null);
        corNetworkSecond = corNetworkRepository.save(corNetworkSecond);

        /// /when
        CorChannel corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel.setId(null);
        corChannel.setShortcut(TEST_NAME);
        corChannel.setNetwork(corNetwork);
        CorChannel corChannel1 = factory.manufacturePojo(CorChannel.class);
        corChannel1.setId(null);
        corChannel1.setShortcut(TEST_NAME);
        corChannel1.setNetwork(corNetworkSecond);

        corChannel = corChannelService.save(corChannel);
        corChannel1 = corChannelService.save(corChannel1);


    }

}
