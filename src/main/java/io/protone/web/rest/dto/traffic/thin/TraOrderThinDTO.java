package io.protone.web.rest.dto.traffic.thin;

import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by lukaszozimek on 19/06/2017.
 */
public class TraOrderThinDTO implements Serializable {

    private Long calculatedPrize = null;

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

    private Long invoiceId = null;

    public TraOrderThinDTO() {
    }

    public TraOrderThinDTO calculatedPrize(Long calculatedPrize) {
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

    public TraOrderThinDTO traCampaign(Long campaignId) {
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


    public TraOrderThinDTO customerId(TraCustomerThinDTO customerId) {
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

    public TraOrderThinDTO endDate(LocalDate endDate) {
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

    public TraOrderThinDTO id(Long id) {
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

    public TraOrderThinDTO name(String name) {
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

    public TraOrderThinDTO startDate(LocalDate startDate) {
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

    public TraOrderThinDTO invoiceId(Long traInvoicePT) {
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

    public TraOrderThinDTO statusId(CorDictionaryDTO traOrderStatus) {
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

    public TraOrderThinDTO advertismentId(TraAdvertisementThinDTO advertisment) {
        this.advertismentId = advertisment;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraOrderThinDTO traOrderDTO = (TraOrderThinDTO) o;
        return Objects.equals(this.calculatedPrize, traOrderDTO.calculatedPrize) &&
            Objects.equals(this.campaignId, traOrderDTO.campaignId) &&
            Objects.equals(this.customerId, traOrderDTO.customerId) &&
            Objects.equals(this.endDate, traOrderDTO.endDate) &&
            Objects.equals(this.id, traOrderDTO.id) &&
            Objects.equals(this.name, traOrderDTO.name) &&
            Objects.equals(this.startDate, traOrderDTO.startDate) &&
            Objects.equals(this.advertismentId, traOrderDTO.advertismentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calculatedPrize, campaignId, customerId, endDate, id, name, startDate, advertismentId);
    }

    @Override
    public String toString() {
        return "{" +
            "calculatedPrize=" + calculatedPrize +
            ", campaignId=" + campaignId +
            ", customerId=" + customerId +
            ", endDate=" + endDate +
            ", id=" + id +
            ", name='" + name + '\'' +
            ", startDate=" + startDate +
            ", advertismentId=" + advertismentId +
            ", statusId=" + statusId +
            ", invoiceId=" + invoiceId +
            '}';
    }
}
