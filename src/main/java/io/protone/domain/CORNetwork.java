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

/**
 * A CORNetwork.
 */
@Entity
@Table(name = "cor_network")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORNetwork implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "shortcut", length = 100, nullable = false)
    private String shortcut;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "cORNetwork")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CORUser> users = new HashSet<>();

    @OneToMany(mappedBy = "cORNetwork")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CORChannel> channels = new HashSet<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortcut() {
        return shortcut;
    }

    public CORNetwork shortcut(String shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public CORNetwork name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CORNetwork description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CORUser> getUsers() {
        return users;
    }

    public CORNetwork users(Set<CORUser> cORUsers) {
        this.users = cORUsers;
        return this;
    }

    public CORNetwork addUsers(CORUser cORUser) {
        users.add(cORUser);
        cORUser.setCORNetwork(this);
        return this;
    }

    public CORNetwork removeUsers(CORUser cORUser) {
        users.remove(cORUser);
        cORUser.setCORNetwork(null);
        return this;
    }

    public void setUsers(Set<CORUser> cORUsers) {
        this.users = cORUsers;
    }

    public Set<CORChannel> getChannels() {
        return channels;
    }

    public CORNetwork channels(Set<CORChannel> cORChannels) {
        this.channels = cORChannels;
        return this;
    }

    public CORNetwork addChannel(CORChannel cORChannel) {
        channels.add(cORChannel);
        cORChannel.setCORNetwork(this);
        return this;
    }

    public CORNetwork removeChannel(CORChannel cORChannel) {
        channels.remove(cORChannel);
        cORChannel.setCORNetwork(null);
        return this;
    }

    public void setChannels(Set<CORChannel> cORChannels) {
        this.channels = cORChannels;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORNetwork cORNetwork = (CORNetwork) o;
        if (cORNetwork.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORNetwork.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORNetwork{" +
            "id=" + id +
            ", shortcut='" + shortcut + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
