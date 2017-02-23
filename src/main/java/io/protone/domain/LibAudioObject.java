package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import io.protone.domain.enumeration.LibAudioQualityEnum;

/**
 * A LibAudioObject.
 */
@Entity
@Table(name = "lib_audio_object")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibAudioObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "length", nullable = false)
    private Long length;

    @NotNull
    @Column(name = "bi_trate", nullable = false)
    private Integer biTrate;

    @NotNull
    @Size(max = 100)
    @Column(name = "codec", length = 100, nullable = false)
    private String codec;

    @Enumerated(EnumType.STRING)
    @Column(name = "quality")
    private LibAudioQualityEnum quality;

    @OneToOne
    @JoinColumn(unique = true)
    private LibCloudObject cloudObject;

    @ManyToOne
    private LibMediaItem mediaItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLength() {
        return length;
    }

    public LibAudioObject length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Integer getBiTrate() {
        return biTrate;
    }

    public LibAudioObject biTrate(Integer biTrate) {
        this.biTrate = biTrate;
        return this;
    }

    public void setBiTrate(Integer biTrate) {
        this.biTrate = biTrate;
    }

    public String getCodec() {
        return codec;
    }

    public LibAudioObject codec(String codec) {
        this.codec = codec;
        return this;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public LibAudioQualityEnum getQuality() {
        return quality;
    }

    public LibAudioObject quality(LibAudioQualityEnum quality) {
        this.quality = quality;
        return this;
    }

    public void setQuality(LibAudioQualityEnum quality) {
        this.quality = quality;
    }

    public LibCloudObject getCloudObject() {
        return cloudObject;
    }

    public LibAudioObject cloudObject(LibCloudObject libCloudObject) {
        this.cloudObject = libCloudObject;
        return this;
    }

    public void setCloudObject(LibCloudObject libCloudObject) {
        this.cloudObject = libCloudObject;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public LibAudioObject mediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
        return this;
    }

    public void setMediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibAudioObject libAudioObject = (LibAudioObject) o;
        if (libAudioObject.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libAudioObject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibAudioObject{" +
            "id=" + id +
            ", length='" + length + "'" +
            ", biTrate='" + biTrate + "'" +
            ", codec='" + codec + "'" +
            ", quality='" + quality + "'" +
            '}';
    }
}
