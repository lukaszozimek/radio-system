package io.protone.traffic.api.dto;

import java.util.List;

/**
 * Created by lukaszozimek on 12/06/2017.
 */
public class TraPlaylistDiffDTO {


    private List<TraPlaylistDTO> entityPlaylist;


    private List<TraPlaylistDTO> parsedFromExcel;

    public TraPlaylistDiffDTO() {
    }

    public TraPlaylistDiffDTO(List<TraPlaylistDTO> entityPlaylist, List<TraPlaylistDTO> parsedFromExcel) {
        this.entityPlaylist = entityPlaylist;
        this.parsedFromExcel = parsedFromExcel;
    }

    public List<TraPlaylistDTO> getEntityPlaylist() {
        return entityPlaylist;
    }

    public void setEntityPlaylist(List<TraPlaylistDTO> entityPlaylist) {
        this.entityPlaylist = entityPlaylist;
    }

    public List<TraPlaylistDTO> getParsedFromExcel() {
        return parsedFromExcel;
    }

    public void setParsedFromExcel(List<TraPlaylistDTO> parsedFromExcel) {
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
