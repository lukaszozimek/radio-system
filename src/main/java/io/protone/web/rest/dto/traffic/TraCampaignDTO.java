package io.protone.web.rest.dto.traffic;

import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.traffic.thin.TraCustomerThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TraCampaignDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraCampaignDTO implements Serializable {

    @NotNull
    private TraCustomerThinDTO customerPT = null;

    private LocalDate endDate = null;

    private Long id = null;

    @NotNull
    private String shortName = null;

    @NotNull
    private String name = null;

    private Long prize = null;

    private LocalDate startDate = null;

    private CorDictionaryDTO status = null;

    private List<TraOrderDTO> orders = new ArrayList<TraOrderDTO>();

    public TraCampaignDTO customerId(TraCustomerThinDTO customerId) {
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

    public TraCampaignDTO endDate(LocalDate endDate) {
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

    public TraCampaignDTO status(CorDictionaryDTO status) {
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

    public TraCampaignDTO id(Long id) {
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

    public TraCampaignDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(required = true, value = "")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }


    public TraCampaignDTO name(String name) {
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


    public TraCampaignDTO prize(Long prize) {
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

    public TraCampaignDTO startDate(LocalDate startDate) {
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

    public TraCampaignDTO orders(List<TraOrderDTO> orders) {
        this.orders = orders;
        return this;
    }

    public TraCampaignDTO addOrder(TraOrderDTO order) {
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
        TraCampaignDTO traCampaignDTO = (TraCampaignDTO) o;
        return Objects.equals(this.customerPT, traCampaignDTO.customerPT) &&
            Objects.equals(this.endDate, traCampaignDTO.endDate) &&
            Objects.equals(this.id, traCampaignDTO.id) &&
            Objects.equals(this.name, traCampaignDTO.name) &&
            Objects.equals(this.prize, traCampaignDTO.prize) &&
            Objects.equals(this.startDate, traCampaignDTO.startDate) &&
            Objects.equals(this.orders, traCampaignDTO.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerPT, endDate, id, name, prize, startDate, orders, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraCampaignDTO {\n");

        sb.append("    customerId: ").append(toIndentedString(customerPT)).append("\n");
        sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
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

