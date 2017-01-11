package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.protone.domain.enumeration.LIBAudioQualityEnum;

/**
 * A DTO for the LIBAudioObject entity.
 */
public class LIBAudioObjectDTO implements Serializable {

    private Long id;

    @NotNull
    private Long length;

    @NotNull
    private Integer bitrate;

    @NotNull
    @Size(max = 100)
    private String codec;

    private LIBAudioQualityEnum quality;


    private Long cloudObjectId;
    
    private Long mediaItemId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public LIBAudioQualityEnum getQuality() {
        return quality;
    }

    public void setQuality(LIBAudioQualityEnum quality) {
        this.quality = quality;
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

        LIBAudioObjectDTO lIBAudioObjectDTO = (LIBAudioObjectDTO) o;

        if ( ! Objects.equals(id, lIBAudioObjectDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBAudioObjectDTO{" +
            "id=" + id +
            ", length='" + length + "'" +
            ", bitrate='" + bitrate + "'" +
            ", codec='" + codec + "'" +
            ", quality='" + quality + "'" +
            '}';
    }
}
