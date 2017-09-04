package io.protone.library.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorLibrary;
import io.protone.core.domain.CorNetwork;
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
@Table(name = "lib_file_library", uniqueConstraints = @UniqueConstraint(columnNames = {"prefix", "shortcut", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibFileLibrary extends CorLibrary implements Serializable {


    @OneToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "lib_file_library_channel",
            joinColumns = @JoinColumn(name = "lib_file_library_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "channels_id", referencedColumnName = "id"))
    @PodamExclude
    private Set<CorChannel> channels = new HashSet<>();


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

    public LibFileLibrary prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public LibFileLibrary shortcut(String shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibFileLibrary name(String name) {
        this.name = name;
        return this;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public LibFileLibrary counter(Long counter) {
        this.counter = counter;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibFileLibrary description(String description) {
        this.description = description;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public LibFileLibrary network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public Set<CorChannel> getChannels() {
        return channels;
    }

    public void setChannels(Set<CorChannel> corChannels) {
        this.channels = corChannels;
    }

    public LibFileLibrary channels(Set<CorChannel> corChannels) {
        this.channels = corChannels;
        return this;
    }

    public LibFileLibrary addChannel(CorChannel corChannel) {
        this.channels.add(corChannel);

        return this;
    }

    public LibFileLibrary removeChannel(CorChannel corChannel) {
        this.channels.remove(corChannel);

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
        LibFileLibrary libLibrary = (LibFileLibrary) o;
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
