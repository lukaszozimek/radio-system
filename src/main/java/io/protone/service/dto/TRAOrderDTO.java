package io.protone.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TRAOrder entity.
 */
public class TRAOrderDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long calculatedPrize;


    private Long campaignId;
    
    private Long customerId;
    
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
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public Long getCalculatedPrize() {
        return calculatedPrize;
    }

    public void setCalculatedPrize(Long calculatedPrize) {
        this.calculatedPrize = calculatedPrize;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long tRACampaignId) {
        this.campaignId = tRACampaignId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long tRACustomerId) {
        this.customerId = tRACustomerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TRAOrderDTO tRAOrderDTO = (TRAOrderDTO) o;

        if ( ! Objects.equals(id, tRAOrderDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRAOrderDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", calculatedPrize='" + calculatedPrize + "'" +
            '}';
    }
}
