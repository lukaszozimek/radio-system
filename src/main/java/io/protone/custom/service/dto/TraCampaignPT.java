package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.traffic.TraOrderDTO;
import io.protone.web.rest.dto.traffic.thin.TraCustomerThinDTO;
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
    private TraCustomerThinDTO customerPT = null;

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

    @JsonProperty("status")
    private CorDictionaryDTO status = null;

    @JsonProperty("orders")
    private List<TraOrderDTO> orders = new ArrayList<TraOrderDTO>();

    public TraCampaignPT customerId(TraCustomerThinDTO customerId) {
        this.customerPT = customerId;
        return this;
    }


    /**
     * Get customerId
     *
     * @return customerId
     **/
    @ApiModelProperty(value = "")
    public TraCustomerThinDTO getCustomerId() {
        return customerPT;
    }

    public void setCustomerId(TraCustomerThinDTO customerId) {
        this.customerPT = customerId;
    }

    public TraCampaignPT endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }


    /**
     * Get statsus
     *
     * @return status
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getStatus() {
        return status;
    }

    public void setStatus(CorDictionaryDTO status) {
        this.status = status;
    }

    public TraCampaignPT status(CorDictionaryDTO status) {
        this.status = status;
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

    public TraCampaignPT orders(List<TraOrderDTO> orders) {
        this.orders = orders;
        return this;
    }

    public TraCampaignPT addOrder(TraOrderDTO order) {
        this.orders.add(order);
        return this;
    }

    /**
     * Get orders
     *
     * @return orders
     **/
    @ApiModelProperty(value = "")
    public List<TraOrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<TraOrderDTO> orders) {
        this.orders = orders;
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
            Objects.equals(this.orders, traCampaignPT.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerPT, endDate, id, name, prize, startDate, orders, status);
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
        sb.append("    orders: ").append(toIndentedString(orders)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

