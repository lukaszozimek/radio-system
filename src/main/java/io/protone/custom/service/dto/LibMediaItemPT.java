package io.protone.custom.service.dto;

import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the LibMediaItem entity.
 */
public class LibMediaItemPT implements Serializable {

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

    public void setLibraryId(Long lIBLibraryId) {
        this.libraryId = lIBLibraryId;
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

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long lIBAlbumId) {
        this.albumId = lIBAlbumId;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long lIBTrackId) {
        this.trackId = lIBTrackId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LibMediaItemPT lIBMediaItemDTO = (LibMediaItemPT) o;

        if ( ! Objects.equals(id, lIBMediaItemDTO.id)) return false;

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
