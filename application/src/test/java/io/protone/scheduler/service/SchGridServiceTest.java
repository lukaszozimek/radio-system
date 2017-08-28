package io.protone.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.repository.SchGridRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchGridServiceTest {
    @Autowired
    private SchGridService schGridService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private SchGridRepository schGridRepository;


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
        corChannel.setShortcut("CEv");
        corChannel.network(corNetwork);
        corChannelRepository.saveAndFlush(corChannel);

    }


    @Test
    public void shouldGetGrids() throws Exception {
        //when
        SchGrid schGrid = factory.manufacturePojo(SchGrid.class);
        schGrid.setNetwork(corNetwork);
        schGrid.setChannel(corChannel);
        schGrid = schGridRepository.save(schGrid);

        //then
        Slice<SchGrid> fetchedEntity = schGridService.findSchGridsForNetworkAndChannel(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schGrid.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schGrid.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldSaveGrid() throws Exception {
        //when
        SchGrid schGrid = factory.manufacturePojo(SchGrid.class);
        schGrid.setNetwork(corNetwork);
        schGrid.setChannel(corChannel);
        //then
        SchGrid fetchedEntity = schGridService.saveGrid(schGrid);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(schGrid.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteGrid() throws Exception {
        //when
        SchGrid schGrid = factory.manufacturePojo(SchGrid.class);
        schGrid.setNetwork(corNetwork);
        schGrid.setChannel(corChannel);
        schGrid = schGridRepository.saveAndFlush(schGrid);
        //then
        schGridService.deleteSchGridByNetworkAndChannelAndShortNAme(corNetwork.getShortcut(), corChannel.getShortcut(), schGrid.getShortName());
        SchGrid fetchedEntity = schGridRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schGrid.getShortName());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetGrid() throws Exception {
        //when
        SchGrid schGrid = factory.manufacturePojo(SchGrid.class);
        schGrid.setNetwork(corNetwork);
        schGrid.setChannel(corChannel);
        schGrid = schGridRepository.save(schGrid);

        //then
        SchGrid fetchedEntity = schGridService.findSchGridForNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schGrid.getShortName());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schGrid.getId(), fetchedEntity.getId());
        assertEquals(schGrid.getNetwork(), fetchedEntity.getNetwork());

    }


}