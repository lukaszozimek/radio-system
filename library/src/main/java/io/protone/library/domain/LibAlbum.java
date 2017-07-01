package io.protone.library.domain;


import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.enumeration.LibAlbumTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A LibAlbum.
 */
@Entity
@Table(name = "lib_album")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibAlbum extends AbstractAuditingEntity implements Serializable {

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
    private CorImageItem mainImage;

    @ManyToOne
    private LibLabel label;

    @ManyToOne
    private LibArtist artist;

    @ManyToOne
    private CorNetwork network;

    @PodamExclude
    @OneToMany
    @JoinTable(
            name = "lib_album_cor_image_item",
            joinColumns = @JoinColumn(name = "lib_album_id"),
            inverseJoinColumns = @JoinColumn(name = "cor_image_item_id")
    )
    private Set<CorImageItem> imageItems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LibAlbumTypeEnum getAlbumType() {
        return albumType;
    }

    public void setAlbumType(LibAlbumTypeEnum albumType) {
        this.albumType = albumType;
    }

    public LibAlbum albumType(LibAlbumTypeEnum albumType) {
        this.albumType = albumType;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibAlbum name(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public LibAlbum releaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibAlbum description(String description) {
        this.description = description;
        return this;
    }

    public Set<CorImageItem> getCover() {
        return imageItems;
    }

    public void setCover(Set<CorImageItem> libImageItem) {
        this.imageItems = libImageItem;
    }

    public LibAlbum cover(Set<CorImageItem> libImageItem) {
        this.imageItems = libImageItem;
        return this;
    }

    public LibAlbum mainImage(CorImageItem mainImage) {
        this.mainImage = mainImage;
        return this;
    }

    public CorImageItem getMainImage() {
        return mainImage;
    }

    public void setMainImage(CorImageItem mainImage) {
        this.mainImage = mainImage;
    }

    public LibLabel getLabel() {
        return label;
    }

    public void setLabel(LibLabel libLabel) {
        this.label = libLabel;
    }

    public LibAlbum label(LibLabel libLabel) {
        this.label = libLabel;
        return this;
    }

    public LibArtist getArtist() {
        return artist;
    }

    public void setArtist(LibArtist libArtist) {
        this.artist = libArtist;
    }

    public LibAlbum artist(LibArtist libArtist) {
        this.artist = libArtist;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public LibAlbum network(CorNetwork corNetwork) {
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
