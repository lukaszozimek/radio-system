package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.domain.TRAPrice;
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
    private TRAPrice traPrice = null;

    @JsonProperty("emission")
    private List<SchEmissionPT> emission = new ArrayList<>();

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

    public TraOrderPT campaignId(TraCampaignPT campaignId) {
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

    public TraOrderPT campaignId(TRAPrice price) {
        this.traPrice = price;
        return this;
    }

    /**
     * Get traCampaignPT
     *
     * @return traCampaignPT
     **/
    @ApiModelProperty(value = "")
    public TRAPrice getTraPrice() {
        return traPrice;
    }

    public void setTraPrice(TRAPrice traPrice) {
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

    public TraOrderPT emission(List<SchEmissionPT> emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Get emission
     *
     * @return emission
     **/
    @ApiModelProperty(value = "")
    public List<SchEmissionPT> getEmission() {
        return emission;
    }

    public void setEmission(List<SchEmissionPT> emission) {
        this.emission = emission;
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
            Objects.equals(this.traPrice, traOrderPT.traPrice) &&
            Objects.equals(this.emission, traOrderPT.emission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calculatedPrize, traCampaignPT, customerPT, endDate, id, name, startDate, emission, traPrice);
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
        sb.append("    emission: ").append(toIndentedString(emission)).append("\n");
        sb.append("    price: ").append(toIndentedString(traPrice)).append("\n");
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

