package io.protone.scheduler.service.time;

import com.google.common.collect.Lists;
import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.api.dto.SchTimeParamsDTO;
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
public class SchBlockDTOTimeCalculatorService {

    @Inject
    private SchEmissionTimeCalculatorService schEmissionTimeCalculatorService;

    public SchBlockDTO calculateTimeInBlockDTO(SchBlockDTO schBlockDTO, LocalDateTime endTime, SchPlaylistDTO schPlaylistDTO) {
        List<SchTimeParamsDTO> schTimeParamsDTOList = new ArrayList<>();
        schTimeParamsDTOList.addAll(schBlockDTO.getBlocks());
        schTimeParamsDTOList.addAll(schBlockDTO.getEmissions());
        schBlockDTO.setBlocks(Lists.newArrayList());
        schBlockDTO.setEmissions(Lists.newArrayList());
        schTimeParamsDTOList = schTimeParamsDTOList.stream().sorted(Comparator.comparing(SchTimeParamsDTO::getSequence)).collect(toList());
        for (int i = 0; i < schTimeParamsDTOList.size(); i++) {
            if (i == 0) {
                if (schTimeParamsDTOList.get(i) instanceof SchBlockDTO) {
                    schTimeParamsDTOList.get(i).setStartTime(endTime);
                    SchBlockDTO block = this.calculateTimeInBlockDTO((SchBlockDTO) schTimeParamsDTOList.get(i), endTime, schPlaylistDTO);
                    schBlockDTO.endTime(block.getEndTime());
                    schBlockDTO.addBlocksItem(block);
                }
                if (schTimeParamsDTOList.get(i) instanceof SchEmissionDTO) {

                    schTimeParamsDTOList.get(i).setStartTime(endTime);
                    SchEmissionDTO emissionDTO = schEmissionTimeCalculatorService.calculateTimeInSchEmissionDTO((SchEmissionDTO) schTimeParamsDTOList.get(i), endTime, schPlaylistDTO);
                    schBlockDTO.endTime(emissionDTO.getEndTime());
                    schBlockDTO.addEmissionsItem(emissionDTO);
                }
            } else {
                if (schTimeParamsDTOList.get(i) instanceof SchBlockDTO) {
                    schTimeParamsDTOList.get(i).setStartTime(schTimeParamsDTOList.get(i - 1).getEndTime());
                    SchBlockDTO block = this.calculateTimeInBlockDTO((SchBlockDTO) schTimeParamsDTOList.get(i), schBlockDTO.getEndTime(), schPlaylistDTO);
                    schBlockDTO.endTime(block.getEndTime());
                    schBlockDTO.addBlocksItem(block);
                }
                if (schTimeParamsDTOList.get(i) instanceof SchEmissionDTO) {
                    schTimeParamsDTOList.get(i).setStartTime(schTimeParamsDTOList.get(i - 1).getEndTime());
                    SchEmissionDTO emissionDTO = schEmissionTimeCalculatorService.calculateTimeInSchEmissionDTO((SchEmissionDTO) schTimeParamsDTOList.get(i), schBlockDTO.getEndTime(), schPlaylistDTO);
                    schBlockDTO.endTime(emissionDTO.getEndTime());
                    schBlockDTO.addEmissionsItem(emissionDTO);
                }
            }
        }
        return schBlockDTO;
    }

}
