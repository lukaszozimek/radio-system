package io.protone.scheduler.service.schedule.mapper;

import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchEventEmission;
import io.protone.scheduler.domain.SchPlaylist;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchEventEmissionEmissionMapper {
    @Inject
    private SchEventEmissionAttachmentAttachmentMapper schEventEmissionAttachmentAttachmentMapper;


    public SchEmission mapEventEmission(SchEventEmission emission, SchPlaylist schPlaylist) {
        return new SchEmission().seq(emission.getSequence())
                .startTime(emission.getStartTime())
                .seq(emission.getSequence())
                .playlist(schPlaylist)
                .endTime(emission.getStartTime().plusSeconds(emission.getMediaItem().getLength().longValue() / 1000))
                .attachments(schEventEmissionAttachmentAttachmentMapper.mapEventEmissionAttachmentConfiguration(emission.getAttachments()))
                .network(emission.getNetwork())
                .channel(emission.getChannel())
                .mediaItem(emission.getMediaItem());

    }

}