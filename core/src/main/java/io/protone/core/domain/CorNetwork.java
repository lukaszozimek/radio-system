package io.protone.core.domain;

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
 * A CorNetwork.
 */
@Entity
@Table(name = "cor_network")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorNetwork extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "shortcut", length = 100, nullable = false, unique = true)
    private String shortcut;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "networks")
    @JsonIgnore
    @PodamExclude
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CorUser> networkUsers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortcut() {
        return shortcut;
    }

    public CorNetwork shortcut(String shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public CorNetwork name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public CorNetwork active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public CorNetwork description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CorUser> getNetworkUsers() {
        return networkUsers;
    }

    public CorNetwork networkUsers(Set<CorUser> corUsers) {
        this.networkUsers = corUsers;
        return this;
    }

    public CorNetwork addNetworkUsers(CorUser corUser) {
        this.networkUsers.add(corUser);
        corUser.getNetworks().add(this);
        return this;
    }

    public CorNetwork removeNetworkUsers(CorUser corUser) {
        this.networkUsers.remove(corUser);
        corUser.getNetworks().remove(this);
        return this;
    }

    public void setNetworkUsers(Set<CorUser> corUsers) {
        this.networkUsers = corUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CorNetwork corNetwork = (CorNetwork) o;
        if (corNetwork.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corNetwork.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorNetwork{" +
            "id=" + id +
            ", shortcut='" + shortcut + "'" +
            ", name='" + name + "'" +
            ", active='" + active + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
