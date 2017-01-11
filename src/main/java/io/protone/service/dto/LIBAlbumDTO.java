package io.protone.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.protone.domain.enumeration.LIBAlbumTypeEnum;

/**
 * A DTO for the LIBAlbum entity.
 */
public class LIBAlbumDTO implements Serializable {

    private Long id;

    @NotNull
    private LIBAlbumTypeEnum albumType;

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
    public LIBAlbumTypeEnum getAlbumType() {
        return albumType;
    }

    public void setAlbumType(LIBAlbumTypeEnum albumType) {
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

    public void setCoverId(Long lIBImageItemId) {
        this.coverId = lIBImageItemId;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long lIBLabelId) {
        this.labelId = lIBLabelId;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long lIBArtistId) {
        this.artistId = lIBArtistId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long cORNetworkId) {
        this.networkId = cORNetworkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LIBAlbumDTO lIBAlbumDTO = (LIBAlbumDTO) o;

        if ( ! Objects.equals(id, lIBAlbumDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBAlbumDTO{" +
            "id=" + id +
            ", albumType='" + albumType + "'" +
            ", name='" + name + "'" +
            ", releaseDate='" + releaseDate + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
