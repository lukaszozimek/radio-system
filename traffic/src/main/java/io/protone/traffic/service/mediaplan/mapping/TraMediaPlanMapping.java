package io.protone.traffic.service.mediaplan.mapping;

import io.protone.library.domain.LibMediaItem;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.service.mediaplan.diff.TraPlaylistDiff;

import java.util.List;

/**
 * Created by lukaszozimek on 31/07/2017.
 */
public interface TraMediaPlanMapping {
    TraPlaylistDiff mapToEntityPlaylist(List<TraPlaylist> entiyPlaylists, List<TraPlaylist> parsedFromMediaPlan, LibMediaItem libMediaItem);
}
