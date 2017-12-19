package io.protone.library.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorLibrary;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A LibMediaLibrary.
 */
@Entity
@Table(name = "lib_media_library", uniqueConstraints = @UniqueConstraint(columnNames = {"prefix", "shortcut", "network_id"}))

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibMediaLibrary extends CorLibrary implements Serializable {

    @OneToOne
    @PodamExclude
    private CorImageItem mainImage;


    @OneToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "lib_media_library_channel",
            joinColumns = @JoinColumn(name = "lib_media_library_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "channels_id", referencedColumnName = "id"))
    @PodamExclude
    private Set<CorChannel> channels = new HashSet<>();

    @PodamExclude
    @OneToMany
    @JoinTable(
            name = "lib_media_library_cor_image_item",
            joinColumns = @JoinColumn(name = "lib_media_library_id"),
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

    public LibMediaLibrary prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public LibMediaLibrary shortcut(String shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibMediaLibrary name(String name) {
        this.name = name;
        return this;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public LibMediaLibrary counter(Long counter) {
        this.counter = counter;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibMediaLibrary description(String description) {
        this.description = description;
        return this;
    }

    public Set<CorChannel> getChannels() {
        return channels;
    }

    public void setChannels(Set<CorChannel> corChannels) {
        this.channels = corChannels;
    }

    public LibMediaLibrary channels(Set<CorChannel> corChannels) {
        this.channels = corChannels;
        return this;
    }

    public LibMediaLibrary addChannel(CorChannel corChannel) {
        this.channels.add(corChannel);

        return this;
    }

    public LibMediaLibrary removeChannel(CorChannel corChannel) {
        this.channels.remove(corChannel);

        return this;
    }

    public Set<CorImageItem> getImageItems() {
        return imageItems;
    }

    public void setImageItems(Set<CorImageItem> imageItems) {
        this.imageItems = imageItems;
    }

    public LibMediaLibrary addImageItems(CorImageItem imageItems) {
        this.imageItems.add(imageItems);
        return this;
    }

    public LibMediaLibrary removeImageItems(CorImageItem imageItems) {
        this.imageItems.remove(imageItems);
        return this;
    }


    public LibMediaLibrary mainImage(CorImageItem mainImage) {
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
        LibMediaLibrary libMediaLibrary = (LibMediaLibrary) o;
        if (libMediaLibrary.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libMediaLibrary.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibMediaLibrary{" +
                "id=" + id +
                ", prefix='" + prefix + "'" +
                ", shortcut='" + shortcut + "'" +
                ", name='" + name + "'" +
                ", counter='" + counter + "'" +
                ", description='" + description + "'" +
                '}';
    }


}
