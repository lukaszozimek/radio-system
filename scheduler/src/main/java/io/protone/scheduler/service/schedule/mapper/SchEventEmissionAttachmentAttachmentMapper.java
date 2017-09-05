package io.protone.scheduler.service.schedule.mapper;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchAttachment;
import io.protone.scheduler.domain.SchEventEmissionAttachment;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchEventEmissionAttachmentAttachmentMapper {
    public Set<SchAttachment> mapEventEmissionAttachmentConfiguration(Set<SchEventEmissionAttachment> attachments) {
        if (attachments != null) {
            return attachments.stream().map(attachment -> new SchAttachment()
                    .attachmentType(attachment.getAttachmentType())
                    .channel(attachment.getChannel())
                    .network(attachment.getNetwork())
                    .fadeInLength(attachment.getFadeInLength())
                    .fadeOutLength(attachment.getFadeOutLength())
                    .fadeStart(attachment.getFadeStart())
                    .fadeType(attachment.getFadeType())
                    .mediaItem(attachment.getMediaItem())
                    .volumeLevel(attachment.getVolumeLevel())).collect(toSet());
        }
        return Sets.newHashSet();
    }
}
