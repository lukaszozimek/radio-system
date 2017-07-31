package io.protone.traffic.service.mediaplan.mapping.impl;

import com.google.common.collect.Lists;
import io.protone.library.domain.LibMediaItem;
import io.protone.traffic.domain.TraBlock;
import io.protone.traffic.domain.TraEmission;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.service.mediaplan.diff.TraPlaylistDiff;
import io.protone.traffic.service.mediaplan.mapping.TraMediaPlanMapping;
import io.protone.traffic.service.shuffle.exception.TrafficShuffleReindexException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.protone.traffic.service.TraAdvertisementShuffleService.canAddEmissionToBlock;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

/**
 * Created by lukaszozimek on 31/07/2017.
 */
@Service("traDefaultMediaPlanMapping")
@Qualifier("traDefaultMediaPlanMapping")
public class TraDefaultMediaPlanMapping implements TraMediaPlanMapping {
    private final Object lockObject = new Object();
    private final Logger log = LoggerFactory.getLogger(TraDefaultMediaPlanMapping.class);

    @Override
    public TraPlaylistDiff mapToEntityPlaylist(List<TraPlaylist> entiyPlaylists, List<TraPlaylist> parsedFromMediaPlan, LibMediaItem libMediaItem){
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
                                        if (canAddEmissionToBlock(lastTimeStop, filteredEntityTraBlock.getLength(), libMediaItem.getLength()) && hasFixedLastPostion(filteredEntityTraBlock)) {
                                            try {
                                                filteredEntityTraBlock = reindexToFitLastPostionEmissions(filteredEntityTraBlock);
                                            } catch (TrafficShuffleReindexException e) {
                                                e.printStackTrace();
                                            }
                                            log.debug("Put commercial into block");
                                            TraEmission emisssion = new TraEmission().sequence(lastSequence + 1).block(filteredEntityTraBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + libMediaItem.getLength().longValue()).advertiment(libMediaItem).channel(filteredEntityTraBlock.getChannel()).network(filteredEntityTraBlock.getNetwork());
                                            filteredEntityTraBlock.addEmissions(emisssion);
                                            synchronized (lockObject) {
                                                parsedFormExcelTraBlock.getEmissions().remove(parsedFormExcelTraBlock.getEmissions().iterator().next());
                                            }

                                        } else if (canAddEmissionToBlock(lastTimeStop, filteredEntityTraBlock.getLength(), libMediaItem.getLength()) && !hasFixedLastPostion(filteredEntityTraBlock)) {

                                            log.debug("Put commercial into block");
                                            TraEmission emisssion = new TraEmission().sequence(lastSequence + 1).block(filteredEntityTraBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + libMediaItem.getLength().longValue()).advertiment(libMediaItem).channel(filteredEntityTraBlock.getChannel()).network(filteredEntityTraBlock.getNetwork());
                                            filteredEntityTraBlock.addEmissions(emisssion);
                                            synchronized (lockObject) {
                                                parsedFormExcelTraBlock.getEmissions().remove(parsedFormExcelTraBlock.getEmissions().iterator().next());
                                            }
                                        } else {
                                            log.debug("Can't put commercial because block size excide maximum number of seconds");
                                        }
                                    } else {
                                        log.debug("Block is empty");
                                        log.debug("Put commercial into block");
                                        Long lastTimeStop = 0L;
                                        TraEmission emisssion = new TraEmission().block(filteredEntityTraBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + libMediaItem.getLength().longValue()).advertiment(libMediaItem).sequence(0).channel(filteredEntityTraBlock.getChannel()).network(filteredEntityTraBlock.getNetwork());
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

    private boolean isInRange(long parsedStartBlock, long entityStratBlock, long parsedEndBlock) {
        return (parsedStartBlock <= entityStratBlock && entityStratBlock <= parsedEndBlock);
    }

}
