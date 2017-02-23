package io.protone.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import io.protone.domain.enumeration.LibVideoQualityEnum;
import io.protone.domain.enumeration.LibAspecTratioEnum;

/**
 * A DTO for the LibVideoObject entity.
 */
public class LibVideoObjectDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer width;

    @NotNull
    private Integer height;

    @NotNull
    private Long length;

    @NotNull
    private Integer biTrate;

    @NotNull
    @Size(max = 100)
    private String codec;

    private LibVideoQualityEnum quality;

    private LibAspecTratioEnum aspecTratio;

    private Long cloudObjectId;

    private Long mediaItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }
    public Integer getBiTrate() {
        return biTrate;
    }

    public void setBiTrate(Integer biTrate) {
        this.biTrate = biTrate;
    }
    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }
    public LibVideoQualityEnum getQuality() {
        return quality;
    }

    public void setQuality(LibVideoQualityEnum quality) {
        this.quality = quality;
    }
    public LibAspecTratioEnum getAspecTratio() {
        return aspecTratio;
    }

    public void setAspecTratio(LibAspecTratioEnum aspecTratio) {
        this.aspecTratio = aspecTratio;
    }

    public Long getCloudObjectId() {
        return cloudObjectId;
    }

    public void setCloudObjectId(Long libCloudObjectId) {
        this.cloudObjectId = libCloudObjectId;
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

        LibVideoObjectDTO libVideoObjectDTO = (LibVideoObjectDTO) o;

        if ( ! Objects.equals(id, libVideoObjectDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibVideoObjectDTO{" +
            "id=" + id +
            ", width='" + width + "'" +
            ", height='" + height + "'" +
            ", length='" + length + "'" +
            ", biTrate='" + biTrate + "'" +
            ", codec='" + codec + "'" +
            ", quality='" + quality + "'" +
            ", aspecTratio='" + aspecTratio + "'" +
            '}';
    }
}
