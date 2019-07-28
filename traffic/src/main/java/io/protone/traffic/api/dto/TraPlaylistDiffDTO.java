package io.protone.traffic.api.dto;

import java.util.List;

/**
 * Created by lukaszozimek on 12/06/2017.
 */
public class TraPlaylistDiffDTO {


    private List<TraPlaylistDTO> entityPlaylist;


    private List<TraMediaPlanEmissionDTO> parsedFromExcel;

    public TraPlaylistDiffDTO() {
    }

    public TraPlaylistDiffDTO(List<TraPlaylistDTO> entityPlaylist, List<TraMediaPlanEmissionDTO> parsedFromExcel) {
        this.entityPlaylist = entityPlaylist;
        this.parsedFromExcel = parsedFromExcel;
    }

    public List<TraPlaylistDTO> getEntityPlaylist() {
        return entityPlaylist;
    }

    public void setEntityPlaylist(List<TraPlaylistDTO> entityPlaylist) {
        this.entityPlaylist = entityPlaylist;
    }

    public List<TraMediaPlanEmissionDTO> getParsedFromExcel() {
        return parsedFromExcel;
    }

    public void setParsedFromExcel(List<TraMediaPlanEmissionDTO> parsedFromExcel) {
        this.parsedFromExcel = parsedFromExcel;
    }

    @Override
    public String toString() {
        return "PlaylistDiff{" +
                "entityPlaylist=" + entityPlaylist +
                ", parsedFromExcel=" + parsedFromExcel +
                '}';
    }
}
