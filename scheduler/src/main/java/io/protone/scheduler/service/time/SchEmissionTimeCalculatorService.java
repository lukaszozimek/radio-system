package io.protone.scheduler.service.time;

import io.protone.scheduler.api.dto.SchAttachmentDTO;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 07/09/2017.
 */
@Service
public class SchEmissionTimeCalculatorService {

    @Inject
    private SchAttachmentTimeCalculatorService schAttachmentTimeCalculatorService;

    public SchEmissionDTO calculateTimeInSchEmissionDTO(SchEmissionDTO schEmission, LocalDateTime endTime, SchPlaylistDTO schPlaylistDTO) {
        SchEmissionDTO schEmissionDTO =
                schEmission.startTime(endTime).endTime(endTime.plusSeconds(schEmission.getMediaItem().getLength() / 1000)).attachment(schEmission.getAttachment().stream().sorted(Comparator.comparing(SchAttachmentDTO::getSequence)).collect(Collectors.toList()));
        if (schPlaylistDTO != null) {
            schPlaylistDTO.addEmissionsItem(schEmissionDTO);
        }
        return schEmissionDTO;
    }
}
