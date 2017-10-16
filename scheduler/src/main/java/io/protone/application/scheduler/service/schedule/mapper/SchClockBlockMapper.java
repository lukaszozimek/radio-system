package io.protone.application.scheduler.service.schedule.mapper;

import io.protone.scheduler.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchClockBlockMapper {
    @Inject
    private SchEventEmissionEmissionMapper schEventEmissionEmissionMapper;


    public SchBlock buildBlocks(SchEvent schEvent, SchPlaylist schPlaylist) {
        SchBlock schBlock = new SchBlock().startTime(schEvent.getStartTime()).sequence(schEvent.getSequence());
        List<SchTimeParams> schTimeParams = new ArrayList<>();
        schTimeParams.addAll(schEvent.getSchEvents());
        schTimeParams.addAll(schEvent.getEmissions());
        schTimeParams.addAll(schEvent.getEmissionsLog());
        schTimeParams = schTimeParams.stream().sorted(Comparator.comparing(SchTimeParams::getSequence)).collect(toList());
        for (int i = 0; i < schTimeParams.size(); i++) {
            if (i == 0) {
                if (schTimeParams.get(i) instanceof SchEvent) {
                    schTimeParams.get(i).setStartTime(schBlock.getStartTime());
                    SchBlock block = this.buildBlocks((SchEvent) schTimeParams.get(i), schPlaylist);
                    schTimeParams.get(i).setEndTime(block.getEndTime());
                    schBlock.endTime(block.getEndTime());
                    schBlock.addBlock(block);
                }
                if (schTimeParams.get(i) instanceof SchEventEmission) {
                    schTimeParams.get(i).setStartTime(schBlock.getStartTime());
                    SchEmission schEmission = schEventEmissionEmissionMapper.mapEventEmission((SchEventEmission) schTimeParams.get(i), schPlaylist);
                    schTimeParams.get(i).setEndTime(schEmission.getEndTime());
                    schBlock.endTime(schEmission.getEndTime());
                    schBlock.length((long) Duration.between(schBlock.getStartTime(), schBlock.getEndTime()).getNano());
                    schBlock.addEmission(schEmission);
                }

                if (schTimeParams.get(i) instanceof SchEmission) {
                    schTimeParams.get(i).setStartTime(schBlock.getStartTime());
                    SchEmission schEmission = (SchEmission) schTimeParams.get(i).startTime(schBlock.getStartTime()).endTime(schTimeParams.get(i).getStartTime().plusSeconds(((SchEmission) schTimeParams.get(i)).getMediaItem().getLength().longValue() / 1000));
                    schTimeParams.get(i).setEndTime(schEmission.getEndTime());
                    schBlock.endTime(schEmission.getEndTime());
                    schBlock.length((long) Duration.between(schBlock.getStartTime(), schBlock.getEndTime()).getNano());
                    schBlock.addEmission(schEmission);
                }
            } else {
                if (schTimeParams.get(i) instanceof SchEvent) {
                    schTimeParams.get(i).setStartTime(schTimeParams.get(i - 1).getEndTime());
                    SchBlock block = this.buildBlocks((SchEvent) schTimeParams.get(i), schPlaylist);
                    schTimeParams.get(i).setEndTime(block.getEndTime());
                    schBlock.endTime(block.getEndTime());
                    schBlock.addBlock(block);
                }
                if (schTimeParams.get(i) instanceof SchEventEmission) {
                    schTimeParams.get(i).setStartTime(schTimeParams.get(i - 1).getEndTime());
                    SchEmission schEmission = schEventEmissionEmissionMapper.mapEventEmission((SchEventEmission) schTimeParams.get(i), schPlaylist);
                    schBlock.endTime(schEmission.getEndTime());
                    schTimeParams.get(i).setEndTime(schEmission.getEndTime());
                    schBlock.length((long) Duration.between(schBlock.getStartTime(), schBlock.getEndTime()).getNano());
                    schBlock.addEmission(schEmission);
                }
                if (schTimeParams.get(i) instanceof SchEmission) {
                    schTimeParams.get(i).setStartTime(schTimeParams.get(i - 1).getEndTime());
                    SchEmission schEmission = (SchEmission) schTimeParams.get(i).startTime(schTimeParams.get(i).getStartTime()).endTime(schTimeParams.get(i).getStartTime().plusSeconds(((SchEmission) schTimeParams.get(i)).getMediaItem().getLength().longValue() / 1000));
                    schTimeParams.get(i).setEndTime(schEmission.getEndTime());
                    schBlock.endTime(schEmission.getEndTime());
                    schBlock.length((long) Duration.between(schBlock.getStartTime(), schBlock.getEndTime()).getNano());
                    schBlock.addEmission(schEmission);
                }
            }
        }
        if (schBlock.getEndTime() != null) {
            return schBlock.length((long) Duration.between(schBlock.getStartTime(), schBlock.getEndTime()).getNano()).network(schEvent.getNetwork()).channel(schEvent.getChannel());
        } else {
            return schBlock.length((long) Duration.between(schBlock.getStartTime(), schBlock.getStartTime()).getNano()).network(schEvent.getNetwork()).channel(schEvent.getChannel());
        }
    }


}
