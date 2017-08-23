package io.protone.traffic.service.mediaplan.mapping.impl;

import com.google.common.collect.Lists;
import io.protone.library.domain.LibMediaItem;
import io.protone.traffic.domain.TraBlock;
import io.protone.traffic.domain.TraEmission;
import io.protone.traffic.domain.TraMediaPlanEmission;
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
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

/**
 * Created by lukaszozimek on 31/07/2017.
 */
@Service("traDefaultMediaPlanMapping")
@Qualifier("traDefaultMediaPlanMapping")
public class TraDefaultMediaPlanMapping implements TraMediaPlanMapping {
    private final Object lockObject = new Object();
    private final Logger log = LoggerFactory.getLogger(TraDefaultMediaPlanMapping.class);

    @Override
    public TraPlaylistDiff mapToEntityPlaylist(List<TraPlaylist> entiyPlaylists, List<TraMediaPlanEmission> parsedEmissions, LibMediaItem libMediaItem) {
        log.debug("Start mapping entity Playlist with parsed Playlists");
        List<TraPlaylist> traPlaylists = entiyPlaylists;
        List<TraMediaPlanEmission> excelEmissions = Lists.newArrayList(parsedEmissions.iterator());
        for (TraMediaPlanEmission traMediaPlanEmission : parsedEmissions) {
            Optional<TraPlaylist> filteredTraPlaylist = traPlaylists.stream().filter(traPlaylist -> traMediaPlanEmission.getMediaPlanPlaylistDate().getPlaylistDate().equals(traPlaylist.getPlaylistDate())).findFirst();
            if (filteredTraPlaylist.isPresent()) {
                log.debug("Found Playlist matching to Excel Playlist", filteredTraPlaylist);
                Set<TraBlock> traBlockSet = filteredTraPlaylist.get().getPlaylists().stream().sorted(Comparator.comparing(TraBlock::getSequence)).collect(toSet());
                for (TraBlock playlistBlock : traBlockSet) {
                    if (isInRange( traMediaPlanEmission.getMediaPlanBlock().getStartBlock(),playlistBlock.getStartBlock(), traMediaPlanEmission.getMediaPlanBlock().getStopBlock())) {
                        log.debug("Found Block matching to range ");
                        if (isNotEmpty(playlistBlock.getEmissions())) {
                            if (!playlistBlock.getEmissions().stream().filter(entityEmission -> entityEmission.getAdvertiment().getId().equals(traMediaPlanEmission.getAdvertiment().getId())).findFirst().isPresent()) {
                                Long lastTimeStop = playlistBlock.getEmissions().stream().max(Comparator.comparingLong(TraEmission::getTimeStop)).get().getTimeStop();
                                Integer lastSequence = playlistBlock.getEmissions().stream().max(Comparator.comparingLong(TraEmission::getSequence)).get().getSequence();
                                if (canAddEmissionToBlock(lastTimeStop, playlistBlock.getLength(), libMediaItem.getLength()) && hasFixedLastPostion(playlistBlock)) {
                                    try {
                                        playlistBlock = reindexToFitLastPostionEmissions(playlistBlock);
                                    } catch (TrafficShuffleReindexException e) {
                                        log.error("Reindexing problem");
                                    }
                                    log.debug("Put commercial into block");
                                    TraEmission emisssion = new TraEmission().sequence(lastSequence + 1).block(playlistBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + libMediaItem.getLength().longValue()).advertiment(libMediaItem).channel(playlistBlock.getChannel()).network(playlistBlock.getNetwork());
                                    playlistBlock.addEmissions(emisssion);
                                    excelEmissions.remove(traMediaPlanEmission);
                                    break;


                                } else if (canAddEmissionToBlock(lastTimeStop, playlistBlock.getLength(), libMediaItem.getLength()) && !hasFixedLastPostion(playlistBlock)) {

                                    log.debug("Put commercial into block");
                                    TraEmission emisssion = new TraEmission().sequence(lastSequence + 1).block(playlistBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + libMediaItem.getLength().longValue()).advertiment(libMediaItem).channel(playlistBlock.getChannel()).network(playlistBlock.getNetwork());
                                    playlistBlock.addEmissions(emisssion);
                                    excelEmissions.remove(traMediaPlanEmission);
                                    break;
                                } else {
                                    log.debug("Can't put commercial because block size excide maximum number of seconds");
                                }
                            } else {
                                log.debug("Can't put commercial because it is in a block");
                            }

                        } else {
                            log.debug("Block is empty");
                            log.debug("Put commercial into block");
                            Long lastTimeStop = 0L;
                            TraEmission emisssion = new TraEmission().block(playlistBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + libMediaItem.getLength().longValue()).advertiment(libMediaItem).sequence(0).channel(playlistBlock.getChannel()).network(playlistBlock.getNetwork());
                            playlistBlock.addEmissions(emisssion);
                            excelEmissions.remove(traMediaPlanEmission);
                            break;

                        }
                    } else {

                        log.debug("Block Not in range ");
                    }
                }
            } else {
                log.debug("Playlist Not found ");
            }
        }
        return new TraPlaylistDiff(traPlaylists, excelEmissions);
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
