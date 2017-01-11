package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import io.protone.domain.enumeration.LIBVideoQualityEnum;

import io.protone.domain.enumeration.LIBAspectRatioEnum;

/**
 * A LIBVideoObject.
 */
@Entity
@Table(name = "lib_video_object")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LIBVideoObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "width", nullable = false)
    private Integer width;

    @NotNull
    @Column(name = "height", nullable = false)
    private Integer height;

    @NotNull
    @Column(name = "length", nullable = false)
    private Long length;

    @NotNull
    @Column(name = "bitrate", nullable = false)
    private Integer bitrate;

    @NotNull
    @Size(max = 100)
    @Column(name = "codec", length = 100, nullable = false)
    private String codec;

    @Enumerated(EnumType.STRING)
    @Column(name = "quality")
    private LIBVideoQualityEnum quality;

    @Enumerated(EnumType.STRING)
    @Column(name = "aspect_ratio")
    private LIBAspectRatioEnum aspectRatio;

    @OneToOne
    @JoinColumn(unique = true)
    private LIBCloudObject cloudObject;

    @ManyToOne
    private LIBMediaItem mediaItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public LIBVideoObject width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public LIBVideoObject height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Long getLength() {
        return length;
    }

    public LIBVideoObject length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public LIBVideoObject bitrate(Integer bitrate) {
        this.bitrate = bitrate;
        return this;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public String getCodec() {
        return codec;
    }

    public LIBVideoObject codec(String codec) {
        this.codec = codec;
        return this;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public LIBVideoQualityEnum getQuality() {
        return quality;
    }

    public LIBVideoObject quality(LIBVideoQualityEnum quality) {
        this.quality = quality;
        return this;
    }

    public void setQuality(LIBVideoQualityEnum quality) {
        this.quality = quality;
    }

    public LIBAspectRatioEnum getAspectRatio() {
        return aspectRatio;
    }

    public LIBVideoObject aspectRatio(LIBAspectRatioEnum aspectRatio) {
        this.aspectRatio = aspectRatio;
        return this;
    }

    public void setAspectRatio(LIBAspectRatioEnum aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public LIBCloudObject getCloudObject() {
        return cloudObject;
    }

    public LIBVideoObject cloudObject(LIBCloudObject lIBCloudObject) {
        this.cloudObject = lIBCloudObject;
        return this;
    }

    public void setCloudObject(LIBCloudObject lIBCloudObject) {
        this.cloudObject = lIBCloudObject;
    }

    public LIBMediaItem getMediaItem() {
        return mediaItem;
    }

    public LIBVideoObject mediaItem(LIBMediaItem lIBMediaItem) {
        this.mediaItem = lIBMediaItem;
        return this;
    }

    public void setMediaItem(LIBMediaItem lIBMediaItem) {
        this.mediaItem = lIBMediaItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LIBVideoObject lIBVideoObject = (LIBVideoObject) o;
        if (lIBVideoObject.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lIBVideoObject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBVideoObject{" +
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
