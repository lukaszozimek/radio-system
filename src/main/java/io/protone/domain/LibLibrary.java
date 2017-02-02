package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.protone.domain.enumeration.CORCounterTypeEnum;

import io.protone.domain.enumeration.LIBObjectTypeEnum;

/**
 * A LibLibrary.
 */
@Entity
@Table(name = "lib_library")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibLibrary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 1)
    @Column(name = "prefix", length = 1, nullable = false)
    private String prefix;

    @NotNull
    @Column(name = "idx_length", nullable = false)
    private Integer idxLength;

    @NotNull
    @Size(max = 3)
    @Column(name = "shortcut", length = 3, nullable = false)
    private String shortcut;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(name = "counter", nullable = false)
    private Long counter;

    @Enumerated(EnumType.STRING)
    @Column(name = "counter_type")
    private CORCounterTypeEnum counterType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "library_type", nullable = false)
    private LIBObjectTypeEnum libraryType;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToMany(mappedBy = "libLibrary")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CORChannel> channels = new HashSet<>();

    @OneToMany(mappedBy = "libLibrary")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CORUser> users = new HashSet<>();

    @OneToMany(mappedBy = "libLibrary")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LIBMediaItem> items = new HashSet<>();

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

    public Integer getIdxLength() {
        return idxLength;
    }

    public LibLibrary idxLength(Integer idxLength) {
        this.idxLength = idxLength;
        return this;
    }

    public void setIdxLength(Integer idxLength) {
        this.idxLength = idxLength;
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

    public CORCounterTypeEnum getCounterType() {
        return counterType;
    }

    public LibLibrary counterType(CORCounterTypeEnum counterType) {
        this.counterType = counterType;
        return this;
    }

    public void setCounterType(CORCounterTypeEnum counterType) {
        this.counterType = counterType;
    }

    public LIBObjectTypeEnum getLibraryType() {
        return libraryType;
    }

    public LibLibrary libraryType(LIBObjectTypeEnum libraryType) {
        this.libraryType = libraryType;
        return this;
    }

    public void setLibraryType(LIBObjectTypeEnum libraryType) {
        this.libraryType = libraryType;
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

    public CORNetwork getNetwork() {
        return network;
    }

    public LibLibrary network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public Set<CORChannel> getChannels() {
        return channels;
    }

    public LibLibrary channels(Set<CORChannel> cORChannels) {
        this.channels = cORChannels;
        return this;
    }

    public LibLibrary addChannel(CORChannel cORChannel) {
        channels.add(cORChannel);
        cORChannel.setLibLibrary(this);
        return this;
    }

    public LibLibrary removeChannel(CORChannel cORChannel) {
        channels.remove(cORChannel);
        cORChannel.setLibLibrary(null);
        return this;
    }

    public void setChannels(Set<CORChannel> cORChannels) {
        this.channels = cORChannels;
    }

    public Set<CORUser> getUsers() {
        return users;
    }

    public LibLibrary users(Set<CORUser> cORUsers) {
        this.users = cORUsers;
        return this;
    }

    public LibLibrary addUser(CORUser cORUser) {
        users.add(cORUser);
        cORUser.setLibLibrary(this);
        return this;
    }

    public LibLibrary removeUser(CORUser cORUser) {
        users.remove(cORUser);
        cORUser.setLibLibrary(null);
        return this;
    }

    public void setUsers(Set<CORUser> cORUsers) {
        this.users = cORUsers;
    }

    public Set<LIBMediaItem> getItems() {
        return items;
    }

    public LibLibrary items(Set<LIBMediaItem> lIBMediaItems) {
        this.items = lIBMediaItems;
        return this;
    }

    public LibLibrary addItem(LIBMediaItem lIBMediaItem) {
        items.add(lIBMediaItem);
        lIBMediaItem.setLibLibrary(this);
        return this;
    }

    public LibLibrary removeItem(LIBMediaItem lIBMediaItem) {
        items.remove(lIBMediaItem);
        lIBMediaItem.setLibLibrary(null);
        return this;
    }

    public void setItems(Set<LIBMediaItem> lIBMediaItems) {
        this.items = lIBMediaItems;
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
            ", idxLength='" + idxLength + "'" +
            ", shortcut='" + shortcut + "'" +
            ", name='" + name + "'" +
            ", counter='" + counter + "'" +
            ", counterType='" + counterType + "'" +
            ", libraryType='" + libraryType + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
