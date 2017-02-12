package io.protone.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import io.protone.domain.enumeration.LibAlbumTypeEnum;

/**
 * A DTO for the LibAlbum entity.
 */
public class LibAlbumDTO implements Serializable {

    private Long id;

    @NotNull
    private LibAlbumTypeEnum albumType;

    @NotNull
    @Size(max = 100)
    private String name;

    private LocalDate releaseDate;

    private String description;

    private Long coverId;

    private Long labelId;

    private Long artistId;

    private Long networkId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LibAlbumTypeEnum getAlbumType() {
        return albumType;
    }

    public void setAlbumType(LibAlbumTypeEnum albumType) {
        this.albumType = albumType;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCoverId() {
        return coverId;
    }

    public void setCoverId(Long libImageItemId) {
        this.coverId = libImageItemId;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long libLabelId) {
        this.labelId = libLabelId;
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

        LibAlbumDTO libAlbumDTO = (LibAlbumDTO) o;

        if ( ! Objects.equals(id, libAlbumDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibAlbumDTO{" +
            "id=" + id +
            ", albumType='" + albumType + "'" +
            ", name='" + name + "'" +
            ", releaseDate='" + releaseDate + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
