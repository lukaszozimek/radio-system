package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TraPrice.
 */
@Entity
@Table(name = "tra_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraPrice implements Serializable {

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
    private CorCurrency currency;

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

    public TraPrice name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public TraPrice validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public TraPrice validTo(LocalDate validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TraPrice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getBaseLength() {
        return baseLength;
    }

    public TraPrice baseLength(Integer baseLength) {
        this.baseLength = baseLength;
        return this;
    }

    public void setBaseLength(Integer baseLength) {
        this.baseLength = baseLength;
    }

    public BigDecimal getPriceAlternative() {
        return priceAlternative;
    }

    public TraPrice priceAlternative(BigDecimal priceAlternative) {
        this.priceAlternative = priceAlternative;
        return this;
    }

    public void setPriceAlternative(BigDecimal priceAlternative) {
        this.priceAlternative = priceAlternative;
    }

    public CorCurrency getCurrency() {
        return currency;
    }

    public TraPrice currency(CorCurrency corCurrency) {
        this.currency = corCurrency;
        return this;
    }

    public void setCurrency(CorCurrency corCurrency) {
        this.currency = corCurrency;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public TraPrice network(CorNetwork corNetwork) {
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
        TraPrice traPrice = (TraPrice) o;
        if (traPrice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traPrice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraPrice{" +
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
