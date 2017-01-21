package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;


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

    @JsonProperty("campaignId")
    private Long campaignId = null;

    @JsonProperty("customerId")
    private Long customerId = null;

    @JsonProperty("endDate")
    private String endDate = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("startDate")
    private String startDate = null;

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

    public TraOrderPT campaignId(Long campaignId) {
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

    public TraOrderPT customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * Get customerId
     *
     * @return customerId
     **/
    @ApiModelProperty(value = "")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public TraOrderPT endDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
     * Get endDate
     *
     * @return endDate
     **/
    @ApiModelProperty(value = "")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    public TraOrderPT startDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Get startDate
     *
     * @return startDate
     **/
    @ApiModelProperty(value = "")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
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
            Objects.equals(this.campaignId, traOrderPT.campaignId) &&
            Objects.equals(this.customerId, traOrderPT.customerId) &&
            Objects.equals(this.endDate, traOrderPT.endDate) &&
            Objects.equals(this.id, traOrderPT.id) &&
            Objects.equals(this.name, traOrderPT.name) &&
            Objects.equals(this.startDate, traOrderPT.startDate) &&
            Objects.equals(this.emission, traOrderPT.emission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calculatedPrize, campaignId, customerId, endDate, id, name, startDate, emission);
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
        sb.append("    emission: ").append(toIndentedString(emission)).append("\n");
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

