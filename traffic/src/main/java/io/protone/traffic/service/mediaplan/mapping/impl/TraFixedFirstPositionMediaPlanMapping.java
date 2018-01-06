package io.protone.traffic.service.mediaplan.mapping.impl;

import com.google.common.collect.Lists;
import io.protone.library.domain.LibMediaItem;
import io.protone.traffic.domain.TraBlock;
import io.protone.traffic.domain.TraEmission;
import io.protone.traffic.domain.TraMediaPlanEmission;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.service.TraAdvertisementShuffleService;
import io.protone.traffic.service.mediaplan.diff.TraPlaylistDiff;
import io.protone.traffic.service.mediaplan.mapping.TraMediaPlanMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toSet;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

/**
 * Created by lukaszozimek on 31/07/2017.
 */
@Service("traFixedFirstPositionMediaPlanMapping")
@Qualifier("traFixedFirstPositionMediaPlanMapping")
public class TraFixedFirstPositionMediaPlanMapping implements TraMediaPlanMapping {
    private final Object lockObject = new Object();
    private final Logger log = LoggerFactory.getLogger(TraFixedFirstPositionMediaPlanMapping.class);

    @Override
    public TraPlaylistDiff mapToEntityPlaylist(List<TraPlaylist> entiyPlaylists, List<TraMediaPlanEmission> parsedEmissions, LibMediaItem libMediaItem) {
        log.debug("Start mapping entity Playlist with parsed Playlists");
        List<TraPlaylist> traPlaylists = entiyPlaylists;
        List<TraMediaPlanEmission> excelEmissions = Lists.newArrayList(parsedEmissions.iterator());
        for (TraMediaPlanEmission traMediaPlanEmission : parsedEmissions) {
            Optional<TraPlaylist> filteredTraPlaylist = traPlaylists.stream().filter(traPlaylist -> traMediaPlanEmission.getMediaPlanPlaylistDate().getPlaylistDate().equals(traPlaylist.getPlaylistDate())).findFirst();
            if (filteredTraPlaylist.isPresent()) {
                log.debug("Found Playlist matching to Excel Playlist", filteredTraPlaylist);
                for (TraBlock playlistBlock : filteredTraPlaylist.get().getPlaylists()) {
                    if (isInRange(traMediaPlanEmission.getMediaPlanBlock().getStartBlock(), playlistBlock.getStartBlock(), playlistBlock.getStopBlock())) {
                        log.debug("Found Block matching to range ");
                        if (isNotEmpty(playlistBlock.getEmissions())) {
                            if (!playlistBlock.getEmissions().stream().filter(entityEmission -> entityEmission.getAdvertiment().getId().equals(traMediaPlanEmission.getAdvertiment().getId())).findFirst().isPresent()) {

                                Long lastTimeStop = playlistBlock.getEmissions().stream().max(Comparator.comparingLong(TraEmission::getTimeStop)).get().getTimeStop();
                                Integer lastSequence = playlistBlock.getEmissions().stream().max(Comparator.comparingLong(TraEmission::getSequence)).get().getSequence();
                                if (TraAdvertisementShuffleService.canAddEmissionToBlock(lastTimeStop, playlistBlock.getLength(), libMediaItem.getLength()) && hasNotFixedFirstPostion(playlistBlock)) {
                                    playlistBlock = reindexEmissions(reindexEmissions(playlistBlock));
                                    log.debug("Put commercial into block");
                                    TraEmission emisssion = new TraEmission().sequence(0).block(playlistBlock).firstPosition(true).fixedPosition(true).timeStart(lastTimeStop).timeStop(lastTimeStop + libMediaItem.getLength().longValue()).advertiment(libMediaItem).channel(playlistBlock.getChannel());
                                    playlistBlock.addEmissions(emisssion);
                                    excelEmissions.remove(traMediaPlanEmission);
                                    break;
                                } else {
                                    log.debug("Can't put commercial because block size excide maximum number of seconds or contains fixed First postion");
                                }
                            }
                        } else {
                            log.debug("Block is empty");
                            log.debug("Put commercial into block");
                            Long lastTimeStop = 0L;
                            TraEmission emisssion = new TraEmission().block(playlistBlock).firstPosition(true).fixedPosition(true).timeStart(lastTimeStop).timeStop(lastTimeStop + libMediaItem.getLength().longValue()).advertiment(libMediaItem).sequence(0).channel(playlistBlock.getChannel());
                            playlistBlock.addEmissions(emisssion);
                            excelEmissions.remove(traMediaPlanEmission);
                            break;
                        }
                    }
                }
            }
        }
        return new TraPlaylistDiff(entiyPlaylists, parsedEmissions);
    }

    private TraBlock reindexEmissions(TraBlock traBlock) {
        Map<Integer, TraEmission> reindexedMap = new LinkedHashMap<>();
        Set<TraEmission> notfixedEmssions = traBlock.getEmissions().stream().filter(traEmission -> !traEmission.isFixedPosition()).collect(toSet());
        Set<TraEmission> fixedPostion = traBlock.getEmissions().stream().filter(TraEmission::isFixedPosition).sorted(Comparator.comparing(TraEmission::getSequence)).collect(toSet());
        fixedPostion.stream().forEach(fixedPostionEmission -> {
            reindexedMap.put(fixedPostionEmission.getSequence(), fixedPostionEmission);
        });
        notfixedEmssions.stream().forEach(notfixedEmssion -> {
            Integer index = notfixedEmssion.getSequence() + 1;
            while (reindexedMap.containsKey(index)) {
                if (reindexedMap.get(index).isLastPosition()) {
                    TraEmission lastEmissionFixed = reindexedMap.get(index);
                    reindexedMap.remove(index);
                    lastEmissionFixed.setSequence(index + 2);
                    reindexedMap.put(lastEmissionFixed.getSequence(), lastEmissionFixed);
                    index++;
                } else {
                    index++;
                }
            }
            notfixedEmssion = notfixedEmssion.sequence(index);
            reindexedMap.put(notfixedEmssion.getSequence(), notfixedEmssion);
        });
        return traBlock.emissions(reindexedMap.values().stream().collect(toSet()));
    }


    private boolean hasNotFixedFirstPostion(TraBlock traBlock) {
        return !traBlock.getEmissions().stream().filter(traEmission -> traEmission.isFirsrPosition() && traEmission.isFixedPosition()).findFirst().isPresent();
    }

    private boolean isInRange(long parsedStartBlock, long entityStratBlock, long entityStopBlock) {
        return (parsedStartBlock <= entityStratBlock && parsedStartBlock <= entityStopBlock);
    }

}
