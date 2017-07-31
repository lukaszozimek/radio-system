package io.protone.traffic.service.mediaplan.mapping.impl;

import com.google.common.collect.Lists;
import io.protone.library.domain.LibMediaItem;
import io.protone.traffic.domain.TraBlock;
import io.protone.traffic.domain.TraEmission;
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
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

/**
 * Created by lukaszozimek on 31/07/2017.
 */
@Service("traFixedFirstPositionMediaPlanMapping")
@Qualifier("traFixedFirstPositionMediaPlanMapping")
public class TraFixedFirstPositionMediaPlanMapping implements TraMediaPlanMapping {
    private final Object lockObject = new Object();
    private final Logger log = LoggerFactory.getLogger(TraFixedFirstPositionMediaPlanMapping.class);

    @Override
    public TraPlaylistDiff mapToEntityPlaylist(List<TraPlaylist> entiyPlaylists, List<TraPlaylist> parsedFromMediaPlan, LibMediaItem libMediaItem) {
        log.debug("Start mapping entity Playlist with parsed Playlists");
        List<TraPlaylist> traPlaylists = entiyPlaylists;
        List<TraPlaylist> traPlaylistsExcel = Lists.newArrayList(parsedFromMediaPlan.iterator());
        traPlaylists.forEach(entiyPlaylist -> {
            entiyPlaylist.setPlaylists(entiyPlaylist.getPlaylists().stream().sorted(Comparator.comparing(TraBlock::getSequence)).collect(toSet()));
            Optional<TraPlaylist> filteredPlaylist = traPlaylistsExcel.stream().filter(parsedPlaylist -> parsedPlaylist.getPlaylistDate().equals(entiyPlaylist.getPlaylistDate())).findFirst();
            if (filteredPlaylist.isPresent()) {
                log.debug("Found Playlist for Date {} ", filteredPlaylist.get().getPlaylistDate());
                filteredPlaylist.get().getPlaylists().stream().sorted(Comparator.comparing(TraBlock::getSequence)).collect(toSet()).forEach(parsedFormExcelTraBlock -> {
                    if (parsedFormExcelTraBlock.getEmissions().stream().count() > 0) {
                        Set<TraBlock> entityFilteredByRangeBlockSet = entiyPlaylist.getPlaylists().stream().filter(entityTraBlock -> isInRange(parsedFormExcelTraBlock.getStartBlock(), entityTraBlock.getStartBlock(), parsedFormExcelTraBlock.getStopBlock())).collect(toSet());
                        if (isNotEmpty(entityFilteredByRangeBlockSet)) {
                            log.debug("Found Block matching to range ");
                            entityFilteredByRangeBlockSet.stream().forEach(filteredEntityTraBlock -> {
                                if (isNotEmpty(parsedFormExcelTraBlock.getEmissions())) {
                                    if (isNotEmpty(filteredEntityTraBlock.getEmissions())) {
                                        Long lastTimeStop = filteredEntityTraBlock.getEmissions().stream().max(Comparator.comparingLong(TraEmission::getTimeStop)).get().getTimeStop();
                                        Integer lastSequence = filteredEntityTraBlock.getEmissions().stream().max(Comparator.comparingLong(TraEmission::getSequence)).get().getSequence();
                                        if (TraAdvertisementShuffleService.canAddEmissionToBlock(lastTimeStop, filteredEntityTraBlock.getLength(), libMediaItem.getLength()) && hasNotFixedFirstPostion(filteredEntityTraBlock)) {
                                            filteredEntityTraBlock = reindexEmissions(reindexEmissions(filteredEntityTraBlock));
                                            log.debug("Put commercial into block");
                                            TraEmission emisssion = new TraEmission().sequence(0).block(filteredEntityTraBlock).firstPosition(true).fixedPosition(true).timeStart(lastTimeStop).timeStop(lastTimeStop + libMediaItem.getLength().longValue()).advertiment(libMediaItem).channel(filteredEntityTraBlock.getChannel()).network(filteredEntityTraBlock.getNetwork());
                                            filteredEntityTraBlock.addEmissions(emisssion);
                                            synchronized (lockObject) {
                                                parsedFormExcelTraBlock.getEmissions().remove(parsedFormExcelTraBlock.getEmissions().iterator().next());
                                            }
                                        } else {
                                            log.debug("Can't put commercial because block size excide maximum number of seconds or contains fixed First postion");
                                        }
                                    } else {
                                        log.debug("Block is empty");
                                        log.debug("Put commercial into block");
                                        Long lastTimeStop = 0L;
                                        TraEmission emisssion = new TraEmission().block(filteredEntityTraBlock).firstPosition(true).fixedPosition(true).timeStart(lastTimeStop).timeStop(lastTimeStop + libMediaItem.getLength().longValue()).advertiment(libMediaItem).sequence(0).channel(filteredEntityTraBlock.getChannel()).network(filteredEntityTraBlock.getNetwork());
                                        filteredEntityTraBlock.addEmissions(emisssion);
                                        synchronized (lockObject) {
                                            parsedFormExcelTraBlock.getEmissions().remove(parsedFormExcelTraBlock.getEmissions().iterator().next());
                                        }
                                    }
                                }
                            });
                        }
                    }
                });

            }
        });
        return new TraPlaylistDiff(traPlaylists, traPlaylistsExcel);
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

    private boolean isInRange(long parsedStartBlock, long entityStratBlock, long parsedEndBlock) {
        return (parsedStartBlock <= entityStratBlock && entityStratBlock <= parsedEndBlock);
    }

}
