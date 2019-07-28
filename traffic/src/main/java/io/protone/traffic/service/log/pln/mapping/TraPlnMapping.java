package io.protone.traffic.service.log.pln.mapping;

import io.protone.library.domain.LibMediaItem;
import io.protone.traffic.domain.TraLogEmission;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.service.log.TraLogDiff;

import java.util.List;

/**
 * Created by lukaszozimek on 31/07/2017.
 */
public interface TraPlnMapping {
    TraLogDiff mapToEntityPlaylist(List<TraPlaylist> entiyPlaylists, List<TraLogEmission> parsedEmissions, LibMediaItem libMediaItem);
}
