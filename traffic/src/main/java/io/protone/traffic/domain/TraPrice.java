package io.protone.traffic.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorNetwork;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A TraPrice.
 */
@Entity
@Table(name = "tra_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraPrice extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "lenght")
    @Column(name = "multiplier")
    @CollectionTable(name = "tra_price_lenght_multiplier")
    Map<Long, Double> lenghtMultiplier = new HashMap<>();
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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
    @Column(name = "base_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal basePrice;
    @NotNull
    @Column(name = "price_last_postion", precision = 10, scale = 2, nullable = false)
    private BigDecimal priceLastPostion;
    @NotNull
    @Column(name = "price_first_postion", precision = 10, scale = 2, nullable = false)
    private BigDecimal priceFirstPostion;
    @Column(name = "base_length")
    private Long baseLength;
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

    public void setName(String name) {
        this.name = name;
    }

    public TraPrice name(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public TraPrice validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public TraPrice validTo(LocalDate validTo) {
        this.validTo = validTo;
        return this;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Map<Long, Double> getLenghtMultiplier() {
        return lenghtMultiplier;
    }

    public void setLenghtMultiplier(Map<Long, Double> lenghtMultiplier) {
        this.lenghtMultiplier = lenghtMultiplier;
    }

    public TraPrice putToLenghtMultiplier(Long lenght, Double multiplier) {
        this.lenghtMultiplier.put(lenght, multiplier);
        return this;
    }

    public TraPrice removeToLenghtMultiplier(Long lenght) {
        this.lenghtMultiplier.remove(lenght);
        return this;
    }

    public Long getBaseLength() {
        return baseLength;
    }

    public void setBaseLength(Long baseLength) {
        this.baseLength = baseLength;
    }

    public TraPrice baseLength(Long baseLength) {
        this.baseLength = baseLength;
        return this;
    }


    public BigDecimal getPriceLastPostion() {
        return priceLastPostion;
    }

    public void setPriceLastPostion(BigDecimal priceLastPostion) {
        this.priceLastPostion = priceLastPostion;
    }

    public BigDecimal getPriceFirstPostion() {
        return priceFirstPostion;
    }

    public void setPriceFirstPostion(BigDecimal priceFirstPostion) {
        this.priceFirstPostion = priceFirstPostion;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraPrice network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
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
                "lenghtMultiplier=" + lenghtMultiplier +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", basePrice=" + basePrice +
                ", priceLastPostion=" + priceLastPostion +
                ", priceFirstPostion=" + priceFirstPostion +
                ", baseLength=" + baseLength +
                ", network=" + network +
                '}';
    }


    public TraPrice basePrice(BigDecimal defaultBasePrice) {
        this.basePrice = defaultBasePrice;
        return this;
    }

    public TraPrice priceLastPostion(BigDecimal defaultPriceLastPostion) {
        this.priceLastPostion = defaultPriceLastPostion;
        return this;
    }

    public TraPrice priceFirstPostion(BigDecimal defaultPriceFirstPostion) {
        this.priceFirstPostion = defaultPriceFirstPostion;
        return this;
    }

}
