package io.protone.scheduler.service.time;

import com.beust.jcommander.internal.Lists;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 07/09/2017.
 */
@Service
public class SchEmissionTimeCalculatorService {

    @Inject
    private SchAttachmentTimeCalculatorService schAttachmentTimeCalculatorService;

    public List<SchEmissionDTO> calculateTimeInSchEmissionDTO(List<SchEmissionDTO> schEmissionDTOS) {
        if (schEmissionDTOS != null && !schEmissionDTOS.isEmpty()) {
            return schEmissionDTOS.stream().map(schEmission -> {
                return schEmission.attachment(schAttachmentTimeCalculatorService.calculateTimeInSchAttachmentDTO(schEmission.getAttachment()));
            }).collect(toList());
        }
        return Lists.newArrayList();
    }


}
