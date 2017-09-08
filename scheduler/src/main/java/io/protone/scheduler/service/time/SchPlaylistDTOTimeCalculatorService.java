package io.protone.scheduler.service.time;

import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.mapper.SchClockMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 07/09/2017.
 */
@Service
public class SchPlaylistDTOTimeCalculatorService {

    @Inject
    private SchClockMapper schClockMapper;

    @Inject
    private SchClockDTOTimeCalculatorService schClockDTOTimeCalculatorService;

    public SchPlaylistDTO calculateTimeInSchPlaylistDTO(SchPlaylist schPlaylist) {
        SchPlaylistDTO schPlaylistDTO = new SchPlaylistDTO().date(schPlaylist.getDate());
        List<SchClock> schClocks = schPlaylist.getEmissions().stream().filter(schEmission -> schEmission.getClock() != null)
                .map(schEmission -> schEmission.getClock())
                .sorted(Comparator.comparing(SchClock::getSequence))
                .collect(toList());
        List<SchClockDTO> schClockDTOS = schClockMapper.DBs2DTOs(schClocks);
        for (int i = 0; i < schClockDTOS.size(); i++) {
            if (i == 0) {
                schClockDTOS.get(i).setStartTime(LocalDateTime.of(schPlaylist.getDate(), LocalTime.of(0, 0, 0)));
                schClockDTOTimeCalculatorService.calculateTimeInClockDTO(schClockDTOS.get(i), schClockDTOS.get(i).getStartTime(), schPlaylistDTO);

            } else {
                schClockDTOS.get(i).setStartTime(schClockDTOS.get(i - 1).getEndTime());
                schClockDTOTimeCalculatorService.calculateTimeInClockDTO(schClockDTOS.get(i), schClockDTOS.get(i).getStartTime(), schPlaylistDTO);
            }
        }
        return schPlaylistDTO.emissions(schPlaylistDTO.getEmissions().stream().sorted(Comparator.comparing(SchEmissionDTO::getStartTime)).collect(toList()));
    }


}
