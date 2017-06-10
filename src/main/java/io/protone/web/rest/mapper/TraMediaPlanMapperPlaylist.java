package io.protone.web.rest.mapper;

import io.protone.domain.*;
import io.protone.web.rest.dto.traffic.TraPlaylistDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
@Mapper(componentModel = "spring", uses = {TraBlockMapper.class})
public interface TraMediaPlanMapperPlaylist {


    TraPlaylist mediaPlanPlaylistToTraPlaylist(TraMediaPlanPlaylist traMediaPlanPlaylist);

    List<TraPlaylist> mediaPlanPlaylistToTraPlaylist(Set<TraMediaPlanPlaylist> traMediaPlanPlaylists);

    TraMediaPlanPlaylist traPlaylistToTraMediaPlanPlaylist(TraPlaylist traPlaylist);

    Set<TraMediaPlanPlaylist> traPlaylistsToTraMediaPlanPlaylists(List<TraPlaylist> playlists);

}
