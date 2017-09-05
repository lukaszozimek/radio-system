package io.protone.scheduler.service.schedule.factory;

import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchEvent;
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

    public SchBlock blockFactoryMethod(SchEvent schEvent) {
        return new SchBlock().network(schEvent.getNetwork()).channel(schEvent.getChannel()).length(schEvent.getTimeParams().getLength()).emissions(schEventEmissionEmissionMapper.mapEventEmissionsToBlockEmissions(schEvent.getEmissions()));
    }

}
