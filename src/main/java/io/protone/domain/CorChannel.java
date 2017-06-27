package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A CorChannel.
 */
@Entity
@Table(name = "cor_channel", uniqueConstraints =
@UniqueConstraint(columnNames = {"shortcut", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorChannel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "shortcut", length = 3, nullable = false)
    private String shortcut;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @PodamExclude
    @JsonIgnore
    private CorNetwork network;

    @ManyToMany(mappedBy = "channels")
    @JsonIgnore
    @PodamExclude
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CorUser> channelUsers = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public CorChannel shortcut(String shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CorChannel name(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CorChannel description(String description) {
        this.description = description;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CorChannel network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public Set<CorUser> getChannelUsers() {
        return channelUsers;
    }

    public void setChannelUsers(Set<CorUser> corUsers) {
        this.channelUsers = corUsers;
    }

    public CorChannel channelUsers(Set<CorUser> corUsers) {
        this.channelUsers = corUsers;
        return this;
    }

    public CorChannel addChannelUsers(CorUser corUser) {
        this.channelUsers.add(corUser);
        corUser.getChannels().add(this);
        return this;
    }

    public CorChannel removeChannelUsers(CorUser corUser) {
        this.channelUsers.remove(corUser);
        corUser.getChannels().remove(this);
        return this;
    }

    public CorChannel removeChannelLibarary(LibLibrary libLibrary) {
        this.channelUsers.remove(libLibrary);
        libLibrary.getChannels().remove(this);
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
        CorChannel corChannel = (CorChannel) o;
        if (corChannel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corChannel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorChannel{" +
            "id=" + id +
            ", shortcut='" + shortcut + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
