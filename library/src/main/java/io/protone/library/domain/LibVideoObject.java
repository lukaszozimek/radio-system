package io.protone.library.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.enumeration.LibAspecTratioEnum;
import io.protone.library.domain.enumeration.LibVideoQualityEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LibVideoObject.
 */
@Entity
@Table(name = "lib_video_object")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibVideoObject  extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "length")
    private Long length;

    @Column(name = "bi_trate")
    private Integer biTrate;

    @Column(name = "codec")
    private String codec;

    @Enumerated(EnumType.STRING)
    @Column(name = "quality")
    private LibVideoQualityEnum quality;

    @Enumerated(EnumType.STRING)
    @Column(name = "aspec_tratio")
    private LibAspecTratioEnum aspecTratio;

    @OneToOne
    @JoinColumn(unique = true)
    private LibCloudObject cloudObject;

    @ManyToOne
    private LibMediaItem mediaItem;

    @ManyToOne
    private CorNetwork network;

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

    public LibVideoObject width(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public LibVideoObject height(Integer height) {
        this.height = height;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public LibVideoObject length(Long length) {
        this.length = length;
        return this;
    }

    public Integer getBiTrate() {
        return biTrate;
    }

    public void setBiTrate(Integer biTrate) {
        this.biTrate = biTrate;
    }

    public LibVideoObject biTrate(Integer biTrate) {
        this.biTrate = biTrate;
        return this;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public LibVideoObject codec(String codec) {
        this.codec = codec;
        return this;
    }

    public LibVideoQualityEnum getQuality() {
        return quality;
    }

    public void setQuality(LibVideoQualityEnum quality) {
        this.quality = quality;
    }

    public LibVideoObject quality(LibVideoQualityEnum quality) {
        this.quality = quality;
        return this;
    }

    public LibAspecTratioEnum getAspecTratio() {
        return aspecTratio;
    }

    public void setAspecTratio(LibAspecTratioEnum aspecTratio) {
        this.aspecTratio = aspecTratio;
    }

    public LibVideoObject aspecTratio(LibAspecTratioEnum aspecTratio) {
        this.aspecTratio = aspecTratio;
        return this;
    }

    public LibCloudObject getCloudObject() {
        return cloudObject;
    }

    public void setCloudObject(LibCloudObject libCloudObject) {
        this.cloudObject = libCloudObject;
    }

    public LibVideoObject cloudObject(LibCloudObject libCloudObject) {
        this.cloudObject = libCloudObject;
        return this;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
    }

    public LibVideoObject mediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public LibVideoObject network(CorNetwork corNetwork) {
        this.network = corNetwork;
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
        LibVideoObject libVideoObject = (LibVideoObject) o;
        if (libVideoObject.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libVideoObject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibVideoObject{" +
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
