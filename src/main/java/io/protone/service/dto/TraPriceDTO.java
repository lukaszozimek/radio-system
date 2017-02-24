package io.protone.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TraPrice entity.
 */
public class TraPriceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    private LocalDate validFrom;

    private LocalDate validTo;

    @NotNull
    private BigDecimal price;

    private Integer baseLength;

    @NotNull
    private BigDecimal priceAlternative;

    private Long currencyId;

    private Long networkId;

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
    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }
    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Integer getBaseLength() {
        return baseLength;
    }

    public void setBaseLength(Integer baseLength) {
        this.baseLength = baseLength;
    }
    public BigDecimal getPriceAlternative() {
        return priceAlternative;
    }

    public void setPriceAlternative(BigDecimal priceAlternative) {
        this.priceAlternative = priceAlternative;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long corCurrencyId) {
        this.currencyId = corCurrencyId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TraPriceDTO traPriceDTO = (TraPriceDTO) o;

        if ( ! Objects.equals(id, traPriceDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraPriceDTO{" +
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