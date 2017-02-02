package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CORUser.
 */
@Entity
@Table(name = "cor_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private CORNetwork cORNetwork;

    @ManyToOne
    private CORChannel cORChannel;

    @ManyToOne
    private LibLibrary libLibrary;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToMany(mappedBy = "cORUser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CORChannel> channels = new HashSet<>();

    @OneToMany(mappedBy = "cORUser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CORNotification> notifications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CORUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CORNetwork getCORNetwork() {
        return cORNetwork;
    }

    public CORUser cORNetwork(CORNetwork cORNetwork) {
        this.cORNetwork = cORNetwork;
        return this;
    }

    public void setCORNetwork(CORNetwork cORNetwork) {
        this.cORNetwork = cORNetwork;
    }

    public CORChannel getCORChannel() {
        return cORChannel;
    }

    public CORUser cORChannel(CORChannel cORChannel) {
        this.cORChannel = cORChannel;
        return this;
    }

    public void setCORChannel(CORChannel cORChannel) {
        this.cORChannel = cORChannel;
    }

    public LibLibrary getLibLibrary() {
        return libLibrary;
    }

    public CORUser libLibrary(LibLibrary libLibrary) {
        this.libLibrary = libLibrary;
        return this;
    }

    public void setLibLibrary(LibLibrary libLibrary) {
        this.libLibrary = libLibrary;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CORUser network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public Set<CORChannel> getChannels() {
        return channels;
    }

    public CORUser channels(Set<CORChannel> cORChannels) {
        this.channels = cORChannels;
        return this;
    }

    public CORUser addChannel(CORChannel cORChannel) {
        channels.add(cORChannel);
        cORChannel.setCORUser(this);
        return this;
    }

    public CORUser removeChannel(CORChannel cORChannel) {
        channels.remove(cORChannel);
        cORChannel.setCORUser(null);
        return this;
    }

    public void setChannels(Set<CORChannel> cORChannels) {
        this.channels = cORChannels;
    }

    public Set<CORNotification> getNotifications() {
        return notifications;
    }

    public CORUser notifications(Set<CORNotification> cORNotifications) {
        this.notifications = cORNotifications;
        return this;
    }

    public CORUser addNotifications(CORNotification cORNotification) {
        notifications.add(cORNotification);
        cORNotification.setCORUser(this);
        return this;
    }

    public CORUser removeNotifications(CORNotification cORNotification) {
        notifications.remove(cORNotification);
        cORNotification.setCORUser(null);
        return this;
    }

    public void setNotifications(Set<CORNotification> cORNotifications) {
        this.notifications = cORNotifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORUser cORUser = (CORUser) o;
        if (cORUser.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORUser{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
