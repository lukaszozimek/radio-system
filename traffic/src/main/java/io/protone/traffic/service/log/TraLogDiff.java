package io.protone.traffic.service.log;

import io.protone.traffic.domain.TraLogEmission;
import io.protone.traffic.domain.TraPlaylist;

import java.util.List;

/**
 * Created by lukaszozimek on 11/12/2017.
 */
public class TraLogDiff  {
    private List<TraPlaylist> entityPlaylist;

    private List<TraLogEmission> traLogEmissions;

    public TraLogDiff() {
    }

    public TraLogDiff(List<TraPlaylist> entityPlaylist, List<TraLogEmission> traLogEmissions) {
        this.entityPlaylist = entityPlaylist;
        this.traLogEmissions = traLogEmissions;
    }

    public List<TraPlaylist> getEntityPlaylist() {
        return entityPlaylist;
    }

    public List<TraLogEmission> getTraLogEmissions() {
        return traLogEmissions;
    }

    @Override
    public String toString() {
        return "PlaylistDiff{" +
                "entityPlaylist=" + entityPlaylist +
                ", traLogEmissions=" + traLogEmissions +
                '}';
    }
}
