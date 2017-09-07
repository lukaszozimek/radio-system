package io.protone.scheduler.service.time;

import com.beust.jcommander.internal.Lists;
import io.protone.scheduler.api.dto.SchBlockDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 07/09/2017.
 */
@Service
public class SchBlockDTOTimeCalculatorService {

    @Inject
    private SchEmissionTimeCalculatorService schEmissionTimeCalculatorService;

    public List<SchBlockDTO> calculateTimeInBlockDTO(List<SchBlockDTO> schBlockDTO) {

        if (schBlockDTO != null && !schBlockDTO.isEmpty()) {
            return schBlockDTO.stream().map(schBlock -> {
                if (schBlock.getBlocks() != null && !schBlock.getBlocks().isEmpty()) {
                    schBlock.blocks(this.calculateTimeInBlockDTO(schBlock.getBlocks()));
                }

                schBlock.emissions(schEmissionTimeCalculatorService.calculateTimeInSchEmissionDTO(schBlock.getEmissions()));
                return schBlock;
            }).collect(toList());
        }
        return Lists.newArrayList();
    }


}
