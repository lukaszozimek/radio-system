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
 * A CORSubscriptionOwner.
 */
@Entity
@Table(name = "cor_subscription_owner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORSubscriptionOwner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToOne
    @JoinColumn(unique = true)
    private CORUser user;

    @OneToMany(mappedBy = "cORSubscriptionOwner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CORSubscription> subscriptions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CORSubscriptionOwner name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CORSubscriptionOwner network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public CORUser getUser() {
        return user;
    }

    public CORSubscriptionOwner user(CORUser cORUser) {
        this.user = cORUser;
        return this;
    }

    public void setUser(CORUser cORUser) {
        this.user = cORUser;
    }

    public Set<CORSubscription> getSubscriptions() {
        return subscriptions;
    }

    public CORSubscriptionOwner subscriptions(Set<CORSubscription> cORSubscriptions) {
        this.subscriptions = cORSubscriptions;
        return this;
    }

    public CORSubscriptionOwner addSubscriptions(CORSubscription cORSubscription) {
        subscriptions.add(cORSubscription);
        cORSubscription.setCORSubscriptionOwner(this);
        return this;
    }

    public CORSubscriptionOwner removeSubscriptions(CORSubscription cORSubscription) {
        subscriptions.remove(cORSubscription);
        cORSubscription.setCORSubscriptionOwner(null);
        return this;
    }

    public void setSubscriptions(Set<CORSubscription> cORSubscriptions) {
        this.subscriptions = cORSubscriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORSubscriptionOwner cORSubscriptionOwner = (CORSubscriptionOwner) o;
        if (cORSubscriptionOwner.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORSubscriptionOwner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORSubscriptionOwner{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
