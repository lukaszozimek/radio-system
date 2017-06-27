package io.protone.application.web.rest.dto.traffic;

import java.util.List;

/**
 * Created by lukaszozimek on 12/06/2017.
 */
public class TraPlaylistDiffDTO {


    private List<TraPlaylistDTO> entityPlaylist;


    private List<TraPlaylistDTO> parsedFromExcel;

    public List<TraPlaylistDTO> getEntityPlaylist() {
        return entityPlaylist;
    }

    public List<TraPlaylistDTO> getParsedFromExcel() {
        return parsedFromExcel;
    }

    public void setParsedFromExcel(List<TraPlaylistDTO> parsedFromExcel) {
        this.parsedFromExcel = parsedFromExcel;
    }

    public void setEntityPlaylist(List<TraPlaylistDTO> entityPlaylist) {
        this.entityPlaylist = entityPlaylist;
    }

    public TraPlaylistDiffDTO() {
    }

    public TraPlaylistDiffDTO(List<TraPlaylistDTO> entityPlaylist, List<TraPlaylistDTO> parsedFromExcel) {
        this.entityPlaylist = entityPlaylist;
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
