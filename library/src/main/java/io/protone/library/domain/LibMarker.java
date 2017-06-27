package io.protone.library.domain;

import io.protone.domain.enumeration.LibMarkerTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LibMarker.
 */
@Entity
@Table(name = "lib_marker")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibMarker  extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marker_type", nullable = false)
    private LibMarkerTypeEnum markerType;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Long startTime;

    @ManyToOne
    private LibMediaItem mediaItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LibMarkerTypeEnum getMarkerType() {
        return markerType;
    }

    public LibMarker markerType(LibMarkerTypeEnum markerType) {
        this.markerType = markerType;
        return this;
    }

    public void setMarkerType(LibMarkerTypeEnum markerType) {
        this.markerType = markerType;
    }

    public String getName() {
        return name;
    }

    public LibMarker name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartTime() {
        return startTime;
    }

    public LibMarker startTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public LibMarker mediaItem(LibMediaItem libMediaItem) {
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
        LibMarker libMarker = (LibMarker) o;
        if (libMarker.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libMarker.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibMarker{" +
            "id=" + id +
            ", markerType='" + markerType + "'" +
            ", name='" + name + "'" +
            ", startTime='" + startTime + "'" +
            '}';
    }
}
