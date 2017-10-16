package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.repository.SchPlaylistRepository;
import io.protone.scheduler.service.SchPlaylistService;
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

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
//TODO: Implement Test
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchPlaylistServiceTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchPlaylistService schPlaylistService;

    @Autowired
    private SchPlaylistRepository schPlaylistRepository;

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
    public void shouldGetPlaylists() throws Exception {
        //when
        SchPlaylist SchPlaylist = factory.manufacturePojo(SchPlaylist.class);
        SchPlaylist.setNetwork(corNetwork);
        SchPlaylist.setChannel(corChannel);
        SchPlaylist = schPlaylistRepository.save(SchPlaylist);

        //then
        Slice<SchPlaylist> fetchedEntity = schPlaylistService.findSchPlaylistForNetworkAndChannel(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(SchPlaylist.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(SchPlaylist.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldSavePlaylist() throws Exception {
        //when
        SchPlaylist SchPlaylist = factory.manufacturePojo(SchPlaylist.class);
        SchPlaylist.setNetwork(corNetwork);
        SchPlaylist.setChannel(corChannel);
        //then
        SchPlaylist fetchedEntity = schPlaylistService.saveSchPlaylist(SchPlaylist);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(SchPlaylist.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeletePlaylist() throws Exception {
        //when
        SchPlaylist SchPlaylist = factory.manufacturePojo(SchPlaylist.class);
        SchPlaylist.setNetwork(corNetwork);
        SchPlaylist.setChannel(corChannel);
        SchPlaylist = schPlaylistRepository.saveAndFlush(SchPlaylist);
        //then
        schPlaylistService.deleteSchPlaylistByNetworkAndChannelAndDate(corNetwork.getShortcut(), corChannel.getShortcut(), SchPlaylist.getDate());
        SchPlaylist fetchedEntity = schPlaylistRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(corNetwork.getShortcut(), corChannel.getShortcut(), SchPlaylist.getDate());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetPlaylist() throws Exception {
        //when
        SchPlaylist SchPlaylist = factory.manufacturePojo(SchPlaylist.class);
        SchPlaylist.setNetwork(corNetwork);
        SchPlaylist.setChannel(corChannel);
        SchPlaylist = schPlaylistRepository.save(SchPlaylist);

        //then
        SchPlaylistDTO fetchedEntity = schPlaylistService.findSchPlaylistForNetworkAndChannelAndDate(corNetwork.getShortcut(), corChannel.getShortcut(), SchPlaylist.getDate());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(SchPlaylist.getId(), fetchedEntity.getId());

    }
}