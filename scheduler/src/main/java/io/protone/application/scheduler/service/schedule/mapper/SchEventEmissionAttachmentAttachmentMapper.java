package io.protone.application.scheduler.service.schedule.mapper;

import com.google.common.collect.Lists;
import io.protone.scheduler.domain.SchAttachment;
import io.protone.scheduler.domain.SchAttachmentTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchEventEmissionAttachmentAttachmentMapper {
    public List<SchAttachment> mapEventEmissionAttachmentConfiguration(List<SchAttachmentTemplate> attachments) {
        if (attachments == null || attachments.isEmpty()) {
            return Lists.newArrayList();
        }
        return attachments.stream().map(attachment -> new SchAttachment()
                .attachmentType(attachment.getAttachmentType())
                .channel(attachment.getChannel())
                .sequence(attachment.getSequence())
                .network(attachment.getNetwork())
                .fadeInLength(attachment.getFadeInLength())
                .fadeOutLength(attachment.getFadeOutLength())
                .fadeStart(attachment.getFadeStart())
                .fadeType(attachment.getFadeType())
                .mediaItem(attachment.getMediaItem())
                .volumeLevel(attachment.getVolumeLevel())).collect(toList());
    }
}
