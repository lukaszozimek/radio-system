package io.protone.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.protone.domain.enumeration.LibMarkerTypeEnum;

/**
 * A DTO for the LibMarker entity.
 */
public class LibMarkerDTO implements Serializable {

    private Long id;

    @NotNull
    private LibMarkerTypeEnum markerType;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    private Integer startTime;

    private Long mediaItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LibMarkerTypeEnum getMarkerType() {
        return markerType;
    }

    public void setMarkerType(LibMarkerTypeEnum markerType) {
        this.markerType = markerType;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Long getMediaItemId() {
        return mediaItemId;
    }

    public void setMediaItemId(Long libMediaItemId) {
        this.mediaItemId = libMediaItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LibMarkerDTO libMarkerDTO = (LibMarkerDTO) o;

        if ( ! Objects.equals(id, libMarkerDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibMarkerDTO{" +
            "id=" + id +
            ", markerType='" + markerType + "'" +
            ", name='" + name + "'" +
            ", startTime='" + startTime + "'" +
            '}';
    }
}
