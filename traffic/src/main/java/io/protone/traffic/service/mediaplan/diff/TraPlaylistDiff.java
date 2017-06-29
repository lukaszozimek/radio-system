package io.protone.traffic.service.mediaplan.diff;


import io.protone.traffic.domain.TraPlaylist;

import java.util.List;

/**
 * Created by lukaszozimek on 12/06/2017.
 */
public class TraPlaylistDiff {
    private List<TraPlaylist> entityPlaylist;

    private List<TraPlaylist> parsedFromExcel;

    public TraPlaylistDiff() {
    }

    public TraPlaylistDiff(List<TraPlaylist> entityPlaylist, List<TraPlaylist> parsedFromExcel) {
        this.entityPlaylist = entityPlaylist;
        this.parsedFromExcel = parsedFromExcel;
    }

    public List<TraPlaylist> getEntityPlaylist() {
        return entityPlaylist;
    }

    public List<TraPlaylist> getParsedFromExcel() {
        return parsedFromExcel;
    }

    @Override
    public String toString() {
        return "PlaylistDiff{" +
            "entityPlaylist=" + entityPlaylist +
            ", parsedFromExcel=" + parsedFromExcel +
            '}';
    }
}
