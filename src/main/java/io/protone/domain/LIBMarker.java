package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import io.protone.domain.enumeration.LIBMarkerTypeEnum;

/**
 * A LIBMarker.
 */
@Entity
@Table(name = "lib_marker")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LIBMarker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marker_type", nullable = false)
    private LIBMarkerTypeEnum markerType;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Integer startTime;

    @ManyToOne
    private LIBMediaItem lIBMediaItem;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LIBMarkerTypeEnum getMarkerType() {
        return markerType;
    }

    public LIBMarker markerType(LIBMarkerTypeEnum markerType) {
        this.markerType = markerType;
        return this;
    }

    public void setMarkerType(LIBMarkerTypeEnum markerType) {
        this.markerType = markerType;
    }

    public String getName() {
        return name;
    }

    public LIBMarker name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public LIBMarker startTime(Integer startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public LIBMediaItem getLIBMediaItem() {
        return lIBMediaItem;
    }

    public LIBMarker lIBMediaItem(LIBMediaItem lIBMediaItem) {
        this.lIBMediaItem = lIBMediaItem;
        return this;
    }

    public void setLIBMediaItem(LIBMediaItem lIBMediaItem) {
        this.lIBMediaItem = lIBMediaItem;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public LIBMarker network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LIBMarker lIBMarker = (LIBMarker) o;
        if (lIBMarker.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lIBMarker.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBMarker{" +
            "id=" + id +
            ", markerType='" + markerType + "'" +
            ", name='" + name + "'" +
            ", startTime='" + startTime + "'" +
            '}';
    }
}
