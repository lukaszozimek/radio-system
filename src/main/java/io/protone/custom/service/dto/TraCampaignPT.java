package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TraCampaignPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraCampaignPT {
    @JsonProperty("customerPT")
    private TraCustomerPT customerPT = null;

    @JsonProperty("endDate")
    private LocalDate endDate = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("prize")
    private Long prize = null;

    @JsonProperty("startDate")
    private LocalDate startDate = null;

    @JsonProperty("emission")
    private List<SchEmissionPT> emission = new ArrayList<SchEmissionPT>();

    public TraCampaignPT customerId(TraCustomerPT customerId) {
        this.customerPT = customerId;
        return this;
    }

    /**
     * Get customerId
     *
     * @return customerId
     **/
    @ApiModelProperty(value = "")
    public TraCustomerPT getCustomerId() {
        return customerPT;
    }

    public void setCustomerId(TraCustomerPT customerId) {
        this.customerPT = customerId;
    }

    public TraCampaignPT endDate(LocalDate endDate) {
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

    public TraCampaignPT id(Long id) {
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

    public TraCampaignPT name(String name) {
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

    public TraCampaignPT prize(Long prize) {
        this.prize = prize;
        return this;
    }

    /**
     * Get prize
     *
     * @return prize
     **/
    @ApiModelProperty(value = "")
    public Long getPrize() {
        return prize;
    }

    public void setPrize(Long prize) {
        this.prize = prize;
    }

    public TraCampaignPT startDate(LocalDate startDate) {
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

    public TraCampaignPT emission(List<SchEmissionPT> emission) {
        this.emission = emission;
        return this;
    }

    public TraCampaignPT addEmissionItem(SchEmissionPT emissionItem) {
        this.emission.add(emissionItem);
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
        TraCampaignPT traCampaignPT = (TraCampaignPT) o;
        return Objects.equals(this.customerPT, traCampaignPT.customerPT) &&
            Objects.equals(this.endDate, traCampaignPT.endDate) &&
            Objects.equals(this.id, traCampaignPT.id) &&
            Objects.equals(this.name, traCampaignPT.name) &&
            Objects.equals(this.prize, traCampaignPT.prize) &&
            Objects.equals(this.startDate, traCampaignPT.startDate) &&
            Objects.equals(this.emission, traCampaignPT.emission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerPT, endDate, id, name, prize, startDate, emission);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraCampaignPT {\n");

        sb.append("    customerId: ").append(toIndentedString(customerPT)).append("\n");
        sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    prize: ").append(toIndentedString(prize)).append("\n");
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
