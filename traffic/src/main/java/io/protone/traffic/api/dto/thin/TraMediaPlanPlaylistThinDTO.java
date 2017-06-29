package io.protone.traffic.api.dto.thin;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
public class TraMediaPlanPlaylistThinDTO implements Serializable{

    private Long id;

    @NotNull
    private LocalDate playlistDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPlaylistDate() {
        return playlistDate;
    }

    public void setPlaylistDate(LocalDate playlistDate) {
        this.playlistDate = playlistDate;
    }

}
