package io.protone.library.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorNetwork;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LibTrack.
 */
@Entity
@Table(name = "lib_track")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibTrack  extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "disc_no", nullable = false)
    private Integer discNo;

    @NotNull
    @Column(name = "track_no", nullable = false)
    private Integer trackNo;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(name = "length", nullable = false)
    private Long length;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private LibAlbum album;

    @ManyToOne
    private LibArtist artist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiscNo() {
        return discNo;
    }

    public void setDiscNo(Integer discNo) {
        this.discNo = discNo;
    }

    public LibTrack discNo(Integer discNo) {
        this.discNo = discNo;
        return this;
    }

    public Integer getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(Integer trackNo) {
        this.trackNo = trackNo;
    }

    public LibTrack trackNo(Integer trackNo) {
        this.trackNo = trackNo;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibTrack name(String name) {
        this.name = name;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public LibTrack length(Long length) {
        this.length = length;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibTrack description(String description) {
        this.description = description;
        return this;
    }

    public LibAlbum getAlbum() {
        return album;
    }

    public void setAlbum(LibAlbum libAlbum) {
        this.album = libAlbum;
    }

    public LibTrack album(LibAlbum libAlbum) {
        this.album = libAlbum;
        return this;
    }

    public LibArtist getArtist() {
        return artist;
    }

    public void setArtist(LibArtist libArtist) {
        this.artist = libArtist;
    }

    public LibTrack artist(LibArtist libArtist) {
        this.artist = libArtist;
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
        LibTrack libTrack = (LibTrack) o;
        if (libTrack.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libTrack.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibTrack{" +
            "id=" + id +
            ", discNo='" + discNo + "'" +
            ", trackNo='" + trackNo + "'" +
            ", name='" + name + "'" +
            ", length='" + length + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
