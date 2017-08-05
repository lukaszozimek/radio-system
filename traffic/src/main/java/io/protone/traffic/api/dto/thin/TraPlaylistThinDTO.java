package io.protone.traffic.api.dto.thin;

import java.time.LocalDate;

/**
 * Created by lukaszozimek on 03/08/2017.
 */
public class TraPlaylistThinDTO {

    private Long id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraPlaylistThinDTO that = (TraPlaylistThinDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return playlistDate != null ? playlistDate.equals(that.playlistDate) : that.playlistDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (playlistDate != null ? playlistDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraPlaylistThinDTO{" +
                "id=" + id +
                ", playlistDate=" + playlistDate +
                '}';
    }
}
