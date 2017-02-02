package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A SCHBlockPrice.
 */
@Entity
@Table(name = "sch_block_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SCHBlockPrice implements Serializable {

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

    public SCHBlockPrice calculatedPrice(BigDecimal calculatedPrice) {
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
        SCHBlockPrice sCHBlockPrice = (SCHBlockPrice) o;
        if (sCHBlockPrice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sCHBlockPrice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SCHBlockPrice{" +
            "id=" + id +
            ", calculatedPrice='" + calculatedPrice + "'" +
            '}';
    }
}
