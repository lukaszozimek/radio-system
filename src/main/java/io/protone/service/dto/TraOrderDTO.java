package io.protone.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TraOrder entity.
 */
public class TraOrderDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long calculatedPrize;

    private Long customerId;

    private Long campaignId;

    private Long priceId;

    private Long networkId;

    private Long statusId;

    private Long invoiceId;

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long crmAccountId) {
        this.customerId = crmAccountId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long traCampaignId) {
        this.campaignId = traCampaignId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long traPriceId) {
        this.priceId = traPriceId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long traOrderStatusId) {
        this.statusId = traOrderStatusId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long traInvoiceId) {
        this.invoiceId = traInvoiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TraOrderDTO traOrderDTO = (TraOrderDTO) o;

        if ( ! Objects.equals(id, traOrderDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraOrderDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", calculatedPrize='" + calculatedPrize + "'" +
            '}';
    }
}
