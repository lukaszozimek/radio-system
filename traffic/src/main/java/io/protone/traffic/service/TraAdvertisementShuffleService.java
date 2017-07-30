package io.protone.traffic.service;


import io.protone.library.domain.LibMediaItem;
import io.protone.library.service.LibItemService;
import io.protone.traffic.api.dto.TraShuffleAdvertisementDTO;
import io.protone.traffic.domain.TraBlock;
import io.protone.traffic.domain.TraEmission;
import io.protone.traffic.domain.TraPlaylist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.concurrent.ThreadLocalRandom.current;
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
    private LibItemService libItemService;

    public static boolean canAddEmissionToBlock(long lastTimeStop, long blockLenght, Double mediaItemLenght) {
        return lastTimeStop < blockLenght && (lastTimeStop + mediaItemLenght.longValue()) <= blockLenght;
    }

    public List<TraPlaylist> shuffleCommercials(TraShuffleAdvertisementDTO tarShuffleAdvertisementPT, String networkShortcut, String channelShortcut) throws InterruptedException {
        log.debug("Start shuffling commercial");
        log.debug("Commercial to shuffle {}", tarShuffleAdvertisementPT.getNumber());
        LibMediaItem mediaItem = libItemService.getMediaItem(networkShortcut, "com", tarShuffleAdvertisementPT.getLibMediaItemThinDTO().getIdx());
        log.debug("Found advertisment {}", mediaItem.getId());
        List<TraPlaylist> traPlaylistListInRange = traPlaylistService.getTraPlaylistListInRange(tarShuffleAdvertisementPT.getFrom(), tarShuffleAdvertisementPT.getTo(), networkShortcut, channelShortcut);
        log.debug("Found number of Playlist in range : {}", traPlaylistListInRange.size());
        int numberOfCommercialsShuffled = 0;
        for (int i = 0; i < traPlaylistListInRange.size(); i++) {
            log.warn("Start Looking in {}", traPlaylistListInRange.get(i));
            TraPlaylist traPlaylist = traPlaylistListInRange.get(i);
            int numberOfScheduledBlocks = traPlaylist.getPlaylists().size();
            log.debug("Has number of Blocks {}", numberOfScheduledBlocks);
            for (int currentBlockIndex = 0; currentBlockIndex < numberOfScheduledBlocks; currentBlockIndex++) {
                if (isNumberOfShuffeledCommercialsDifferentThenRequestedNumber(numberOfCommercialsShuffled, tarShuffleAdvertisementPT.getNumber())) {
                    int blockIndex = current().nextInt(numberOfScheduledBlocks);
                    log.debug("Check is it possible to shuffle commercial in block: {}", blockIndex);
                    TraBlock traBlock = traPlaylist.getPlaylists().stream().collect(toList()).get(blockIndex);
                    if (isAdvertismentInBlock(traBlock.getEmissions(), tarShuffleAdvertisementPT.getLibMediaItemThinDTO().getIdx())) {
                        if (areEmissionsInBlock(traBlock)) {
                            log.debug("Block size is {}", traBlock.getEmissions().size());
                            Long lastTimeStop = traBlock.getEmissions().stream().max(Comparator.comparingLong(TraEmission::getTimeStop)).get().getTimeStop();
                            Integer lastSequence = traBlock.getEmissions().stream().max(Comparator.comparingLong(TraEmission::getSequence)).get().getSequence();
                            if (canAddEmissionToBlock(lastTimeStop, traBlock.getLength(), mediaItem.getLength())) {
                                TraEmission emisssion = new TraEmission().block(traBlock).sequence(lastSequence + 1).timeStart(lastTimeStop).timeStop(lastTimeStop + mediaItem.getLength().longValue()).advertiment(mediaItem).channel(traBlock.getChannel()).network(traBlock.getNetwork());
                                traBlock.addEmissions(emisssion);
                                numberOfCommercialsShuffled++;
                            }
                        } else {
                            log.debug("Block is empty");
                            Long lastTimeStop = 0L;
                            TraEmission emisssion = new TraEmission().block(traBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + mediaItem.getLength().longValue()).advertiment(mediaItem).sequence(0).channel(traBlock.getChannel()).network(traBlock.getNetwork());
                            traBlock.addEmissions(emisssion);
                            numberOfCommercialsShuffled++;
                        }
                    }
                } else {
                    break;
                }
            }
            log.warn("STOP Looking in {}", traPlaylistListInRange.get(i));
        }
        log.debug("Number shuffled: {}, Number unshuffled commercials : {}", numberOfCommercialsShuffled, tarShuffleAdvertisementPT.getNumber() - numberOfCommercialsShuffled);
        return traPlaylistListInRange;
    }

    private boolean areEmissionsInBlock(TraBlock traBlock) {
        return traBlock.getEmissions().size() != 0;
    }

    private boolean isNumberOfShuffeledCommercialsDifferentThenRequestedNumber(int numberOfCommercialsShuffled, int requestedNumber) {
        return numberOfCommercialsShuffled != requestedNumber;
    }

    private boolean isAdvertismentInBlock(Set<TraEmission> traEmissionSet, String mediaItemIdx) {
        return traEmissionSet.stream().filter(traEmission -> traEmission.getAdvertiment().getIdx().equalsIgnoreCase(mediaItemIdx)).count() == 0;

    }


}

