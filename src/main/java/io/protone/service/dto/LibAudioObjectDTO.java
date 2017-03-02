package io.protone.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.protone.domain.enumeration.LibAudioQualityEnum;

/**
 * A DTO for the LibAudioObject entity.
 */
public class LibAudioObjectDTO implements Serializable {

    private Long id;

    @NotNull
    private Long length;

    @NotNull
    private Integer biTrate;

    @NotNull
    @Size(max = 100)
    private String codec;

    private LibAudioQualityEnum quality;

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
    public LibAudioQualityEnum getQuality() {
        return quality;
    }

    public void setQuality(LibAudioQualityEnum quality) {
        this.quality = quality;
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

        LibAudioObjectDTO libAudioObjectDTO = (LibAudioObjectDTO) o;

        if ( ! Objects.equals(id, libAudioObjectDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibAudioObjectDTO{" +
            "id=" + id +
            ", length='" + length + "'" +
            ", biTrate='" + biTrate + "'" +
            ", codec='" + codec + "'" +
            ", quality='" + quality + "'" +
            '}';
    }
}
