package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TraDiscount.
 */
@Entity
@Table(name = "tra_discount")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraDiscount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "discount")
    private Long discount;

    @ManyToOne
    private CorNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public TraDiscount validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public TraDiscount validTo(LocalDate validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public Long getDiscount() {
        return discount;
    }

    public TraDiscount discount(Long discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public TraDiscount network(CorNetwork corNetwork) {
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
        TraDiscount traDiscount = (TraDiscount) o;
        if (traDiscount.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traDiscount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraDiscount{" +
            "id=" + id +
            ", validFrom='" + validFrom + "'" +
            ", validTo='" + validTo + "'" +
            ", discount='" + discount + "'" +
            '}';
    }
}
