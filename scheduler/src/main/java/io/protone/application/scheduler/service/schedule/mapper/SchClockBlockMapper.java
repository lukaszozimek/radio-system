package io.protone.application.scheduler.service.schedule.mapper;

import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.service.LibLibraryMediaService;
import io.protone.library.service.LibMediaItemService;
import io.protone.scheduler.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchClockBlockMapper {
    @Inject
    private SchEmissionConfigurationSchEmissionMapper schEventEmissionEmissionMapper;

    @Inject
    private LibLibraryMediaService libraryMediaService;
    @Inject
    private LibMediaItemService libMediaItemService;

    @Transactional
    public SchBlock buildBlocks(SchEventTemplate schEventTemplate, SchPlaylist schPlaylist) {
        SchBlock schBlock = new SchBlock().eventType(schEventTemplate.getEventType()).startTime(schEventTemplate.getStartTime()).name(schEventTemplate.getName()).sequence(schEventTemplate.getSequence());
        List<SchTimeParams> schTimeParams = new ArrayList<>();
        schTimeParams.addAll(schEventTemplate.getChilds());
        schTimeParams.addAll(schEventTemplate.getEmissions());
        schTimeParams.addAll(schEventTemplate.getEmissionsLog());
        for (int i = 0; i < schTimeParams.size(); i++) {
            if (i == 0) {
                if (schTimeParams.get(i) instanceof SchEventTemplate) {
                    schTimeParams.get(i).setStartTime(schBlock.getStartTime());
                    SchBlock block = this.buildBlocks((SchEventTemplate) schTimeParams.get(i), schPlaylist);
                    schTimeParams.get(i).setEndTime(block.getEndTime());
                    schBlock.endTime(block.getEndTime());
                    schBlock.addBlock(new SchBlockSchBlock().parent(schBlock).child(block).sequence(schTimeParams.get(i).getSequence()));
                }
                if (schTimeParams.get(i) instanceof SchEmissionTemplate) {
                    schTimeParams.get(i).setStartTime(schBlock.getStartTime());
                    SchEmission schEmission = schEventEmissionEmissionMapper.mapSchEmission((SchEmissionTemplate) schTimeParams.get(i), schPlaylist).sequence(schTimeParams.get(i).getSequence());
                    schTimeParams.get(i).setEndTime(schEmission.getEndTime());
                    schBlock.endTime(schEmission.getEndTime());
                    schBlock.length((long) Duration.between(schBlock.getStartTime(), schBlock.getEndTime()).getNano());
                    schBlock.addEmission(schEmission);
                }

                if (schTimeParams.get(i) instanceof SchEmission) {
                    schTimeParams.get(i).setStartTime(schBlock.getStartTime());
                    SchEmission schEmission = (SchEmission) ((SchEmission) schTimeParams.get(i)).sequence(schTimeParams.get(i).getSequence()).startTime(schBlock.getStartTime()).endTime(schTimeParams.get(i).getStartTime().plusSeconds(((SchEmission) schTimeParams.get(i)).getMediaItem().getLength().longValue() / 1000));
                    schTimeParams.get(i).setEndTime(schEmission.getEndTime());
                    schBlock.endTime(schEmission.getEndTime());
                    schBlock.length((long) Duration.between(schBlock.getStartTime(), schBlock.getEndTime()).getNano());
                    LibMediaLibrary libMediaLibrary = libraryMediaService.findLibrary(schEmission.getNetwork().getShortcut(), schEmission.getLibraryElementShortCut());
                    if (libMediaLibrary != null) {
                        LibMediaItem libMediaItem = libMediaItemService.getMediaItem(schEmission.getNetwork().getShortcut(), schEmission.getLibraryElementShortCut(), schEmission.getMediaItem().getIdx());
                        if (libMediaItem != null) {
                            schEmission.mediaItem(libMediaItem);
                        } else {
                            schEmission.network(schEmission.getNetwork()).getMediaItem().library(libMediaLibrary).network(schEmission.getNetwork());
                        }
                        schBlock.addEmission(schEmission.sequence((long) i));
                    }
                }
            } else {
                if (schTimeParams.get(i) instanceof SchEventTemplate) {
                    schTimeParams.get(i).setStartTime(schTimeParams.get(i - 1).getEndTime());
                    SchBlock block = this.buildBlocks((SchEventTemplate) schTimeParams.get(i), schPlaylist);
                    schTimeParams.get(i).setEndTime(block.getEndTime());
                    schBlock.endTime(block.getEndTime());
                    schBlock.addBlock(new SchBlockSchBlock().parent(schBlock).child(block).sequence(schTimeParams.get(i).getSequence()));
                }
                if (schTimeParams.get(i) instanceof SchEmissionTemplate) {
                    schTimeParams.get(i).setStartTime(schTimeParams.get(i - 1).getEndTime());
                    SchEmission schEmission = schEventEmissionEmissionMapper.mapSchEmission((SchEmissionTemplate) schTimeParams.get(i), schPlaylist).sequence(schTimeParams.get(i).getSequence());
                    schBlock.endTime(schEmission.getEndTime());
                    schTimeParams.get(i).setEndTime(schEmission.getEndTime());
                    schBlock.length((long) Duration.between(schBlock.getStartTime(), schBlock.getEndTime()).getNano());
                    schBlock.addEmission(schEmission);
                }
                if (schTimeParams.get(i) instanceof SchEmission) {
                    schTimeParams.get(i).setStartTime(schTimeParams.get(i - 1).getEndTime());
                    SchEmission schEmission =  ((SchEmission) schTimeParams.get(i)).sequence(schTimeParams.get(i).getSequence()).startTime(schTimeParams.get(i).getStartTime()).endTime(schTimeParams.get(i).getStartTime().plusSeconds(((SchEmission) schTimeParams.get(i)).getMediaItem().getLength().longValue() / 1000));
                    schTimeParams.get(i).setEndTime(schEmission.getEndTime());
                    schBlock.endTime(schEmission.getEndTime());
                    schBlock.length((long) Duration.between(schBlock.getStartTime(), schBlock.getEndTime()).getNano());

                    LibMediaLibrary libMediaLibrary = libraryMediaService.findLibrary(schEmission.getNetwork().getShortcut(), schEmission.getLibraryElementShortCut());
                    if (libMediaLibrary != null) {
                        LibMediaItem libMediaItem = libMediaItemService.getMediaItem(schEmission.getNetwork().getShortcut(), schEmission.getLibraryElementShortCut(), schEmission.getMediaItem().getIdx());
                        if (libMediaItem != null) {
                            schEmission.mediaItem(libMediaItem);
                        } else {
                            schEmission.network(schEmission.getNetwork()).getMediaItem().library(libMediaLibrary).network(schEmission.getNetwork());
                        }
                        schBlock.addEmission(schEmission.sequence((long) i));
                    }
                }
            }
        }
        if (schBlock.getEndTime() != null) {
            return schBlock.length((long) Duration.between(schBlock.getStartTime(), schBlock.getEndTime()).getNano()).network(schEventTemplate.getNetwork()).channel(schEventTemplate.getChannel());
        } else {
            return schBlock.length((long) Duration.between(schBlock.getStartTime(), schBlock.getStartTime()).getNano()).network(schEventTemplate.getNetwork()).channel(schEventTemplate.getChannel());
        }
    }


}
