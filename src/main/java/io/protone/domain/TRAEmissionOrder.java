package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A TRAEmissionOrder.
 */
@Entity
@Table(name = "tra_emission_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TRAEmissionOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "calculated_price", precision=10, scale=2)
    private BigDecimal calculatedPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCalculatedPrice() {
        return calculatedPrice;
    }

    public TRAEmissionOrder calculatedPrice(BigDecimal calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
        return this;
    }

    public void setCalculatedPrice(BigDecimal calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TRAEmissionOrder tRAEmissionOrder = (TRAEmissionOrder) o;
        if (tRAEmissionOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tRAEmissionOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRAEmissionOrder{" +
            "id=" + id +
            ", calculatedPrice='" + calculatedPrice + "'" +
            '}';
    }
}
