package io.protone.scheduler.service.time;

import io.protone.scheduler.api.dto.SchClockDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 07/09/2017.
 */
@Service
public class SchClockDTOTimeCalculatorService {

    @Inject
    private SchBlockDTOTimeCalculatorService schBlockDTOTimeCalculatorService;
    @Inject
    private SchEmissionTimeCalculatorService schEmissionTimeCalculatorService;

    public SchClockDTO calculateTimeInClockDTO(SchClockDTO schClockDTO) {
        schBlockDTOTimeCalculatorService.calculateTimeInBlockDTO(schClockDTO.getBlocks());
        schEmissionTimeCalculatorService.calculateTimeInSchEmissionDTO(schClockDTO.getEmissions());
        return schClockDTO;
    }
}
