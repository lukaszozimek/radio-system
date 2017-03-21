package io.protone.domain;

import io.protone.domain.enumeration.CorSubscriptionTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CorSubscription.
 */
@Entity
@Table(name = "cor_subscription")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription")
    private CorSubscriptionTypeEnum subscription;

    @ManyToOne
    private CorNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CorSubscription name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public CorSubscription price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public CorSubscriptionTypeEnum getSubscription() {
        return subscription;
    }

    public CorSubscription subscription(CorSubscriptionTypeEnum subscription) {
        this.subscription = subscription;
        return this;
    }

    public void setSubscription(CorSubscriptionTypeEnum subscription) {
        this.subscription = subscription;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CorSubscription network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
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
        CorSubscription corSubscription = (CorSubscription) o;
        if (corSubscription.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corSubscription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorSubscription{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", subscription='" + subscription + "'" +
            '}';
    }
}
