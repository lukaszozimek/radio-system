package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
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

import static io.protone.application.util.TestConstans.*;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchPlaylistServiceTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchPlaylistService schPlaylistService;

    @Autowired
    private SchPlaylistRepository schPlaylistRepository;

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
    public void shouldGetPlaylists() throws Exception {
        //when
        SchPlaylist SchPlaylist = factory.manufacturePojo(SchPlaylist.class);
        SchPlaylist.setChannel(corChannel);
        SchPlaylist = schPlaylistRepository.save(SchPlaylist);

        //then
        Slice<SchPlaylist> fetchedEntity = schPlaylistService.findSchPlaylistForNetworkAndChannel(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(SchPlaylist.getId(), fetchedEntity.getContent().get(0).getId());

    }

    @Test
    public void shouldSavePlaylist() throws Exception {
        //when
        SchPlaylist SchPlaylist = factory.manufacturePojo(SchPlaylist.class);
        SchPlaylist.setChannel(corChannel);
        //then
        SchPlaylist fetchedEntity = schPlaylistService.saveSchPlaylist(SchPlaylist);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
    }

    @Test
    public void shouldDeletePlaylist() throws Exception {
        //when
        SchPlaylist SchPlaylist = factory.manufacturePojo(SchPlaylist.class);
        SchPlaylist.setChannel(corChannel);
        SchPlaylist = schPlaylistRepository.saveAndFlush(SchPlaylist);
        //then
        schPlaylistService.deleteSchPlaylistByNetworkAndChannelAndDate(corOrganization.getShortcut(), corChannel.getShortcut(), SchPlaylist.getDate());
        SchPlaylist fetchedEntity = schPlaylistRepository.findOneByChannel_Organization_ShortcutAndChannel_ShortcutAndDate(corOrganization.getShortcut(), corChannel.getShortcut(), SchPlaylist.getDate());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetPlaylist() throws Exception {
        //when
        SchPlaylist SchPlaylist = factory.manufacturePojo(SchPlaylist.class);
        SchPlaylist.setChannel(corChannel);
        SchPlaylist = schPlaylistRepository.save(SchPlaylist);

        //then
        SchPlaylistDTO fetchedEntity = schPlaylistService.findSchPlaylistForNetworkAndChannelAndDate(corOrganization.getShortcut(), corChannel.getShortcut(), SchPlaylist.getDate());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(SchPlaylist.getId(), fetchedEntity.getId());

    }
}