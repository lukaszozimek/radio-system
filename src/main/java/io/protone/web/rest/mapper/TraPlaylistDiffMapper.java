package io.protone.web.rest.mapper;

import io.protone.service.traffic.mediaplan.diff.TraPlaylistDiff;
import io.protone.web.rest.dto.traffic.TraPlaylistDiffDTO;
import org.mapstruct.Mapper;
/**
 * Created by lukaszozimek on 10/06/2017.
 */
@Mapper(componentModel = "spring", uses = {TraPlaylistMapper.class})
public interface TraPlaylistDiffMapper {

    TraPlaylistDiffDTO DB2DTO(TraPlaylistDiff traPlaylistDiff);

}
