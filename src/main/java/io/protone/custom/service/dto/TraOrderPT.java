package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.thin.TraAdvertisementThinDTO;
import io.protone.web.rest.dto.thin.TraCustomerThinDTO;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TraOrderPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraOrderPT {
    @JsonProperty("calculatedPrize")
    private Long calculatedPrize = null;

    @JsonProperty("campaign")
    private Long campaignId = null;

    @JsonProperty("customer")
    private TraCustomerThinDTO customerId = null;

    @JsonProperty("endDate")
    private LocalDate endDate = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("startDate")
    private LocalDate startDate = null;

    @JsonProperty("advertisment")
    private TraAdvertisementThinDTO advertismentId = null;

    @JsonProperty("status")
    private CorDictionaryDTO statusId = null;

    @JsonProperty("emission")
    private List<SchEmissionPT> schEmissionPT = new ArrayList<SchEmissionPT>();

    @JsonProperty
    private Long invoiceId = null;

    public TraOrderPT calculatedPrize(Long calculatedPrize) {
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

    public TraOrderPT traCampaign(Long campaignId) {
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


    public TraOrderPT customerId(TraCustomerThinDTO customerId) {
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

    public TraOrderPT endDate(LocalDate endDate) {
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

    public TraOrderPT id(Long id) {
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

    public TraOrderPT name(String name) {
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

    public TraOrderPT startDate(LocalDate startDate) {
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

    public TraOrderPT invoiceId(Long traInvoicePT) {
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

    public TraOrderPT statusId(CorDictionaryDTO traOrderStatus) {
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

    public TraOrderPT advertismentId(TraAdvertisementThinDTO advertisment) {
        this.advertismentId = advertisment;
        return this;
    }

    /**
     * Get emissions
     *
     * @return status
     **/
    @ApiModelProperty(required = true, value = "")
    public List<SchEmissionPT> getEmissions() {
        return schEmissionPT;
    }

    public void setEmissions(List<SchEmissionPT> emissions) {
        this.schEmissionPT = emissions;
    }

    public TraOrderPT addEmission(SchEmissionPT emission) {
        this.schEmissionPT.add(emission);
        return this;
    }

    public TraOrderPT removeEmission(SchEmissionPT emission) {
        this.schEmissionPT.remove(emission);
        return this;
    }

    public TraOrderPT emissions(List<SchEmissionPT> emissions) {
        this.schEmissionPT = emissions;
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
        TraOrderPT traOrderPT = (TraOrderPT) o;
        return Objects.equals(this.calculatedPrize, traOrderPT.calculatedPrize) &&
            Objects.equals(this.campaignId, traOrderPT.campaignId) &&
            Objects.equals(this.customerId, traOrderPT.customerId) &&
            Objects.equals(this.endDate, traOrderPT.endDate) &&
            Objects.equals(this.id, traOrderPT.id) &&
            Objects.equals(this.name, traOrderPT.name) &&
            Objects.equals(this.startDate, traOrderPT.startDate) &&
            Objects.equals(this.schEmissionPT, traOrderPT.schEmissionPT) &&
            Objects.equals(this.advertismentId, traOrderPT.advertismentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calculatedPrize, campaignId, customerId, endDate, id, name, startDate, schEmissionPT, advertismentId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraOrderPT {\n");

        sb.append("    calculatedPrize: ").append(toIndentedString(calculatedPrize)).append("\n");
        sb.append("    campaignId: ").append(toIndentedString(campaignId)).append("\n");
        sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
        sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
        sb.append("    emissions: ").append(toIndentedString(schEmissionPT)).append("\n");
        sb.append("    advertisment: ").append(toIndentedString(advertismentId)).append("\n");

        sb.append("}");
        return sb.toString();
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
}

