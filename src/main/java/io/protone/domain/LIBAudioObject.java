package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import io.protone.domain.enumeration.LIBAudioQualityEnum;

/**
 * A LIBAudioObject.
 */
@Entity
@Table(name = "lib_audio_object")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LIBAudioObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    private LIBAudioQualityEnum quality;

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

    public Long getLength() {
        return length;
    }

    public LIBAudioObject length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public LIBAudioObject bitrate(Integer bitrate) {
        this.bitrate = bitrate;
        return this;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public String getCodec() {
        return codec;
    }

    public LIBAudioObject codec(String codec) {
        this.codec = codec;
        return this;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public LIBAudioQualityEnum getQuality() {
        return quality;
    }

    public LIBAudioObject quality(LIBAudioQualityEnum quality) {
        this.quality = quality;
        return this;
    }

    public void setQuality(LIBAudioQualityEnum quality) {
        this.quality = quality;
    }

    public LIBCloudObject getCloudObject() {
        return cloudObject;
    }

    public LIBAudioObject cloudObject(LIBCloudObject lIBCloudObject) {
        this.cloudObject = lIBCloudObject;
        return this;
    }

    public void setCloudObject(LIBCloudObject lIBCloudObject) {
        this.cloudObject = lIBCloudObject;
    }

    public LIBMediaItem getMediaItem() {
        return mediaItem;
    }

    public LIBAudioObject mediaItem(LIBMediaItem lIBMediaItem) {
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
        LIBAudioObject lIBAudioObject = (LIBAudioObject) o;
        if (lIBAudioObject.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lIBAudioObject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBAudioObject{" +
            "id=" + id +
            ", length='" + length + "'" +
            ", bitrate='" + bitrate + "'" +
            ", codec='" + codec + "'" +
            ", quality='" + quality + "'" +
            '}';
    }
}
