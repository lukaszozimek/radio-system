package io.protone.scheduler.service.time;

import com.google.common.collect.Lists;
import io.protone.scheduler.api.dto.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 07/09/2017.
 */
@Service
public class SchClockDTOTimeCalculatorService {

    @Inject
    private SchBlockDTOTimeCalculatorService schBlockDTOTimeCalculatorService;
    @Inject
    private SchEmissionTimeCalculatorService schEmissionTimeCalculatorService;

    public SchClockDTO calculateTimeInClockDTO(SchClockDTO schClockDTO, LocalDateTime endTime, SchPlaylistDTO schPlaylistDTO) {
        List<SchTimeParamsDTO> schTimeParamsDTOList = new ArrayList<>();
        schTimeParamsDTOList.addAll(schClockDTO.getBlocks());
        schTimeParamsDTOList.addAll(schClockDTO.getEmissions());
        schClockDTO.setBlocks(Lists.newArrayList());
        schClockDTO.setEmissions(Lists.newArrayList());
        schTimeParamsDTOList = schTimeParamsDTOList.stream().sorted(Comparator.comparing(SchTimeParamsDTO::getSequence)).collect(toList());
        for (int i = 0; i < schTimeParamsDTOList.size(); i++) {
            if (i == 0) {
                if (schTimeParamsDTOList.get(i) instanceof SchBlockDTO) {
                    schTimeParamsDTOList.get(i).setStartTime(endTime);
                    SchBlockDTO schBlockDTO = schBlockDTOTimeCalculatorService.calculateTimeInBlockDTO((SchBlockDTO) schTimeParamsDTOList.get(i), endTime, schPlaylistDTO);
                    schClockDTO.endTime(schBlockDTO.getEndTime());
                    schClockDTO.addBlocksItem(schBlockDTO);
                }
                if (schTimeParamsDTOList.get(i) instanceof SchEmissionDTO) {
                    schTimeParamsDTOList.get(i).setStartTime(endTime);
                    SchEmissionDTO emissionDTO = schEmissionTimeCalculatorService.calculateTimeInSchEmissionDTO((SchEmissionDTO) schTimeParamsDTOList.get(i), endTime, schPlaylistDTO);
                    schClockDTO.endTime(emissionDTO.getEndTime());
                    schClockDTO.addEmissionsItem(emissionDTO);
                }
            } else {
                if (schTimeParamsDTOList.get(i) instanceof SchBlockDTO) {
                    schTimeParamsDTOList.get(i).setStartTime(schTimeParamsDTOList.get(i - 1).getEndTime());
                    SchBlockDTO schBlockDTO = schBlockDTOTimeCalculatorService.calculateTimeInBlockDTO((SchBlockDTO) schTimeParamsDTOList.get(i), schClockDTO.getEndTime(), schPlaylistDTO);
                    schClockDTO.endTime(schBlockDTO.getEndTime());
                    schClockDTO.addBlocksItem(schBlockDTO);
                }
                if (schTimeParamsDTOList.get(i) instanceof SchEmissionDTO) {
                    schTimeParamsDTOList.get(i).setStartTime(schTimeParamsDTOList.get(i - 1).getEndTime());
                    SchEmissionDTO emissionDTO = schEmissionTimeCalculatorService.calculateTimeInSchEmissionDTO((SchEmissionDTO) schTimeParamsDTOList.get(i), schClockDTO.getEndTime(), schPlaylistDTO);
                    schClockDTO.endTime(emissionDTO.getEndTime());
                    schClockDTO.addEmissionsItem(emissionDTO);
                }
            }
        }

        return schClockDTO;
    }

}
