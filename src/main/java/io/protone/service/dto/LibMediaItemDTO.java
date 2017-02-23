package io.protone.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.domain.enumeration.LibItemStateEnum;

/**
 * A DTO for the LibMediaItem entity.
 */
public class LibMediaItemDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 15)
    private String idx;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    private LibItemTypeEnum itemType;

    @NotNull
    private Long length;

    @NotNull
    private LibItemStateEnum state;

    private String command;

    private String description;

    private Long libraryId;

    private Long labelId;

    private Long artistId;

    private Long albumId;

    private Long trackId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LibItemTypeEnum getItemType() {
        return itemType;
    }

    public void setItemType(LibItemTypeEnum itemType) {
        this.itemType = itemType;
    }
    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }
    public LibItemStateEnum getState() {
        return state;
    }

    public void setState(LibItemStateEnum state) {
        this.state = state;
    }
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Long libLibraryId) {
        this.libraryId = libLibraryId;
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

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long libAlbumId) {
        this.albumId = libAlbumId;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long libTrackId) {
        this.trackId = libTrackId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LibMediaItemDTO libMediaItemDTO = (LibMediaItemDTO) o;

        if ( ! Objects.equals(id, libMediaItemDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibMediaItemDTO{" +
            "id=" + id +
            ", idx='" + idx + "'" +
            ", name='" + name + "'" +
            ", itemType='" + itemType + "'" +
            ", length='" + length + "'" +
            ", state='" + state + "'" +
            ", command='" + command + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
