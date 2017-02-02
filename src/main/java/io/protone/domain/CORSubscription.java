package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import io.protone.domain.enumeration.CORCSubscriptionTypeEnum;

/**
 * A CORSubscription.
 */
@Entity
@Table(name = "cor_subscription")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription")
    private CORCSubscriptionTypeEnum subscription;

    @ManyToOne
    private CORSubscriptionOwner cORSubscriptionOwner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CORSubscription name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public CORSubscription price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public CORCSubscriptionTypeEnum getSubscription() {
        return subscription;
    }

    public CORSubscription subscription(CORCSubscriptionTypeEnum subscription) {
        this.subscription = subscription;
        return this;
    }

    public void setSubscription(CORCSubscriptionTypeEnum subscription) {
        this.subscription = subscription;
    }

    public CORSubscriptionOwner getCORSubscriptionOwner() {
        return cORSubscriptionOwner;
    }

    public CORSubscription cORSubscriptionOwner(CORSubscriptionOwner cORSubscriptionOwner) {
        this.cORSubscriptionOwner = cORSubscriptionOwner;
        return this;
    }

    public void setCORSubscriptionOwner(CORSubscriptionOwner cORSubscriptionOwner) {
        this.cORSubscriptionOwner = cORSubscriptionOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORSubscription cORSubscription = (CORSubscription) o;
        if (cORSubscription.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORSubscription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORSubscription{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", subscription='" + subscription + "'" +
            '}';
    }
}
