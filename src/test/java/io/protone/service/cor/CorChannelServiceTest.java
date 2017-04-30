package io.protone.service.cor;

import io.protone.ProtoneApp;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.repository.cor.CorChannelRepository;
import io.protone.repository.cor.CorNetworkRepository;
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
 * Created by lukaszozimek on 28.04.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CorChannelServiceTest {

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
        List<CorChannel> corChannels = corChannelService.findAllChannel(corNetwork, new PageRequest(0, 10));
        assertNotNull(corChannels);
        assertEquals(corChannels.size(), 1);
        assertEquals(corChannels.get(0).getId(), corChannel.getId());

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

    }

    @Test
    public void deleteChannel() throws Exception {
        corChannelService.deleteChannel(corNetwork.getShortcut(), corChannel.getShortcut());
        CorChannel fetechedEntity = corChannelService.findChannel(corNetwork.getShortcut(), corChannel.getShortcut());

        assertNull(fetechedEntity);
    }
}
