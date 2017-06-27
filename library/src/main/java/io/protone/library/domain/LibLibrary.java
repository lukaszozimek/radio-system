package io.protone.library.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
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
        name = "lib_library_lib_image_item",
        joinColumns = @JoinColumn(name = "lib_library_id"),
        inverseJoinColumns = @JoinColumn(name = "lib_image_item_id")
    )
    private Set<LibImageItem> imageItems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public LibLibrary prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getShortcut() {
        return shortcut;
    }

    public LibLibrary shortcut(String shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public LibLibrary name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCounter() {
        return counter;
    }

    public LibLibrary counter(Long counter) {
        this.counter = counter;
        return this;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public String getDescription() {
        return description;
    }

    public LibLibrary description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public LibLibrary network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public Set<CorChannel> getChannels() {
        return channels;
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

    public void setChannels(Set<CorChannel> corChannels) {
        this.channels = corChannels;
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
