package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import io.protone.domain.enumeration.LIBAlbumTypeEnum;

/**
 * A LIBAlbum.
 */
@Entity
@Table(name = "lib_album")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LIBAlbum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "album_type", nullable = false)
    private LIBAlbumTypeEnum albumType;

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
    private LIBImageItem cover;

    @ManyToOne
    private LIBLabel label;

    @ManyToOne
    private LIBArtist artist;

    @ManyToOne
    private CORNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LIBAlbumTypeEnum getAlbumType() {
        return albumType;
    }

    public LIBAlbum albumType(LIBAlbumTypeEnum albumType) {
        this.albumType = albumType;
        return this;
    }

    public void setAlbumType(LIBAlbumTypeEnum albumType) {
        this.albumType = albumType;
    }

    public String getName() {
        return name;
    }

    public LIBAlbum name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public LIBAlbum releaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public LIBAlbum description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LIBImageItem getCover() {
        return cover;
    }

    public LIBAlbum cover(LIBImageItem lIBImageItem) {
        this.cover = lIBImageItem;
        return this;
    }

    public void setCover(LIBImageItem lIBImageItem) {
        this.cover = lIBImageItem;
    }

    public LIBLabel getLabel() {
        return label;
    }

    public LIBAlbum label(LIBLabel lIBLabel) {
        this.label = lIBLabel;
        return this;
    }

    public void setLabel(LIBLabel lIBLabel) {
        this.label = lIBLabel;
    }

    public LIBArtist getArtist() {
        return artist;
    }

    public LIBAlbum artist(LIBArtist lIBArtist) {
        this.artist = lIBArtist;
        return this;
    }

    public void setArtist(LIBArtist lIBArtist) {
        this.artist = lIBArtist;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public LIBAlbum network(CORNetwork cORNetwork) {
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
        LIBAlbum lIBAlbum = (LIBAlbum) o;
        if (lIBAlbum.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lIBAlbum.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBAlbum{" +
            "id=" + id +
            ", albumType='" + albumType + "'" +
            ", name='" + name + "'" +
            ", releaseDate='" + releaseDate + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
