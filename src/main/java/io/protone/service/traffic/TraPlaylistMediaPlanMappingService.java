package io.protone.service.traffic;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import io.protone.domain.*;
import io.protone.web.rest.mapper.TraMediaPlanMapperPlaylist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static io.protone.service.traffic.TraAdvertisementShuffleService.canAddEmissionToBlock;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

/**
 * Created by lukaszozimek on 12/06/2017.
 */
@Service
public class TraPlaylistMediaPlanMappingService {
    private final Object lockObject = new Object();
    private final Logger log = LoggerFactory.getLogger(TraPlaylistMediaPlanMappingService.class);

    @Autowired
    private TraMediaPlanService traMediaPlanService;

    @Autowired
    private TraPlaylistService traPlaylistService;

    @Autowired
    private TraAdvertisementService traAdvertisementService;
    @Autowired
    private TraMediaPlanMapperPlaylist traMediaPlanMapperPlaylistMapper;

    public TraPlaylistMediaPlanMappingService.PlaylistDiff mapMediaPlanEntriesToPlaylistWithSelectedAdvertisment(Long mediaPlanId, Long advertismentId, String networkShortcut, String channelShortcut) {
        TraAdvertisement traAdvertisement = traAdvertisementService.getAdvertisement(advertismentId, networkShortcut);
        TraMediaPlan traMediaPlan = traMediaPlanService.getMediaPlan(mediaPlanId, networkShortcut, channelShortcut);
        List<LocalDate> playListsDates = traMediaPlan.getPlaylists().stream().map(TraMediaPlanPlaylist::getPlaylistDate).sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(playListsDates.get(0), playListsDates.get(playListsDates.size() - 1).plusDays(1), networkShortcut, channelShortcut);
        List<TraPlaylist> mediaPlanPlaylists = traMediaPlanMapperPlaylistMapper.mediaPlanPlaylistsToTraPlaylists(traMediaPlan.getPlaylists());
        return mapToEntityPlaylist(entiyPlaylists, mediaPlanPlaylists, traAdvertisement);
    }

    @VisibleForTesting
    public TraPlaylistMediaPlanMappingService.PlaylistDiff mapToEntityPlaylist(List<TraPlaylist> entiyPlaylists, List<TraPlaylist> parsedFromMediaPlan, TraAdvertisement traAdvertisement) {
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
                                        if (canAddEmissionToBlock(lastTimeStop, filteredEntityTraBlock.getLength(), traAdvertisement.getMediaItem().getLength())) {
                                            TraEmission emisssion = new TraEmission().sequence(lastSequence + 1).block(filteredEntityTraBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + traAdvertisement.getMediaItem().getLength().longValue()).advertiment(traAdvertisement).channel(filteredEntityTraBlock.getChannel()).network(filteredEntityTraBlock.getNetwork());
                                            filteredEntityTraBlock.addEmissions(emisssion);
                                            synchronized (lockObject) {
                                                parsedFormExcelTraBlock.getEmissions().remove(parsedFormExcelTraBlock.getEmissions().iterator().next());
                                            }
                                        } else {
                                            log.debug("Can't put commercial because block size excide maximum number of seconds");
                                        }
                                    } else {
                                        log.debug("Block is empty");
                                        Long lastTimeStop = 0L;
                                        TraEmission emisssion = new TraEmission().block(filteredEntityTraBlock).timeStart(lastTimeStop).timeStop(lastTimeStop + traAdvertisement.getMediaItem().getLength().longValue()).advertiment(traAdvertisement).sequence(0).channel(filteredEntityTraBlock.getChannel()).network(filteredEntityTraBlock.getNetwork());
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
        return new TraPlaylistMediaPlanMappingService.PlaylistDiff(traPlaylists, traPlaylistsExcel);
    }

    private boolean isInRange(long parsedStartBlock, long entityStratBlock, long parsedEndBlock) {
        return (parsedStartBlock <= entityStratBlock && entityStratBlock <= parsedEndBlock);
    }

    public class PlaylistDiff {

        private List<TraPlaylist> entityPlaylist;

        private List<TraPlaylist> parsedFromExcel;

        public List<TraPlaylist> getEntityPlaylist() {
            return entityPlaylist;
        }

        public List<TraPlaylist> getParsedFromExcel() {
            return parsedFromExcel;
        }

        public PlaylistDiff(List<TraPlaylist> entityPlaylist, List<TraPlaylist> parsedFromExcel) {
            this.entityPlaylist = entityPlaylist;
            this.parsedFromExcel = parsedFromExcel;
        }
    }
}
