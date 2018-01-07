package io.protone.application.service.traffic;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorOrganizationRepository;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.traffic.domain.*;
import io.protone.traffic.repository.TraAdvertisementRepository;
import io.protone.traffic.repository.TraOrderRepository;
import io.protone.traffic.repository.TraPlaylistRepository;
import io.protone.traffic.service.TraPlaylistService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.protone.application.util.TestConstans.*;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 16/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraPlaylistServiceTest {
    @Autowired
    private TraPlaylistService traPlaylistService;

    @Autowired
    private CorOrganizationRepository corOrganizationRepository;


    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private TraPlaylistRepository traPlaylistRepository;

    @Autowired
    private TraAdvertisementRepository traAdvertisementRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private TraOrderRepository traOrderRepository;

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
    public void shouldGetPlaylists() throws Exception {
        //when
        TraPlaylist traPlaylist = factory.manufacturePojo(TraPlaylist.class);

        traPlaylist.setChannel(corChannel);
        traPlaylist = traPlaylistRepository.save(traPlaylist);

        //then
        Slice<TraPlaylist> fetchedEntity = traPlaylistService.getAllPlaylistList(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(traPlaylist.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(traPlaylist.getPlaylistDate(), fetchedEntity.getContent().get(0).getPlaylistDate());
        assertEquals(traPlaylist.getChannel(), fetchedEntity.getContent().get(0).getChannel());

    }

    @Test
    public void shouldSavePlaylist() throws Exception {
        //when
        TraPlaylist traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist.setChannel(corChannel);
        //then
        TraPlaylist fetchedEntity = traPlaylistService.savePlaylist(traPlaylist);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getPlaylistDate().toString(), traPlaylist.getPlaylistDate().toString());
        assertEquals(traPlaylist.getChannel(), fetchedEntity.getChannel());
    }

    @Test
    public void shouldDeletePlaylist() throws Exception {
        //when
        TraPlaylist traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist.setChannel(corChannel);
        traPlaylist = traPlaylistRepository.saveAndFlush(traPlaylist);
        //then
        traPlaylistService.deleteOneTraPlaylistList(traPlaylist.getPlaylistDate(), corOrganization.getShortcut(), corChannel.getShortcut());
        TraPlaylist fetchedEntity = traPlaylistRepository.findOneByPlaylistDateAndChannel_Organization_ShortcutAndChannel_Shortcut(traPlaylist.getPlaylistDate(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetPlaylist() throws Exception {
        //when
        TraPlaylist traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist.setChannel(corChannel);
        traPlaylist = traPlaylistRepository.save(traPlaylist);

        //then
        TraPlaylist fetchedEntity = traPlaylistService.getTraPlaylistList(traPlaylist.getPlaylistDate(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(traPlaylist.getId(), fetchedEntity.getId());
        assertEquals(traPlaylist.getPlaylistDate(), fetchedEntity.getPlaylistDate());
        assertEquals(traPlaylist.getChannel(), fetchedEntity.getChannel());

    }

    @Test
    public void shouldGetPlaylistsInDataeRange() throws Exception {
        //when
        TraPlaylist traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist.setPlaylistDate(LocalDate.now());
        traPlaylist.setChannel(corChannel);
        traPlaylist = traPlaylistRepository.save(traPlaylist);

        TraPlaylist traPlaylist1 = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist1.setPlaylistDate(LocalDate.now().plusDays(2));
        traPlaylist1.setChannel(corChannel);
        traPlaylist1 = traPlaylistRepository.save(traPlaylist1);

        //then
        List<TraPlaylist> fetchedEntity = traPlaylistService.getTraPlaylistListInRange(traPlaylist.getPlaylistDate(), traPlaylist1.getPlaylistDate(), corOrganization.getShortcut(), corChannel.getShortcut());


        //assert
        assertNotNull(fetchedEntity);
        assertEquals(2, fetchedEntity.size());

    }

    @Test
    public void shouldSaveBlockWithEmissions() throws Exception {
        //when
        LibMediaLibrary libMediaLibrary = new LibMediaLibrary();
        libMediaLibrary.setId(1L);
        libMediaLibrary.setShortcut("tes");
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);

        libMediaItem.setLibrary(libMediaLibrary);
        libMediaItem = libMediaItemRepository.save(libMediaItem);
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setChannel(corChannel);

        TraPlaylist traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        TraEmission traEmission = factory.manufacturePojo(TraEmission.class);
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount = crmAccountRepository.save(crmAccount);

        traAdvertisement.customer(crmAccount);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);

        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder.setCustomer(crmAccount);
        traOrder.channel(corChannel);

        traOrder = traOrderRepository.saveAndFlush(traOrder);

        traEmission.setChannel(corChannel);
        traEmission.setAdvertiment(libMediaItem);
        traEmission.setOrder(traOrder);

        TraBlock traBlock = factory.manufacturePojo(TraBlock.class)
                .emissions(Sets.newHashSet(traEmission))
                .channel(corChannel);
        traPlaylist.setPlaylistDate(LocalDate.now());
        traPlaylist.setPlaylists(Sets.newHashSet(traBlock));
        traPlaylist.setChannel(corChannel);
        traPlaylist = traPlaylistRepository.save(traPlaylist);


        //then
        traPlaylistService.savePlaylist(traPlaylist);

        TraPlaylist fetchedEntity = traPlaylistService.getTraPlaylistList(traPlaylist.getPlaylistDate(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getPlaylists());
        assertNotNull(fetchedEntity.getPlaylists().stream().findAny().get().getId());
        assertNotNull(fetchedEntity.getPlaylists().stream().findAny().get().getEmissions());
        assertNotNull(fetchedEntity.getPlaylists().stream().findAny().get().getEmissions().stream().findAny().get().getId());
        assertNotNull(fetchedEntity.getPlaylists().stream().findAny().get().getCreatedBy());
        assertNotNull(fetchedEntity.getPlaylists().stream().findAny().filter(traBlock1 -> !traBlock1.getEmissions().isEmpty()).get().getEmissions().stream().findFirst().get().getCreatedBy());
        assertEquals(1, fetchedEntity.getPlaylists().size());
        assertEquals(1, fetchedEntity.getPlaylists().stream().findAny().get().getEmissions().size());
        assertEquals(1, fetchedEntity.getPlaylists().stream().findAny().get().getEmissions().size());

    }

    @Test
    public void shouldSaveBlocksWithEmissions() throws Exception {
        //when
        LibMediaLibrary libMediaLibrary = new LibMediaLibrary();
        libMediaLibrary.setId(1L);
        libMediaLibrary.setShortcut("tes");
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setChannel(corChannel);
        libMediaItem.setLibrary(libMediaLibrary);
        libMediaItem = libMediaItemRepository.save(libMediaItem);
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setChannel(corChannel);

        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount = crmAccountRepository.save(crmAccount);
        traAdvertisement.customer(crmAccount);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);

        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder.setCustomer(crmAccount);
        traOrder.channel(corChannel);

        traOrder = traOrderRepository.saveAndFlush(traOrder);
        Set<TraBlock> traBlockSet = new HashSet<>();
        Set<TraEmission> traEmissionSet = new HashSet<>();
        TraPlaylist traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        for (int i = 0; i < 3; i++) {

            TraEmission traEmission = factory.manufacturePojo(TraEmission.class);
            traEmission.setChannel(corChannel);
            traEmission.setOrder(traOrder);
            traEmission.setAdvertiment(libMediaItem);
            traEmissionSet.add(traEmission);
        }
        for (int i = 0; i < 3; i++) {

            TraBlock traBlock = factory.manufacturePojo(TraBlock.class)
                    .emissions(traEmissionSet)
                    .channel(corChannel);
            traBlockSet.add(traBlock);
        }
        traPlaylist.setPlaylistDate(LocalDate.now());
        traPlaylist.setPlaylists(traBlockSet);
        traPlaylist.setChannel(corChannel);
        traPlaylist = traPlaylistRepository.save(traPlaylist);


        //then
        traPlaylistService.savePlaylist(traPlaylist);

        TraPlaylist fetchedEntity = traPlaylistService.getTraPlaylistList(traPlaylist.getPlaylistDate(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getPlaylists());
        assertNotNull(fetchedEntity.getPlaylists().stream().findAny().get().getId());
        assertNotNull(fetchedEntity.getPlaylists().stream().findAny().get().getEmissions());
        assertNotNull(fetchedEntity.getPlaylists().stream().findAny().get().getEmissions().stream().findAny().get().getId());

        assertEquals(3, fetchedEntity.getPlaylists().size());
        assertEquals(3, fetchedEntity.getPlaylists().stream().findAny().get().getEmissions().size());

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoPlaylistWithSameDayInOneNetworkAndChannel() {
        //given
        LocalDate localDate = LocalDate.now();

        //when
        TraPlaylist traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist.setId(null);
        traPlaylist.setPlaylistDate(localDate);
        traPlaylist.setChannel(corChannel);

        TraPlaylist traPlaylist1 = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist1.setId(null);
        traPlaylist1.setPlaylistDate(localDate);
        traPlaylist1.setChannel(corChannel);

        //then
        traPlaylist = traPlaylistService.savePlaylist(traPlaylist);
        traPlaylist1 = traPlaylistService.savePlaylist(traPlaylist1);

    }

    @Test
    public void shouldSaveTwoPlaylistWithSameDayInOneNetworkAndDifferentChannels() {
        //given
        LocalDate localDate = LocalDate.now();
        CorChannel corChannelSecond = factory.manufacturePojo(CorChannel.class);
        corChannelSecond.setId(null);
        corChannelSecond.setShortcut("YYY");
        corChannelSecond.setOrganization(corOrganization);
        corChannelRepository.saveAndFlush(corChannelSecond);
        //when
        TraPlaylist traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist.setId(null);
        traPlaylist.setPlaylistDate(localDate);
        traPlaylist.setChannel(corChannel);

        TraPlaylist traPlaylist1 = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist1.setId(null);
        traPlaylist1.setPlaylistDate(localDate);
        traPlaylist1.setChannel(corChannelSecond);

        //then
        traPlaylistService.savePlaylist(traPlaylist);
        traPlaylistService.savePlaylist(traPlaylist1);

    }

    @Test
    public void shouldSaveTwoPlaylistWithSameDayInOneNetworksAndDifferentChannels() {
        //given
        LocalDate localDate = LocalDate.now();

        CorOrganization corOrganizationkSecond = factory.manufacturePojo(CorOrganization.class);
        corOrganizationkSecond.setId(null);
        corOrganizationkSecond = corOrganizationRepository.saveAndFlush(corOrganizationkSecond);
        CorChannel corChannelSecond = factory.manufacturePojo(CorChannel.class);
        corChannelSecond.setId(null);
        corChannelSecond.setShortcut("XXT");
        corChannelSecond.setOrganization(corOrganization);
        corChannelRepository.saveAndFlush(corChannelSecond);
        //when
        TraPlaylist traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist.setId(null);
        traPlaylist.setPlaylistDate(localDate);
        traPlaylist.setChannel(corChannel);

        TraPlaylist traPlaylist1 = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist1.setId(null);
        traPlaylist1.setPlaylistDate(localDate);
        traPlaylist1.setChannel(corChannelSecond);

        //then
        traPlaylistService.savePlaylist(traPlaylist);
        traPlaylistService.savePlaylist(traPlaylist1);
    }

}
