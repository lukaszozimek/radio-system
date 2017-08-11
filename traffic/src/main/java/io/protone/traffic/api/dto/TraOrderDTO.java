package io.protone.traffic.api.dto;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.traffic.api.dto.thin.TraAdvertisementThinDTO;
import io.protone.traffic.api.dto.thin.TraCustomerThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TraOrderDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraOrderDTO {

    private Long calculatedPrize = null;

    private Long price = null;

    private Long campaignId = null;

    @NotNull
    private TraCustomerThinDTO customerId = null;

    private LocalDate endDate = null;

    private Long id = null;

    @NotNull
    private String name = null;

    private LocalDate startDate = null;

    @NotNull
    private TraAdvertisementThinDTO advertismentId = null;

    private CorDictionaryDTO statusId = null;

    private List<TraEmissionDTO> emissions = new ArrayList<TraEmissionDTO>();

    private Long invoiceId = null;

    private CoreUserThinDTO createdBy;

    private ZonedDateTime createdDate;

    private CoreUserThinDTO lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    public TraOrderDTO calculatedPrize(Long calculatedPrize) {
        this.calculatedPrize = calculatedPrize;
        return this;
    }

    /**
     * Get calculatedPrize
     *
     * @return calculatedPrize
     **/
    @ApiModelProperty(value = "")
    public Long getCalculatedPrize() {
        return calculatedPrize;
    }

    public void setCalculatedPrize(Long calculatedPrize) {
        this.calculatedPrize = calculatedPrize;
    }

    public TraOrderDTO traCampaign(Long campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    /**
     * Get campaignId
     *
     * @return campaignId
     **/
    @ApiModelProperty(value = "")
    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }


    public TraOrderDTO customerId(TraCustomerThinDTO customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * Get customerId
     *
     * @return customerId
     **/
    @ApiModelProperty(value = "")
    public TraCustomerThinDTO getCustomerId() {
        return customerId;
    }

    public void setCustomerId(TraCustomerThinDTO customerId) {
        this.customerId = customerId;
    }

    public TraOrderDTO endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
     * Get endDate
     *
     * @return endDate
     **/
    @ApiModelProperty(value = "")
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TraOrderDTO id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TraOrderDTO name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(required = true, value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TraOrderDTO startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Get trainvoice
     *
     * @return status
     **/
    @ApiModelProperty(required = true, value = "")
    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long traInvoicePT) {
        this.invoiceId = traInvoicePT;
    }

    public TraOrderDTO invoiceId(Long traInvoicePT) {
        this.invoiceId = traInvoicePT;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @ApiModelProperty(required = true, value = "")
    public CorDictionaryDTO getStatusId() {
        return statusId;
    }

    public void setStatusId(CorDictionaryDTO traOrderStatus) {
        this.statusId = traOrderStatus;
    }

    public TraOrderDTO statusId(CorDictionaryDTO traOrderStatus) {
        this.statusId = traOrderStatus;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @ApiModelProperty(required = true, value = "")
    public TraAdvertisementThinDTO getAdvertismentId() {
        return advertismentId;
    }

    public void setAdvertismentId(TraAdvertisementThinDTO advertisment) {
        this.advertismentId = advertisment;
    }

    public TraOrderDTO advertismentId(TraAdvertisementThinDTO advertisment) {
        this.advertismentId = advertisment;
        return this;
    }

    /**
     * Get emissions
     *
     * @return status
     **/
    @ApiModelProperty(required = true, value = "")
    public List<TraEmissionDTO> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<TraEmissionDTO> emissions) {
        this.emissions = emissions;
    }

    public TraOrderDTO addEmission(TraEmissionDTO emission) {
        this.emissions.add(emission);
        return this;
    }

    public TraOrderDTO removeEmission(TraEmissionDTO emission) {
        this.emissions.remove(emission);
        return this;
    }

    public TraOrderDTO emissions(List<TraEmissionDTO> emissions) {
        this.emissions = emissions;
        return this;
    }

    /**
     * Get startDate
     *
     * @return startDate
     **/
    @ApiModelProperty(value = "")
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public CoreUserThinDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CoreUserThinDTO createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public CoreUserThinDTO getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(CoreUserThinDTO lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraOrderDTO that = (TraOrderDTO) o;

        if (calculatedPrize != null ? !calculatedPrize.equals(that.calculatedPrize) : that.calculatedPrize != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (campaignId != null ? !campaignId.equals(that.campaignId) : that.campaignId != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (advertismentId != null ? !advertismentId.equals(that.advertismentId) : that.advertismentId != null)
            return false;
        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        if (emissions != null ? !emissions.equals(that.emissions) : that.emissions != null) return false;
        if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastModifiedBy != null ? !lastModifiedBy.equals(that.lastModifiedBy) : that.lastModifiedBy != null)
            return false;
        if (lastModifiedDate != null ? !lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = calculatedPrize != null ? calculatedPrize.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (campaignId != null ? campaignId.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (advertismentId != null ? advertismentId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (emissions != null ? emissions.hashCode() : 0);
        result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraOrderDTO{" +
                "calculatedPrize=" + calculatedPrize +
                ", price=" + price +
                ", campaignId=" + campaignId +
                ", customerId=" + customerId +
                ", endDate=" + endDate +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", advertismentId=" + advertismentId +
                ", statusId=" + statusId +
                ", emissions=" + emissions +
                ", invoiceId=" + invoiceId +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}

