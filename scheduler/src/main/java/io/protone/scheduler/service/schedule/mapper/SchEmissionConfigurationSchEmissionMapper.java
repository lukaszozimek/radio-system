package io.protone.scheduler.service.schedule.mapper;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchEmissionConfiguration;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchEmissionConfigurationSchEmissionMapper {
    @Inject
    private SchEmissionConfigurationAttachmentSchEmissionAttachmentMapper schEmissionConfigurationAttachmentSchEmissionAttachmentMapper;

    public Set<SchEmission> mapEmissionConfigurationToEmission(Set<SchEmissionConfiguration> schEmissions) {
        if (schEmissions != null) {
            return schEmissions.stream().map(schEmissionConfiguration -> new SchEmission()
                    .mediaItem(schEmissionConfiguration.getMediaItem())
                    .seq(schEmissionConfiguration.getSequence())
                    .channel(schEmissionConfiguration.getChannel())
                    .attachments(schEmissionConfigurationAttachmentSchEmissionAttachmentMapper.mapAttachmentConfiguration(schEmissionConfiguration.getAttachments()))
                    .network(schEmissionConfiguration.getNetwork())).collect(toSet());

        }
        return Sets.newHashSet();
    }
}
