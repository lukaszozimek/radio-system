package io.protone.traffic.api.dto;

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
public class TraPriceDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    @NotNull
    private String name;
    private LocalDate validFrom;
    private LocalDate validTo;
    private BigDecimal basePrice;
    private BigDecimal priceLastPostion;
    private BigDecimal priceFirstPostion;
    private Integer baseLength;
    private Map<Long, Double> lenghtMultiplier = new HashMap<>();

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

    public TraPriceDTO name(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public TraPriceDTO validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public TraPriceDTO validTo(LocalDate validTo) {
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


    public Integer getBaseLength() {
        return baseLength;
    }

    public void setBaseLength(Integer baseLength) {
        this.baseLength = baseLength;
    }

    public TraPriceDTO baseLength(Integer baseLength) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraPriceDTO traPrice = (TraPriceDTO) o;
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
        return "TraPriceDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", basePrice=" + basePrice +
                ", priceLastPostion=" + priceLastPostion +
                ", priceFirstPostion=" + priceFirstPostion +
                ", baseLength=" + baseLength +
                ", lenghtMultiplier=" + lenghtMultiplier +
                '}';
    }
}
