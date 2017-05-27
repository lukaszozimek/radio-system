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
        for (int i = 0; i < traPlaylistListInRange.size(); i++) {
            log.debug("Start Looking in {}", traPlaylistListInRange.get(i));
            TraPlaylist traPlaylist = traPlaylistListInRange.get(i);
            int numberOfScheduledBlocks = traPlaylist.getPlaylists().size();
            log.debug("Has number of Blocks {}", numberOfScheduledBlocks);
            if (numberOfCommercialsShuffled != tarShuffleAdvertisementPT.getNumber()) {
                for (int currentBlockIndex = 0; currentBlockIndex < numberOfScheduledBlocks; i++) {
                    int blockIndex = java.util.concurrent.ThreadLocalRandom.current().nextInt(numberOfScheduledBlocks);
                    log.debug("Check is it possible to shuffle commercial in block: {}", blockIndex);
                    TraBlock traBlock = traPlaylist.getPlaylists().stream().collect(toList()).get(blockIndex);
                    Set<TraEmission> traEmissionSet = traBlock.getEmissions();
                    long numberOfAdvertisements = traEmissionSet.stream().filter(traEmission -> traEmission.getAdvertiment().getMediaItem().getIdx().equalsIgnoreCase(tarShuffleAdvertisementPT.getTraAdvertisementDTO().getMediaItemId().getIdx())).count();
                    if (numberOfAdvertisements == 0) {
                        if (traBlock.getEmissions().size() != 0) {
                            log.debug("Block size is {}", traBlock.getEmissions().size());
                            Long lastTimeStop = traBlock.getEmissions().stream().max(Comparator.comparingLong(TraEmission::getTimeStop)).get().getTimeStop();
                            if (lastTimeStop < traBlock.getLength() && (lastTimeStop + traAdvertisement.getMediaItem().getLength().longValue()) <= traBlock.getLength()) {
                                TraEmission emisssion = new TraEmission().block(traBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + traAdvertisement.getMediaItem().getLength().longValue()).advertiment(traAdvertisement);
                                traBlock.addEmissions(emisssion);
                                numberOfCommercialsShuffled++;
                            }
                        } else {
                            log.debug("Block is empty");
                            Long lastTimeStop = 0L;
                            TraEmission emisssion = new TraEmission().block(traBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + traAdvertisement.getMediaItem().getLength().longValue()).advertiment(traAdvertisement);
                            traBlock.addEmissions(emisssion);
                            numberOfCommercialsShuffled++;
                        }

                    }
                }
            }
            else {
                break;
            }
        }
        log.debug("Number shuffled: {}, Number unshuffled commercials : {}", numberOfCommercialsShuffled, tarShuffleAdvertisementPT.getNumber() - numberOfCommercialsShuffled);
        return traPlaylistListInRange;
    }

}

