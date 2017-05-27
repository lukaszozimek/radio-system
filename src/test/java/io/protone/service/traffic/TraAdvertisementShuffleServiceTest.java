package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.*;
import io.protone.repository.cor.CorChannelRepository;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.library.LibLibraryRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.repository.traffic.TraBlockRepository;
import io.protone.repository.traffic.TraEmissionRepository;
import io.protone.repository.traffic.TraPlaylistRepository;
import io.protone.web.rest.dto.traffic.TraAdvertisementDTO;
import io.protone.web.rest.dto.traffic.TraShuffleAdvertisementDTO;
import io.protone.web.rest.mapper.TraAdvertisementMapper;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.springframework.util.Assert.notNull;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraAdvertisementShuffleServiceTest {

    private static final int COMMERCIALS_AUDIO_FILES_NUMBER_IN_PLAYLIST_SCHEDULE = 300;

    private static final int DAILY_BLOCK_NUMBER = 96;

    private static final int DAILY_EMISSION_NUMBER_MIDDEL = 300;

    private static final int DAILY_EMISSION_NUMBER_FULL = 570;

    private static final int DAILY_EMISSION_NUMBER_LOW = 100;

    private static final LocalDate SCHEDULING_START = LocalDate.now().minusDays(14);

    private static final LocalDate SCHEDULING_END = LocalDate.now().plusDays(14);

    private static final long NUMBER_OF_PLAYLISTS = DAYS.between(SCHEDULING_START, SCHEDULING_END);

    private static final long SUM_OF_BLOCK_IN_PLAYLISTS = DAILY_BLOCK_NUMBER * NUMBER_OF_PLAYLISTS;

    @Inject
    private TraAdvertisementShuffleService traAdvertisementShuffleService;

    @Inject
    private TraPlaylistService traPlaylistService;

    @Inject
    private TraAdvertisementService traAdvertisementService;

    @Inject
    private LibMediaItemRepository libMediaItemRepository;

    @Inject
    private CorChannelRepository corChannelRepository;

    @Inject
    private CorNetworkRepository corNetworkRepository;

    @Inject
    private TraBlockRepository trablockRepository;

    @Inject
    private TraPlaylistRepository traPlaylistRepository;

    @Inject
    private TraEmissionRepository traEmissionRepository;

    @Inject
    private LibLibraryRepository libLibraryRepository;

    @Inject
    private CrmAccountRepository crmAccountRepository;

    @Inject
    private TraAdvertisementMapper traAdvertisementMapper;

    private PodamFactory factory = new PodamFactoryImpl();

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private LibLibrary libLibrary;

    private TraAdvertisementDTO advertisementToShuffleDTO;

    private TraAdvertisement advertisementToShuffle;

    private LibMediaItem libMediaItemToShuffle;

    private CrmAccount crmAccount;

    private List<LibMediaItem> mediaItemList;

    private List<TraAdvertisement> advertisements;

    private List<TraPlaylist> traPlaylists;

    @Before
    public void setup() {
        buildMustHavePojos();
        traPlaylists = new ArrayList<>();
        mediaItemList = buildMediaItems();
        advertisements = buildAdvertisments();

        for (int i = 0; i < NUMBER_OF_PLAYLISTS; i++) {
            traPlaylists.add(buildTraPlaylist(SCHEDULING_START.plusDays(i)));
        }
    }

    @Test
    public void tryShuffleCommercial() {
        //when
        TraShuffleAdvertisementDTO traShuffleAdvertisementDTO = new TraShuffleAdvertisementDTO();
        traShuffleAdvertisementDTO.setTraAdvertisementDTO(advertisementToShuffleDTO);
        traShuffleAdvertisementDTO.setFrom(SCHEDULING_START);
        traShuffleAdvertisementDTO.setTo(SCHEDULING_END);
        traShuffleAdvertisementDTO.setNumber(50);

        //then
        List<TraPlaylist> traPlaylists = traAdvertisementShuffleService.shuffleCommercials(traShuffleAdvertisementDTO, corNetwork.getShortcut(), corChannel.getShortcut());

        notNull(traPlaylists);

    }

    private List<LibMediaItem> buildMediaItems() {
        List<LibMediaItem> libMediaItems = Lists.newArrayList();
        for (int i = 0; i < COMMERCIALS_AUDIO_FILES_NUMBER_IN_PLAYLIST_SCHEDULE; i++) {
            LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
            libMediaItem.setIdx(String.valueOf(java.util.concurrent.ThreadLocalRandom.current().nextInt()));
            libMediaItem.length(30000.0);
            libMediaItem.network(corNetwork);
            libMediaItem.setLibrary(libLibrary);
            libMediaItems.add(libMediaItemRepository.saveAndFlush(libMediaItem));
        }
        return libMediaItems;
    }

    private List<TraAdvertisement> buildAdvertisments() {
        List<TraAdvertisement> traAdvertisements = Lists.newArrayList();
        mediaItemList.stream().forEach(libMediaItem -> {
            TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
            traAdvertisement.mediaItem(libMediaItem);
            traAdvertisement.network(corNetwork);
            traAdvertisement.setCustomer(crmAccount);
            traAdvertisements.add(traAdvertisementService.saveAdvertisement(traAdvertisement));

        });
        return traAdvertisements;
    }

    private Set<TraBlock> buildDayliBlockStrucutre() {

        Set<TraBlock> traBlocks = new HashSet<>();
        for (int i = 1; i < DAILY_BLOCK_NUMBER; i++) {
            TraBlock trablock = factory.manufacturePojo(TraBlock.class);
            trablock.sequence(i);
            trablock.setLength(Long.valueOf((3 * 60000)));
            trablock.setChannel(corChannel);
            trablock.setNetwork(corNetwork);
            trablock.emissions(buildTraEmissionss(java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 5), 5));
            trablock = trablockRepository.saveAndFlush(trablock);
            traBlocks.add(trablock);
        }
        return traBlocks;
    }

    private Set<TraEmission> buildTraEmissionss(int commercialsInBlock, int maxCommercialNumber) {
        Set<TraEmission> traEmissions = new HashSet<>();
        for (int i = 1; i < commercialsInBlock; i++) {
            TraEmission traEmission = factory.manufacturePojo(TraEmission.class);
            traEmission.advertiment(advertisements.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(0, COMMERCIALS_AUDIO_FILES_NUMBER_IN_PLAYLIST_SCHEDULE)));
            traEmission.sequence(java.util.concurrent.ThreadLocalRandom.current().nextInt(0, maxCommercialNumber));
            traEmission.setChannel(corChannel);
            traEmission.setNetwork(corNetwork);
            traEmission = traEmissionRepository.saveAndFlush(traEmission);
            traEmissions.add(traEmission);
        }

        return traEmissions;
    }

    private TraPlaylist buildTraPlaylist(LocalDate date) {
        TraPlaylist traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist.playlists(buildDayliBlockStrucutre());
        traPlaylist.setPlaylistDate(date);
        traPlaylist.setChannel(corChannel);
        traPlaylist.setNetwork(corNetwork);
        traPlaylist = traPlaylistRepository.saveAndFlush(traPlaylist);
        return traPlaylist;
    }

    private void buildMustHavePojos() {
        corChannel = factory.manufacturePojo(CorChannel.class);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        libLibrary = factory.manufacturePojo(LibLibrary.class);
        crmAccount = factory.manufacturePojo(CrmAccount.class);
        libMediaItemToShuffle = factory.manufacturePojo(LibMediaItem.class);
        advertisementToShuffle = factory.manufacturePojo(TraAdvertisement.class);
        corNetwork.setId(12L);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
        corChannel.setId(12L);
        corChannel.setNetwork(corNetwork);
        corChannel = corChannelRepository.saveAndFlush(corChannel);
        libLibrary.setId(12L);
        libLibrary.network(corNetwork);
        libLibrary = libLibraryRepository.saveAndFlush(libLibrary);

        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);

        libMediaItemToShuffle.setNetwork(corNetwork);
        libMediaItemToShuffle.setLibrary(libLibrary);
        libMediaItemToShuffle = libMediaItemRepository.saveAndFlush(libMediaItemToShuffle);

        advertisementToShuffle.setCustomer(crmAccount);
        advertisementToShuffle.setNetwork(corNetwork);
        advertisementToShuffle.setMediaItem(libMediaItemToShuffle);
        advertisementToShuffle = traAdvertisementService.saveAdvertisement(advertisementToShuffle);
        advertisementToShuffleDTO = traAdvertisementMapper.DB2DTO(advertisementToShuffle);

    }

}
