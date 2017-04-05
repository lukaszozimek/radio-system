package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import io.protone.domain.SchEmission;
import io.protone.domain.TraOrderStatus;
import io.protone.domain.TraPrice;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * TraOrderPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraOrderPT {
    @JsonProperty("calculatedPrize")
    private Long calculatedPrize = null;

    @JsonProperty("campaign")
    private TraCampaignPT traCampaignPT = null;

    @JsonProperty("customer")
    private TraCustomerPT customerPT = null;

    @JsonProperty("endDate")
    private LocalDate endDate = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("startDate")
    private LocalDate startDate = null;

    @JsonProperty("price")
    private TraPrice traPrice = null;
    @JsonProperty("status")
    private TraOrderStatus traOrderStatus = null;

    @JsonProperty("emission")
    private List<SchEmissionPT> schEmissionPT = new ArrayList<SchEmissionPT>();

    @JsonProperty
    private TraInvoicePT traInvoicePT = null;

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

    public TraOrderPT traCampaign(TraCampaignPT campaignId) {
        this.traCampaignPT = campaignId;
        return this;
    }

    /**
     * Get traCampaignPT
     *
     * @return traCampaignPT
     **/
    @ApiModelProperty(value = "")
    public TraCampaignPT getTraCampaignPT() {
        return traCampaignPT;
    }

    public void setTraCampaignPT(TraCampaignPT traCampaignPT) {
        this.traCampaignPT = traCampaignPT;
    }

    public TraOrderPT traPrice(TraPrice price) {
        this.traPrice = price;
        return this;
    }

    /**
     * Get traCampaignPT
     *
     * @return traCampaignPT
     **/
    @ApiModelProperty(value = "")
    public TraPrice getTraPrice() {
        return traPrice;
    }

    public void setTraPrice(TraPrice traPrice) {
        this.traPrice = traPrice;
    }

    public TraOrderPT customerId(TraCustomerPT customerId) {
        this.customerPT = customerId;
        return this;
    }

    /**
     * Get customerPT
     *
     * @return customerPT
     **/
    @ApiModelProperty(value = "")
    public TraCustomerPT getCustomerPT() {
        return customerPT;
    }

    public void setCustomerPT(TraCustomerPT customerPT) {
        this.customerPT = customerPT;
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
    public TraInvoicePT getTraInvoice() {
        return traInvoicePT;
    }

    public void setTraInvoice(TraInvoicePT traInvoicePT) {
        this.traInvoicePT = traInvoicePT;
    }

    public TraOrderPT traInvoiceT(TraInvoicePT traInvoicePT) {
        this.traInvoicePT = traInvoicePT;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @ApiModelProperty(required = true, value = "")
    public TraOrderStatus getTraOrderStatus() {
        return traOrderStatus;
    }

    public void setTraOrderStatus(TraOrderStatus traOrderStatus) {
        this.traOrderStatus = traOrderStatus;
    }

    public TraOrderPT traOrderStatus(TraOrderStatus traOrderStatus) {
        this.traOrderStatus = traOrderStatus;
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
            Objects.equals(this.traCampaignPT, traOrderPT.traCampaignPT) &&
            Objects.equals(this.customerPT, traOrderPT.customerPT) &&
            Objects.equals(this.endDate, traOrderPT.endDate) &&
            Objects.equals(this.id, traOrderPT.id) &&
            Objects.equals(this.name, traOrderPT.name) &&
            Objects.equals(this.startDate, traOrderPT.startDate) &&
            Objects.equals(this.schEmissionPT, traOrderPT.schEmissionPT) &&
            Objects.equals(this.traPrice, traOrderPT.traPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calculatedPrize, traCampaignPT, customerPT, endDate, id, name, startDate, traPrice, schEmissionPT);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraOrderPT {\n");

        sb.append("    calculatedPrize: ").append(toIndentedString(calculatedPrize)).append("\n");
        sb.append("    traCampaignPT: ").append(toIndentedString(traCampaignPT)).append("\n");
        sb.append("    customerPT: ").append(toIndentedString(customerPT)).append("\n");
        sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
        sb.append("    price: ").append(toIndentedString(traPrice)).append("\n");
        sb.append("    emissions: ").append(toIndentedString(schEmissionPT)).append("\n");

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

