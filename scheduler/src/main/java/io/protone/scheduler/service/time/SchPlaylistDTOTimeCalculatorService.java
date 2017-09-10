package io.protone.scheduler.service.time;

import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.mapper.SchBlockMapper;
import io.protone.scheduler.mapper.SchClockMapper;
import io.protone.scheduler.mapper.SchScheduleMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 07/09/2017.
 */
@Service
public class SchPlaylistDTOTimeCalculatorService {

    @Inject
    private SchScheduleMapper schScheduleMapper;

    @Inject
    private SchClockDTOTimeCalculatorService schClockDTOTimeCalculatorService;

    public SchPlaylistDTO calculateTimeInSchPlaylistDTO(SchSchedule schSchedule) {
        SchPlaylistDTO schPlaylistDTO = new SchPlaylistDTO().date(schSchedule.getDate());
        SchScheduleDTO schScheduleDTO = schScheduleMapper.DB2DTO(schSchedule);
        if (schScheduleDTO.getSchClockDTOS() != null && !schScheduleDTO.getSchClockDTOS().isEmpty()) {
            for (int i = 0; i < schScheduleDTO.getSchClockDTOS().size(); i++) {
                if (i == 0) {
                    schScheduleDTO.getSchClockDTOS().get(i).setStartTime(LocalDateTime.of(schScheduleDTO.getDate(), LocalTime.of(0, 0, 0)));
                    schClockDTOTimeCalculatorService.calculateTimeInClockDTO(schScheduleDTO.getSchClockDTOS().get(i), schScheduleDTO.getSchClockDTOS().get(i).getStartTime(), schPlaylistDTO);

                } else {
                    schScheduleDTO.getSchClockDTOS().get(i).setStartTime(schScheduleDTO.getSchClockDTOS().get(i - 1).getEndTime());
                    schClockDTOTimeCalculatorService.calculateTimeInClockDTO(schScheduleDTO.getSchClockDTOS().get(i), schScheduleDTO.getSchClockDTOS().get(i).getStartTime(), schPlaylistDTO);
                }
            }
        }
        return schPlaylistDTO.emissions(schPlaylistDTO.getEmissions().stream().sorted(Comparator.comparing(SchEmissionDTO::getStartTime)).collect(toList()));
    }


}
