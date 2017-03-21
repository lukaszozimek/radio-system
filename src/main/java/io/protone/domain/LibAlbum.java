package io.protone.domain;

import io.protone.domain.enumeration.LibAlbumTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A LibAlbum.
 */
@Entity
@Table(name = "lib_album")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibAlbum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "album_type", nullable = false)
    private LibAlbumTypeEnum albumType;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private LibImageItem cover;

    @ManyToOne
    private LibLabel label;

    @ManyToOne
    private LibArtist artist;

    @ManyToOne
    private CorNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LibAlbumTypeEnum getAlbumType() {
        return albumType;
    }

    public LibAlbum albumType(LibAlbumTypeEnum albumType) {
        this.albumType = albumType;
        return this;
    }

    public void setAlbumType(LibAlbumTypeEnum albumType) {
        this.albumType = albumType;
    }

    public String getName() {
        return name;
    }

    public LibAlbum name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public LibAlbum releaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public LibAlbum description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibImageItem getCover() {
        return cover;
    }

    public LibAlbum cover(LibImageItem libImageItem) {
        this.cover = libImageItem;
        return this;
    }

    public void setCover(LibImageItem libImageItem) {
        this.cover = libImageItem;
    }

    public LibLabel getLabel() {
        return label;
    }

    public LibAlbum label(LibLabel libLabel) {
        this.label = libLabel;
        return this;
    }

    public void setLabel(LibLabel libLabel) {
        this.label = libLabel;
    }

    public LibArtist getArtist() {
        return artist;
    }

    public LibAlbum artist(LibArtist libArtist) {
        this.artist = libArtist;
        return this;
    }

    public void setArtist(LibArtist libArtist) {
        this.artist = libArtist;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public LibAlbum network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibAlbum libAlbum = (LibAlbum) o;
        if (libAlbum.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libAlbum.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibAlbum{" +
            "id=" + id +
            ", albumType='" + albumType + "'" +
            ", name='" + name + "'" +
            ", releaseDate='" + releaseDate + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
