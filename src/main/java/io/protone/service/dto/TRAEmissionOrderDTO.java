package io.protone.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * A DTO for the TRAEmissionOrder entity.
 */
public class TRAEmissionOrderDTO implements Serializable {

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

        TRAEmissionOrderDTO tRAEmissionOrderDTO = (TRAEmissionOrderDTO) o;

        if ( ! Objects.equals(id, tRAEmissionOrderDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRAEmissionOrderDTO{" +
            "id=" + id +
            ", calculatedPrice='" + calculatedPrice + "'" +
            '}';
    }
}
