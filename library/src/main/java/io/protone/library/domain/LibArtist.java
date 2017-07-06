package io.protone.library.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.enumeration.LibArtistTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A LibArtist.
 */
@Entity
@Table(name = "lib_artist", uniqueConstraints =
@UniqueConstraint(columnNames = {"name", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibArtist extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LibArtistTypeEnum type;

    @Column(name = "description")
    private String description;

    @OneToOne
    @PodamExclude
    private CorImageItem mainImage;

    @ManyToOne
    private CorNetwork network;

    @PodamExclude
    @OneToMany
    @JoinTable(
            name = "lib_artist_cor_image_item",
            joinColumns = @JoinColumn(name = "lib_artist_id"),
            inverseJoinColumns = @JoinColumn(name = "cor_image_item_id")
    )
    private Set<CorImageItem> imageItems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibArtist name(String name) {
        this.name = name;
        return this;
    }

    public LibArtist mainImage(CorImageItem mainImage) {
        this.mainImage = mainImage;
        return this;
    }

    public CorImageItem getMainImage() {
        return mainImage;
    }

    public void setMainImage(CorImageItem mainImage) {
        this.mainImage = mainImage;
    }

    public LibArtistTypeEnum getType() {
        return type;
    }

    public void setType(LibArtistTypeEnum type) {
        this.type = type;
    }

    public LibArtist type(LibArtistTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibArtist description(String description) {
        this.description = description;
        return this;
    }

    public Set<CorImageItem> getImageItems() {
        return imageItems;
    }

    public void setImageItems(Set<CorImageItem> imageItems) {
        this.imageItems = imageItems;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public LibArtist network(CorNetwork corNetwork) {
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
        LibArtist libArtist = (LibArtist) o;
        if (libArtist.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libArtist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibArtist{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", type='" + type + "'" +
                ", description='" + description + "'" +
                '}';
    }



}
