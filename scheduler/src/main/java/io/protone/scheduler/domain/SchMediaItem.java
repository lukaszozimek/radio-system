package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A MediaItem.
 */
@Entity
@Table(name = "sch_media_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchMediaItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @PodamExclude
    @Column(name = "jhi_library")
    private String library;

    @Column(name = "idx")
    private String idx;

    @Column(name = "artist")
    private String artist;

    @Column(name = "album")
    private String album;

    @Column(name = "cover")
    private String cover;

    @Column(name = "length")
    private Long length;

    @Column(name = "name")
    private String name;

    @Column(name = "stream")
    private String stream;

    @PodamExclude
    @OneToMany(mappedBy = "mediaItem")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchMarker> markers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public SchMediaItem library(String library) {
        this.library = library;
        return this;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public SchMediaItem idx(String idx) {
        this.idx = idx;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public SchMediaItem artist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public SchMediaItem album(String album) {
        this.album = album;
        return this;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public SchMediaItem cover(String cover) {
        this.cover = cover;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public SchMediaItem length(Long length) {
        this.length = length;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchMediaItem name(String name) {
        this.name = name;
        return this;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public SchMediaItem stream(String stream) {
        this.stream = stream;
        return this;
    }

    public Set<SchMarker> getMarkers() {
        return markers;
    }

    public void setMarkers(Set<SchMarker> markers) {
        this.markers = markers;
    }

    public SchMediaItem markers(Set<SchMarker> markers) {
        this.markers = markers;
        return this;
    }

    public SchMediaItem addMarker(SchMarker marker) {
        this.markers.add(marker);
        marker.setMediaItem(this);
        return this;
    }

    public SchMediaItem removeMarker(SchMarker marker) {
        this.markers.remove(marker);
        marker.setMediaItem(null);
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
        SchMediaItem mediaItem = (SchMediaItem) o;
        if (mediaItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mediaItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MediaItem{" +
                "id=" + getId() +
                ", library='" + getLibrary() + "'" +
                ", idx='" + getIdx() + "'" +
                ", artist='" + getArtist() + "'" +
                ", album='" + getAlbum() + "'" +
                ", cover='" + getCover() + "'" +
                ", length='" + getLength() + "'" +
                ", name='" + getName() + "'" +
                ", stream='" + getStream() + "'" +
                "}";
    }
}
