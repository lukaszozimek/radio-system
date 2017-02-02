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
 * A CORChannel.
 */
@Entity
@Table(name = "cor_channel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "prefix", length = 3, nullable = false)
    private String prefix;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private CORNetwork cORNetwork;

    @OneToMany(mappedBy = "cORChannel")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CORUser> users = new HashSet<>();

    @ManyToOne
    private CORUser user;

    @ManyToOne
    private LibLibrary libLibrary;

    @ManyToOne
    private CORUser cORUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public CORChannel prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public CORChannel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CORChannel description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CORNetwork getCORNetwork() {
        return cORNetwork;
    }

    public CORChannel cORNetwork(CORNetwork cORNetwork) {
        this.cORNetwork = cORNetwork;
        return this;
    }

    public void setCORNetwork(CORNetwork cORNetwork) {
        this.cORNetwork = cORNetwork;
    }

    public Set<CORUser> getUsers() {
        return users;
    }

    public CORChannel users(Set<CORUser> cORUsers) {
        this.users = cORUsers;
        return this;
    }

    public CORChannel addUsers(CORUser cORUser) {
        users.add(cORUser);
        cORUser.setCORChannel(this);
        return this;
    }

    public CORChannel removeUsers(CORUser cORUser) {
        users.remove(cORUser);
        cORUser.setCORChannel(null);
        return this;
    }

    public void setUsers(Set<CORUser> cORUsers) {
        this.users = cORUsers;
    }

    public CORUser getUser() {
        return user;
    }

    public CORChannel user(CORUser cORUser) {
        this.user = cORUser;
        return this;
    }

    public void setUser(CORUser cORUser) {
        this.user = cORUser;
    }

    public LibLibrary getLibLibrary() {
        return libLibrary;
    }

    public CORChannel libLibrary(LibLibrary libLibrary) {
        this.libLibrary = libLibrary;
        return this;
    }

    public void setLibLibrary(LibLibrary libLibrary) {
        this.libLibrary = libLibrary;
    }

    public CORUser getCORUser() {
        return cORUser;
    }

    public CORChannel cORUser(CORUser cORUser) {
        this.cORUser = cORUser;
        return this;
    }

    public void setCORUser(CORUser cORUser) {
        this.cORUser = cORUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORChannel cORChannel = (CORChannel) o;
        if (cORChannel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORChannel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORChannel{" +
            "id=" + id +
            ", prefix='" + prefix + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
