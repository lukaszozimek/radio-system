package io.protone.application.web.rest.dto.traffic;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the TraDiscount entity.
 */
public class TraDiscountDTO implements Serializable {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("validFrom")
    private LocalDate validFrom;

    @JsonProperty("validTo")
    private LocalDate validTo;

    @JsonProperty("discount")
    private Long discount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TraDiscountDTO traDiscountDTO = (TraDiscountDTO) o;

        if (!Objects.equals(id, traDiscountDTO.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraDiscountDTO{" +
            "id=" + id +
            ", validFrom='" + validFrom + "'" +
            ", validTo='" + validTo + "'" +
            ", discount='" + discount + "'" +
            '}';
    }
}
