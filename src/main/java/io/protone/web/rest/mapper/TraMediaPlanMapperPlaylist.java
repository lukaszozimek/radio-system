package io.protone.web.rest.mapper;

import io.protone.domain.TraMediaPlanPlaylist;
import io.protone.domain.TraPlaylist;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
@Mapper(componentModel = "spring", uses = {TraBlockMapper.class})
public interface TraMediaPlanMapperPlaylist {


    TraPlaylist mediaPlanPlaylistToTraPlaylist(TraMediaPlanPlaylist traMediaPlanPlaylist);

    List<TraPlaylist> mediaPlanPlaylistsToTraPlaylists(Set<TraMediaPlanPlaylist> traMediaPlanPlaylists);

    TraMediaPlanPlaylist traPlaylistToTraMediaPlanPlaylist(TraPlaylist traPlaylist);

    Set<TraMediaPlanPlaylist> traPlaylistsToTraMediaPlanPlaylists(List<TraPlaylist> playlists);

}
