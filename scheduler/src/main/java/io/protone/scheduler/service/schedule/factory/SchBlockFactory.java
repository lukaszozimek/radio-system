package io.protone.scheduler.service.schedule.factory;

import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.service.schedule.mapper.SchEventEmissionEmissionMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchBlockFactory {
    @Inject
    private SchEventEmissionEmissionMapper schEventEmissionEmissionMapper;

    public SchBlock blockFactoryMethod(SchEvent schEvent, SchPlaylist schPlaylist) {
        SchBlock schBlock = new SchBlock()
                .eventType(schEvent.getEventType())
                .sequence(schEvent.getSequence())
                .network(schEvent.getNetwork())
                .channel(schEvent.getChannel())
                .length(schEvent.getLength());
        if (!schEvent.getEmissions().isEmpty()) {
            schBlock.emissions(schEventEmissionEmissionMapper.mapEventEmissionsToBlockEmissions(schEvent.getEmissions(),schPlaylist));
        } else {
            schBlock.emissions(schEvent.getEmissionsLog());
        }
        return schBlock;
    }

}
