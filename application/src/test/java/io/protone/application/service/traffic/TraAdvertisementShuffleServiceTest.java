package io.protone.application.service.traffic;


import io.protone.application.ProtoneApp;
import io.protone.application.service.traffic.base.TraPlaylistBasedTest;
import io.protone.traffic.api.dto.TraShuffleAdvertisementDTO;
import io.protone.traffic.api.dto.TraShuffleAdvertisementOptionalDTO;
import io.protone.traffic.domain.TraEmission;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.service.TraAdvertisementShuffleService;
import io.protone.traffic.service.shuffle.exception.TrafficShuffleReindexException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.util.Assert.notNull;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraAdvertisementShuffleServiceTest extends TraPlaylistBasedTest {


    private static final int LARGE_NUMBER_TO_SHUFFLE = 20000;

    private static final LocalDate SCHEDULING_START = LocalDate.now().minusDays(14);

    private static final LocalDate SCHEDULING_END = LocalDate.now().plusDays(14);

    private static final long NUMBER_OF_PLAYLISTS = DAYS.between(SCHEDULING_START, SCHEDULING_END);


    @Inject
    private TraAdvertisementShuffleService traAdvertisementShuffleService;


    @Before
    public void setup() throws InterruptedException {
        buildMustHavePojos();
        traPlaylists = new ArrayList<>();
        mediaItemList = buildMediaItems();
        advertisements = buildAdvertisments();

        for (int i = 0; i < NUMBER_OF_PLAYLISTS; i++) {
            traPlaylists.add(buildTraPlaylist(SCHEDULING_START.plusDays(i)));
        }
    }

    @Test
    public void tryShuffleCommercial() throws InterruptedException, TrafficShuffleReindexException {
        //when
        TraShuffleAdvertisementDTO traShuffleAdvertisementDTO = new TraShuffleAdvertisementDTO();

        traShuffleAdvertisementDTO.setLibMediaItemThinDTO(libMediaItemToShuffleThinDTO);
        traShuffleAdvertisementDTO.setTraOrderThinDTO(traOrderThinDTO);
        traShuffleAdvertisementDTO.setFrom(SCHEDULING_START);
        traShuffleAdvertisementDTO.setTo(SCHEDULING_END);
        traShuffleAdvertisementDTO.setNumber(LARGE_NUMBER_TO_SHUFFLE);
        traShuffleAdvertisementDTO.setTraShuffleAdvertisementOptionalDTO(new TraShuffleAdvertisementOptionalDTO());
        //then
        List<TraPlaylist> traPlaylists = traAdvertisementShuffleService.shuffleCommercials(traShuffleAdvertisementDTO, corNetwork.getShortcut(), corChannel.getShortcut());

        //assert
        notNull(traPlaylists);
        traPlaylists.stream().forEach(traPlaylist -> {
            traPlaylist.getPlaylists().stream().forEach(traBlock -> {
                long numberFounded = traBlock.getEmissions().stream().filter(traEmission -> traEmission.getAdvertiment().getId().equals(traOrderThinDTO.getAdvertismentId().getId())).count();
                assertTrue((numberFounded < 2 && numberFounded >= 0));
                assertTrue((traBlock.getLength() >= traBlock.getEmissions().stream().mapToDouble(traEmission -> traEmission.getAdvertiment().getLength()).sum()));
                assertNotNull(traBlock.getNetwork());
                assertNotNull(traBlock.getChannel());
                Optional<TraEmission> traEmission = traBlock.getEmissions().stream().findFirst();
                if (traEmission.isPresent()) {
                    assertNotNull(traEmission.get().getSequence());
                    assertNotNull(traEmission.get().getNetwork());
                    assertNotNull(traEmission.get().getChannel());
                }

            });
        });
    }


}
