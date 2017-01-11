package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.protone.domain.enumeration.LIBMarkerTypeEnum;

/**
 * A DTO for the LIBMarker entity.
 */
public class LIBMarkerDTO implements Serializable {

    private Long id;

    @NotNull
    private LIBMarkerTypeEnum markerType;

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
    public LIBMarkerTypeEnum getMarkerType() {
        return markerType;
    }

    public void setMarkerType(LIBMarkerTypeEnum markerType) {
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

    public void setMediaItemId(Long lIBMediaItemId) {
        this.mediaItemId = lIBMediaItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LIBMarkerDTO lIBMarkerDTO = (LIBMarkerDTO) o;

        if ( ! Objects.equals(id, lIBMarkerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBMarkerDTO{" +
            "id=" + id +
            ", markerType='" + markerType + "'" +
            ", name='" + name + "'" +
            ", startTime='" + startTime + "'" +
            '}';
    }
}
