package io.protone.library.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorNetwork;
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
 * A LibLibrary.
 */
@Entity
@Table(name = "lib_library")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibLibrary extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @NotNull
    @Size(max = 1)
    @Column(name = "prefix", length = 1, nullable = false)
    private String prefix;

    @NotNull
    @Size(max = 3)
    @Column(name = "shortcut", length = 3, nullable = false, unique = true)
    private String shortcut;

    @NotNull
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;
    @NotNull
    @Column(name = "counter", nullable = false)
    private Long counter;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private CorNetwork network;

    @OneToOne
    @PodamExclude
    private CorImageItem mainImage;


    @OneToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "lib_library_channel",
            joinColumns = @JoinColumn(name = "lib_library_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "channels_id", referencedColumnName = "id"))
    @PodamExclude
    private Set<CorChannel> channels = new HashSet<>();

    @PodamExclude
    @OneToMany
    @JoinTable(
            name = "lib_library_cor_image_item",
            joinColumns = @JoinColumn(name = "lib_library_id"),
            inverseJoinColumns = @JoinColumn(name = "cor_image_item_id")
    )
    private Set<CorImageItem> imageItems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public LibLibrary prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public LibLibrary shortcut(String shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibLibrary name(String name) {
        this.name = name;
        return this;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public LibLibrary counter(Long counter) {
        this.counter = counter;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibLibrary description(String description) {
        this.description = description;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public LibLibrary network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public Set<CorChannel> getChannels() {
        return channels;
    }

    public void setChannels(Set<CorChannel> corChannels) {
        this.channels = corChannels;
    }

    public LibLibrary channels(Set<CorChannel> corChannels) {
        this.channels = corChannels;
        return this;
    }

    public LibLibrary addChannel(CorChannel corChannel) {
        this.channels.add(corChannel);

        return this;
    }

    public LibLibrary removeChannel(CorChannel corChannel) {
        this.channels.remove(corChannel);

        return this;
    }

    public Set<CorImageItem> getImageItems() {
        return imageItems;
    }

    public void setImageItems(Set<CorImageItem> imageItems) {
        this.imageItems = imageItems;
    }

    public LibLibrary addImageItems(CorImageItem imageItems) {
        this.imageItems.add(imageItems);
        return this;
    }

    public LibLibrary removeImageItems(CorImageItem imageItems) {
        this.imageItems.remove(imageItems);
        return this;
    }

    public LibLibrary mainImage(CorImageItem mainImage) {
        this.mainImage = mainImage;
        return this;
    }

    public CorImageItem getMainImage() {
        return mainImage;
    }

    public void setMainImage(CorImageItem mainImage) {
        this.mainImage = mainImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibLibrary libLibrary = (LibLibrary) o;
        if (libLibrary.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libLibrary.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibLibrary{" +
                "id=" + id +
                ", prefix='" + prefix + "'" +
                ", shortcut='" + shortcut + "'" +
                ", name='" + name + "'" +
                ", counter='" + counter + "'" +
                ", description='" + description + "'" +
                '}';
    }


}
