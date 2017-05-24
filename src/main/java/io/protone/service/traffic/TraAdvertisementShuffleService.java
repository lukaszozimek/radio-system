package io.protone.service.traffic;

import io.protone.domain.TraAdvertisement;
import io.protone.domain.TraBlock;
import io.protone.domain.TraEmission;
import io.protone.web.rest.dto.traffic.TraShuffleAdvertisementDTO;
import io.protone.domain.TraPlaylist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
@Service
public class TraAdvertisementShuffleService {

    private final Logger log = LoggerFactory.getLogger(TraAdvertisementShuffleService.class);

    @Inject
    private TraPlaylistService traPlaylistService;


    @Inject
    private TraAdvertisementService traAdvertisementService;


    public List<TraPlaylist> shuffleCommercials(TraShuffleAdvertisementDTO tarShuffleAdvertisementPT, String networkShortcut, String channelShortcut) {
        log.debug("Start shuffling commercial");
        log.debug("Commercial to shuffle {}", tarShuffleAdvertisementPT.getNumber());
        TraAdvertisement traAdvertisement = traAdvertisementService.getAdvertisement(tarShuffleAdvertisementPT.getTraAdvertisementDTO().getId(), networkShortcut);
        log.debug("Found advertisment {}", traAdvertisement.getId());
        List<TraPlaylist> traPlaylistListInRange = traPlaylistService.getTraPlaylistListInRange(tarShuffleAdvertisementPT.getFrom(), tarShuffleAdvertisementPT.getTo(), networkShortcut, channelShortcut);
        log.debug("Found number of Playlist in range : {}", traPlaylistListInRange.size());
        int numberOfCommercialsShuffled = 0;
        int numberOfScheduledBlocks = 0;// = schBlockList.size();
        for (int i = 0; i < numberOfScheduledBlocks; i++) {
            TraPlaylist traPlaylist = traPlaylistListInRange.get(i);
            if (numberOfCommercialsShuffled != tarShuffleAdvertisementPT.getNumber()) {
                int blockIndex = java.util.concurrent.ThreadLocalRandom.current().nextInt(numberOfScheduledBlocks);
                log.debug("Check is it possible to shuffle commercial in block: {}", blockIndex);
                TraBlock traBlock = traPlaylist.getPlaylists().stream().collect(toList()).get(blockIndex);
                Set<TraEmission> traEmissionSet = traBlock.getEmissions();
                long numberOfAdvertisements = traEmissionSet.stream().filter(traEmission -> traEmission.getAdvertiment().getMediaItem().getIdx().equalsIgnoreCase(tarShuffleAdvertisementPT.getTraAdvertisementDTO().getMediaItemId().getIdx())).count();
                ///Add Filtering by lenghtScheduledTime
                if (numberOfAdvertisements == 0) {
                    Long lastTimeStop = traBlock.getEmissions().stream().max(Comparator.comparingLong(TraEmission::getTimeStop)).get().getTimeStop();
                    if (lastTimeStop < traBlock.getLength() && (lastTimeStop + traAdvertisement.getMediaItem().getLength().longValue()) <= traBlock.getLength()) {
                        TraEmission emisssion = new TraEmission().block(traBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + traAdvertisement.getMediaItem().getLength().longValue()).advertiment(traAdvertisement);
                        numberOfCommercialsShuffled++;
                    }
                } else {
                    break;
                }
            }
            log.debug("Number shuffled: {}, Number unshuffled commercials : {}", numberOfCommercialsShuffled, tarShuffleAdvertisementPT.getNumber() - numberOfCommercialsShuffled);
        }
        return traPlaylistListInRange;
    }
}
