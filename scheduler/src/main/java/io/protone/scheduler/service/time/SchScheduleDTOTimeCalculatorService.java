package io.protone.scheduler.service.time;

import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.api.dto.SchScheduleDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 07/09/2017.
 */
@Service
public class SchScheduleDTOTimeCalculatorService {

    @Inject
    private SchClockDTOTimeCalculatorService schClockDTOTimeCalculatorService;


    public SchScheduleDTO calculateTimeInSchPlaylistDTO(SchScheduleDTO schScheduleDTO) {
        if (schScheduleDTO.getSchClockDTOS() != null && !schScheduleDTO.getSchClockDTOS().isEmpty()) {
            for (int i = 0; i < schScheduleDTO.getSchClockDTOS().size(); i++) {
                if (i == 0) {
                    schScheduleDTO.getSchClockDTOS().get(i).setStartTime(LocalDateTime.of(schScheduleDTO.getDate(), LocalTime.of(0, 0, 0)));
                    schClockDTOTimeCalculatorService.calculateTimeInClockDTO(schScheduleDTO.getSchClockDTOS().get(i), schScheduleDTO.getSchClockDTOS().get(i).getStartTime(), null);

                } else {
                    schScheduleDTO.getSchClockDTOS().get(i).setStartTime(schScheduleDTO.getSchClockDTOS().get(i - 1).getEndTime());
                    schClockDTOTimeCalculatorService.calculateTimeInClockDTO(schScheduleDTO.getSchClockDTOS().get(i), schScheduleDTO.getSchClockDTOS().get(i).getStartTime(), null);
                }
            }
            schScheduleDTO.setSchClockDTOS(schScheduleDTO.getSchClockDTOS().stream().sorted(Comparator.comparing(SchClockDTO::getSequence)).collect(Collectors.toList()));
        }
        return schScheduleDTO;
    }
}
