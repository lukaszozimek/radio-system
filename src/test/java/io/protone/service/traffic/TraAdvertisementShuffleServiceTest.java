package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.*;
import io.protone.repository.cor.CorChannelRepository;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.web.rest.dto.traffic.TraAdvertisementDTO;
import io.protone.web.rest.dto.traffic.TraShuffleAdvertisementDTO;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraAdvertisementShuffleServiceTest {

    private static final int COMMERCIALS_AUDIO_FILES_NUMBER_IN_PLAYLIST_SCHEDULE = 50;

    private static final int SCHEDULED_EMISSIONS = 300;

    private static final LocalDate SCHEDULING_START = LocalDate.now().minusDays(14);

    private static final LocalDate SCHEDULING_END = LocalDate.now().plusDays(14);

    private static final long NUMBER_OF_PLAYLISTS = DAYS.between(SCHEDULING_START, SCHEDULING_END);

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


    private PodamFactory factory = new PodamFactoryImpl();

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private TraAdvertisementDTO advertisementToShuffleDTO;

    @Before
    public void setup() {

    }

    @Test
    public void tryShuffleCommercial() {
        //when
        TraShuffleAdvertisementDTO traShuffleAdvertisementDTO = new TraShuffleAdvertisementDTO();
        traShuffleAdvertisementDTO.setTraAdvertisementDTO(advertisementToShuffleDTO);
        traShuffleAdvertisementDTO.setFrom(SCHEDULING_START);
        traShuffleAdvertisementDTO.setTo(SCHEDULING_END);
    }

    private List<LibMediaItem> buildMediaItems() {
        List<LibMediaItem> libMediaItems = Lists.newArrayList();
        for (int i = 0; i < COMMERCIALS_AUDIO_FILES_NUMBER_IN_PLAYLIST_SCHEDULE; i++) {
            LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
            libMediaItem.length(30000.0);
            libMediaItem.network(corNetwork);
            libMediaItems.add(libMediaItemRepository.saveAndFlush(libMediaItem));

        }
        return libMediaItems;
    }

    private List<TraAdvertisement> buildAdvertisments() {
        List<TraAdvertisement> traAdvertisements = Lists.newArrayList();
        for (int i = 0; i < COMMERCIALS_AUDIO_FILES_NUMBER_IN_PLAYLIST_SCHEDULE; i++) {
            TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
            traAdvertisement.network(corNetwork);
            traAdvertisements.add(traAdvertisementService.saveAdvertisement(traAdvertisement));
        }
        return traAdvertisements;
    }

    private List<TraBlock> buildTraBlocks() {
        return null;
    }

    private List<TraPlaylist> buildTraPlaylists() {
        return null;
    }


}
