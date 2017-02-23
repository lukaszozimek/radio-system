package io.protone.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the LibTrack entity.
 */
public class LibTrackDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer discNo;

    @NotNull
    private Integer trackNo;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    private Long length;

    private String description;

    private Long albumId;

    private Long artistId;

    private Long networkId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getDiscNo() {
        return discNo;
    }

    public void setDiscNo(Integer discNo) {
        this.discNo = discNo;
    }
    public Integer getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(Integer trackNo) {
        this.trackNo = trackNo;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long libAlbumId) {
        this.albumId = libAlbumId;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long libArtistId) {
        this.artistId = libArtistId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LibTrackDTO libTrackDTO = (LibTrackDTO) o;

        if ( ! Objects.equals(id, libTrackDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibTrackDTO{" +
            "id=" + id +
            ", discNo='" + discNo + "'" +
            ", trackNo='" + trackNo + "'" +
            ", name='" + name + "'" +
            ", length='" + length + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
