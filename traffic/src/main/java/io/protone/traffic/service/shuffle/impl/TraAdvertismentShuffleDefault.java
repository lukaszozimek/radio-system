package io.protone.traffic.service.shuffle.impl;

import io.protone.library.domain.LibMediaItem;
import io.protone.library.service.LibMediaItemService;
import io.protone.traffic.api.dto.TraShuffleAdvertisementDTO;
import io.protone.traffic.domain.TraBlock;
import io.protone.traffic.domain.TraEmission;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.service.TraAdvertisementShuffleService;
import io.protone.traffic.service.TraOrderService;
import io.protone.traffic.service.TraPlaylistService;
import io.protone.traffic.service.shuffle.TraAdvertismentShuffle;
import io.protone.traffic.service.shuffle.exception.TrafficShuffleReindexException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.protone.traffic.service.TraAdvertisementShuffleService.canAddEmissionToBlock;
import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 31/07/2017.
 */
@Service("traAdvertismentShuffleDefault")
@Qualifier("traAdvertismentShuffleDefault")
public class TraAdvertismentShuffleDefault implements TraAdvertismentShuffle {
    private final Logger log = LoggerFactory.getLogger(TraAdvertisementShuffleService.class);

    @Inject
    private TraPlaylistService traPlaylistService;

    @Inject
    private LibMediaItemService libMediaItemService;
    @Inject
    private TraOrderService traOrderService;

    public List<TraPlaylist> shuffleCommercials(TraShuffleAdvertisementDTO tarShuffleAdvertisementPT, String organizationShortcut, String channelShortcut) throws InterruptedException, TrafficShuffleReindexException {
        log.debug("Start shuffling commercial");
        log.debug("Commercial to shuffle {}", tarShuffleAdvertisementPT.getNumber());
        LibMediaItem mediaItem = libMediaItemService.getMediaItem(organizationShortcut, "com", tarShuffleAdvertisementPT.getLibMediaItemThinDTO().getIdx());
        log.debug("Found advertisment {}", mediaItem.getId());
        TraOrder traOrder = traOrderService.getOrder(tarShuffleAdvertisementPT.getTraOrderThinDTO().getId(), organizationShortcut);
        log.debug("Found Order {}", traOrder.getId());
        List<TraPlaylist> traPlaylistListInRange = traPlaylistService.getTraPlaylistListInRange(tarShuffleAdvertisementPT.getFrom(), tarShuffleAdvertisementPT.getTo(), organizationShortcut, channelShortcut);
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
                            if (canAddEmissionToBlock(lastTimeStop, traBlock.getLength(), mediaItem.getLength()) && hasFixedLastPostion(traBlock)) {
                                traBlock = reindexToFitLastPostionEmissions(traBlock);
                                TraEmission emisssion = new TraEmission().block(traBlock).sequence(lastSequence - 1).timeStart(lastTimeStop).timeStop(lastTimeStop + mediaItem.getLength().longValue()).advertiment(mediaItem).channel(traBlock.getChannel()).network(traBlock.getNetwork());
                                traBlock.addEmissions(emisssion);
                                numberOfCommercialsShuffled++;
                            } else if (canAddEmissionToBlock(lastTimeStop, traBlock.getLength(), mediaItem.getLength()) && !hasFixedLastPostion(traBlock)) {
                                TraEmission emisssion = new TraEmission().order(traOrder).block(traBlock).sequence(lastSequence + 1).timeStart(lastTimeStop).timeStop(lastTimeStop + mediaItem.getLength().longValue()).advertiment(mediaItem).channel(traBlock.getChannel()).network(traBlock.getNetwork());
                                traBlock.addEmissions(emisssion);
                                numberOfCommercialsShuffled++;
                            }
                        } else {
                            log.debug("Block is empty");
                            Long lastTimeStop = 0L;
                            TraEmission emisssion = new TraEmission().order(traOrder).block(traBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + mediaItem.getLength().longValue()).advertiment(mediaItem).sequence(0).channel(traBlock.getChannel()).network(traBlock.getNetwork());
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

    private TraBlock reindexToFitLastPostionEmissions(TraBlock traBlock) throws TrafficShuffleReindexException {
        Optional<TraEmission> lastFixedEmsiion = traBlock.getEmissions().stream().filter(traEmission -> traEmission.isFixedPosition() && traEmission.isLastPosition()).findFirst();
        if (lastFixedEmsiion.isPresent()) {
            TraEmission lastFixedEmission = lastFixedEmsiion.get();
            traBlock.removeEmissions(lastFixedEmission);
            traBlock.addEmissions(lastFixedEmission.sequence(lastFixedEmission.getSequence() + 1));
            return traBlock;
        }
        throw new TrafficShuffleReindexException();

    }

    private boolean hasFixedLastPostion(TraBlock traBlock) {
        return traBlock.getEmissions().stream().filter(traEmission -> traEmission.isLastPosition() && traEmission.isFixedPosition()).findFirst().isPresent();
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
