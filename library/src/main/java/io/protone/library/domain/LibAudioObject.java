package io.protone.library.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorOrganization;
import io.protone.library.domain.enumeration.LibAudioQualityEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LibAudioObject.
 */
@Entity
@Table(name = "lib_audio_object")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibAudioObject extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "length", nullable = false)
    private Double length;

    @NotNull
    @Column(name = "bi_trate", nullable = false)
    private Integer biTrate;

    @NotNull
    @Size(max = 100)
    @Column(name = "codec", length = 100, nullable = false)
    private String codec;

    @Enumerated(EnumType.STRING)
    @Column(name = "quality")
    @PodamExclude
    private LibAudioQualityEnum quality;

    @OneToOne
    @JoinColumn(unique = true)
    @PodamExclude
    private LibCloudObject cloudObject;

    @ManyToOne
    @PodamExclude
    private LibMediaItem mediaItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public LibAudioObject length(Double length) {
        this.length = length;
        return this;
    }

    public Integer getBiTrate() {
        return biTrate;
    }

    public void setBiTrate(Integer biTrate) {
        this.biTrate = biTrate;
    }

    public LibAudioObject biTrate(Integer biTrate) {
        this.biTrate = biTrate;
        return this;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public LibAudioObject codec(String codec) {
        this.codec = codec;
        return this;
    }

    public LibAudioQualityEnum getQuality() {
        return quality;
    }

    public void setQuality(LibAudioQualityEnum quality) {
        this.quality = quality;
    }

    public LibAudioObject quality(LibAudioQualityEnum quality) {
        this.quality = quality;
        return this;
    }

    public LibCloudObject getCloudObject() {
        return cloudObject;
    }

    public void setCloudObject(LibCloudObject libCloudObject) {
        this.cloudObject = libCloudObject;
    }

    public LibAudioObject cloudObject(LibCloudObject libCloudObject) {
        this.cloudObject = libCloudObject;
        return this;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
    }

    public LibAudioObject mediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
        return this;
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
