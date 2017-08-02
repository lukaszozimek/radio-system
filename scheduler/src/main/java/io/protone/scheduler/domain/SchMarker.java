package io.protone.scheduler.domain;

import io.protone.scheduler.domain.enumeration.MarkerTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Marker.
 */
@Entity
@Table(name = "sch_marker")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchMarker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "marker_type")
    private MarkerTypeEnum markerType;

    @Column(name = "name")
    private String name;

    @Column(name = "cue")
    private Long cue;

    @PodamExclude
    @ManyToOne
    private SchMediaItem mediaItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MarkerTypeEnum getMarkerType() {
        return markerType;
    }

    public SchMarker markerType(MarkerTypeEnum markerType) {
        this.markerType = markerType;
        return this;
    }

    public void setMarkerType(MarkerTypeEnum markerType) {
        this.markerType = markerType;
    }

    public String getName() {
        return name;
    }

    public SchMarker name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCue() {
        return cue;
    }

    public SchMarker cue(Long cue) {
        this.cue = cue;
        return this;
    }

    public void setCue(Long cue) {
        this.cue = cue;
    }

    public SchMediaItem getMediaItem() {
        return mediaItem;
    }

    public SchMarker mediaItem(SchMediaItem mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }

    public void setMediaItem(SchMediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchMarker marker = (SchMarker) o;
        if (marker.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Marker{" +
            "id=" + getId() +
            ", markerType='" + getMarkerType() + "'" +
            ", name='" + getName() + "'" +
            ", cue='" + getCue() + "'" +
            "}";
    }
}
