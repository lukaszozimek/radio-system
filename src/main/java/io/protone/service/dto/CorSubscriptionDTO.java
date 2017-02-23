package io.protone.service.dto;


import java.io.Serializable;
import java.util.Objects;
import io.protone.domain.enumeration.CorSubscriptionTypeEnum;

/**
 * A DTO for the CorSubscription entity.
 */
public class CorSubscriptionDTO implements Serializable {

    private Long id;

    private String name;

    private Long price;

    private CorSubscriptionTypeEnum subscription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
    public CorSubscriptionTypeEnum getSubscription() {
        return subscription;
    }

    public void setSubscription(CorSubscriptionTypeEnum subscription) {
        this.subscription = subscription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorSubscriptionDTO corSubscriptionDTO = (CorSubscriptionDTO) o;

        if ( ! Objects.equals(id, corSubscriptionDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorSubscriptionDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", subscription='" + subscription + "'" +
            '}';
    }
}
