package io.protone.scheduler.service.schedule.mapper;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchEventEmission;
import io.protone.scheduler.domain.SchPlaylist;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchEventEmissionEmissionMapper {
    @Inject
    private SchEventEmissionAttachmentAttachmentMapper schEventEmissionAttachmentAttachmentMapper;

    public Set<SchEmission> mapEventEmissionsToBlockEmissions(Set<SchEventEmission> emissions, SchPlaylist schPlaylist) {
        if (emissions != null) {
            return emissions.stream().map(schEmissionConfiguration -> new SchEmission()
                    .mediaItem(schEmissionConfiguration.getMediaItem())
                    .seq(schEmissionConfiguration.getSequence())
                    .playlist(schPlaylist)
                    .channel(schEmissionConfiguration.getChannel())
                    .attachments(schEventEmissionAttachmentAttachmentMapper.mapEventEmissionAttachmentConfiguration(schEmissionConfiguration.getAttachments()))
                    .network(schEmissionConfiguration.getNetwork())).collect(toSet());
        }
        return Sets.newHashSet();
    }

}
