package io.protone.application.scheduler.service.schedule.mapper;

import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchEmissionTemplate;
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


    public SchEmission mapSchEmission(SchEmissionTemplate schEmissionTemplate, SchPlaylist schPlaylist) {
        return new SchEmission().seq(schEmissionTemplate.getSequence())
                .startTime(schEmissionTemplate.getStartTime())
                .playlist(null)
                .seq(schEmissionTemplate.getSequence())
                .endTime(schEmissionTemplate.getStartTime().plusSeconds(schEmissionTemplate.getMediaItem().getLength().longValue() / 1000))
                .attachments(schEmissionConfigurationAttachmentSchEmissionAttachmentMapper.mapAttachmentConfiguration(schEmissionTemplate.getAttachments()))
                .network(schEmissionTemplate.getNetwork())
                .channel(schEmissionTemplate.getChannel())
                .mediaItem(schEmissionTemplate.getMediaItem());


    }
}
