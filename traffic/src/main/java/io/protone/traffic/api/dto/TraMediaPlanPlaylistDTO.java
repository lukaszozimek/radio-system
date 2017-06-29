package io.protone.traffic.api.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
public class TraMediaPlanPlaylistDTO implements Serializable{

    private Long id;

    @NotNull
    private LocalDate playlistDate;

    private Set<TraBlockDTO> playlists = new HashSet<>();

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

    public Set<TraBlockDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<TraBlockDTO> playlists) {
        this.playlists = playlists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraMediaPlanPlaylistDTO that = (TraMediaPlanPlaylistDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (playlistDate != null ? !playlistDate.equals(that.playlistDate) : that.playlistDate != null) return false;
        return playlists != null ? playlists.equals(that.playlists) : that.playlists == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (playlistDate != null ? playlistDate.hashCode() : 0);
        result = 31 * result + (playlists != null ? playlists.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraMediaPlanPlaylistDTO{" +
            "id=" + id +
            ", playlistDate=" + playlistDate +
            ", playlists=" + playlists +
            '}';
    }
}
