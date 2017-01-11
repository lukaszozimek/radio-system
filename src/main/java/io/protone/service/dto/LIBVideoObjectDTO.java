package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.protone.domain.enumeration.LIBVideoQualityEnum;
import io.protone.domain.enumeration.LIBAspectRatioEnum;

/**
 * A DTO for the LIBVideoObject entity.
 */
public class LIBVideoObjectDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer width;

    @NotNull
    private Integer height;

    @NotNull
    private Long length;

    @NotNull
    private Integer bitrate;

    @NotNull
    @Size(max = 100)
    private String codec;

    private LIBVideoQualityEnum quality;

    private LIBAspectRatioEnum aspectRatio;


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
    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }
    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }
    public LIBVideoQualityEnum getQuality() {
        return quality;
    }

    public void setQuality(LIBVideoQualityEnum quality) {
        this.quality = quality;
    }
    public LIBAspectRatioEnum getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(LIBAspectRatioEnum aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Long getCloudObjectId() {
        return cloudObjectId;
    }

    public void setCloudObjectId(Long lIBCloudObjectId) {
        this.cloudObjectId = lIBCloudObjectId;
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

        LIBVideoObjectDTO lIBVideoObjectDTO = (LIBVideoObjectDTO) o;

        if ( ! Objects.equals(id, lIBVideoObjectDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBVideoObjectDTO{" +
            "id=" + id +
            ", width='" + width + "'" +
            ", height='" + height + "'" +
            ", length='" + length + "'" +
            ", bitrate='" + bitrate + "'" +
            ", codec='" + codec + "'" +
            ", quality='" + quality + "'" +
            ", aspectRatio='" + aspectRatio + "'" +
            '}';
    }
}
