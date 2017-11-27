package io.protone.application.scheduler.service.schedule.mapper;

import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchEmissionTemplate;
import io.protone.scheduler.domain.SchPlaylist;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchEventEmissionEmissionMapper {
    @Inject
    private SchEventEmissionAttachmentAttachmentMapper schEventEmissionAttachmentAttachmentMapper;

    @Transactional
    public SchEmission mapEventEmission(SchEmissionTemplate emission, SchPlaylist schPlaylist) {
        return new SchEmission().sequence(emission.getSequence())
                .startTime(emission.getStartTime())
                .playlist(schPlaylist)
                .endTime(emission.getStartTime().plusSeconds(emission.getMediaItem().getLength().longValue() / 1000))
                .attachments(schEventEmissionAttachmentAttachmentMapper.mapEventEmissionAttachmentConfiguration(emission.getAttachments()))
                .network(emission.getNetwork())
                .channel(emission.getChannel())
                .mediaItem(emission.getMediaItem());

    }

}
