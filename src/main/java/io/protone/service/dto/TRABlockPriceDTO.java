package io.protone.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * A DTO for the TRABlockPrice entity.
 */
public class TRABlockPriceDTO implements Serializable {

    private Long id;

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

        TRABlockPriceDTO tRABlockPriceDTO = (TRABlockPriceDTO) o;

        if ( ! Objects.equals(id, tRABlockPriceDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRABlockPriceDTO{" +
            "id=" + id +
            ", calculatedPrice='" + calculatedPrice + "'" +
            '}';
    }
}
