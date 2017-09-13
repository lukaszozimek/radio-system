package io.protone.scheduler.service.time;

import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.mapper.SchEmissionMapper;
import io.protone.scheduler.mapper.SchPlaylistMapper;
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
    private SchPlaylistMapper schPlaylistMapper;
    @Inject
    private SchEmissionMapper schEmissionMapper;

    public SchPlaylistDTO calculateTimeInSchPlaylistDTO(SchPlaylist schPlaylist) {
        SchPlaylistDTO schPlaylistDTO = new SchPlaylistDTO().date(schPlaylist.getDate()).id(schPlaylist.getId());
        List<SchEmission> schEmissionList = schPlaylist.getEmissions().stream().sorted(Comparator.comparing(SchEmission::getStartTime)).collect(toList());
        for (int i = 0; i < schEmissionList.size(); i++) {
            if (i == 0) {
                schEmissionList.get(0).startTime(LocalDateTime.of(schPlaylist.getDate(), LocalTime.of(0, 0, 0)));
                schPlaylistDTO.addEmissionsItem(schEmissionMapper.DB2DTO(schEmissionList.get(0).endTime(schEmissionList.get(0).getStartTime().plusNanos(schEmissionList.get(0).getMediaItem().getLength().longValue()))));
            } else {
                schEmissionList.get(i).startTime(schEmissionList.get(i - 1).getEndTime());
                schPlaylistDTO.addEmissionsItem(schEmissionMapper.DB2DTO(schEmissionList.get(i).startTime(schEmissionList.get(i).getStartTime().plusNanos(schEmissionList.get(i).getMediaItem().getLength().longValue()))));
            }
        }
        return schPlaylistDTO;
    }


}
