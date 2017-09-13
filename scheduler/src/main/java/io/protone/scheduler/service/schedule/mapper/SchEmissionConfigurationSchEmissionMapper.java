package io.protone.scheduler.service.schedule.mapper;

import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchEmissionConfiguration;
import io.protone.scheduler.domain.SchPlaylist;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchEmissionConfigurationSchEmissionMapper {
    @Inject
    private SchEmissionConfigurationAttachmentSchEmissionAttachmentMapper schEmissionConfigurationAttachmentSchEmissionAttachmentMapper;


    public SchEmission mapSchEmission(SchEmissionConfiguration schEmissionConfiguration, SchPlaylist schPlaylist) {
        return new SchEmission().seq(schEmissionConfiguration.getSequence())
                .startTime(schEmissionConfiguration.getStartTime())
                .playlist(schPlaylist)
                .seq(schEmissionConfiguration.getSequence())
                .endTime(schEmissionConfiguration.getStartTime().plusNanos(schEmissionConfiguration.getMediaItem().getLength().longValue()))
                .attachments(schEmissionConfigurationAttachmentSchEmissionAttachmentMapper.mapAttachmentConfiguration(schEmissionConfiguration.getAttachments()))
                .network(schEmissionConfiguration.getNetwork())
                .channel(schEmissionConfiguration.getChannel())
                .mediaItem(schEmissionConfiguration.getMediaItem());


    }
}
