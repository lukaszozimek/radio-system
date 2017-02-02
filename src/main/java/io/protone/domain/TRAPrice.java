package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TRAPrice.
 */
@Entity
@Table(name = "tra_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TRAPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @NotNull
    @Column(name = "price", precision=10, scale=2, nullable = false)
    private BigDecimal price;

    @Column(name = "base_length")
    private Integer baseLength;

    @NotNull
    @Column(name = "price_alternative", precision=10, scale=2, nullable = false)
    private BigDecimal priceAlternative;

    @OneToOne
    @JoinColumn(unique = true)
    private CORCurrency currency;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TRAPrice name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public TRAPrice validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public TRAPrice validTo(LocalDate validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TRAPrice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getBaseLength() {
        return baseLength;
    }

    public TRAPrice baseLength(Integer baseLength) {
        this.baseLength = baseLength;
        return this;
    }

    public void setBaseLength(Integer baseLength) {
        this.baseLength = baseLength;
    }

    public BigDecimal getPriceAlternative() {
        return priceAlternative;
    }

    public TRAPrice priceAlternative(BigDecimal priceAlternative) {
        this.priceAlternative = priceAlternative;
        return this;
    }

    public void setPriceAlternative(BigDecimal priceAlternative) {
        this.priceAlternative = priceAlternative;
    }

    public CORCurrency getCurrency() {
        return currency;
    }

    public TRAPrice currency(CORCurrency cORCurrency) {
        this.currency = cORCurrency;
        return this;
    }

    public void setCurrency(CORCurrency cORCurrency) {
        this.currency = cORCurrency;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public TRAPrice network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TRAPrice tRAPrice = (TRAPrice) o;
        if (tRAPrice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tRAPrice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRAPrice{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", validFrom='" + validFrom + "'" +
            ", validTo='" + validTo + "'" +
            ", price='" + price + "'" +
            ", baseLength='" + baseLength + "'" +
            ", priceAlternative='" + priceAlternative + "'" +
            '}';
    }
}
