package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.traffic.thin.TraInvoiceCustomerThinDTO;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * TraInvoicePT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraInvoicePT {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("order")
    private List<TraOrderPT> orders = null;

    @JsonProperty("paid")
    private Boolean paid = null;

    @JsonProperty("paymentDay")
    private LocalDate paymentDay = null;

    @JsonProperty("price")
    private BigDecimal price = null;

    @JsonProperty("customerId")
    private TraInvoiceCustomerThinDTO customerId = null;

    @JsonProperty("status")
    private CorDictionaryDTO statusId = null;

    public TraInvoicePT id(Long id) {
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

    public TraInvoicePT order(List<TraOrderPT> order) {
        this.orders = order;
        return this;
    }

    /**
     * Get orders
     *
     * @return orders
     **/
    @ApiModelProperty(value = "")
    public List<TraOrderPT> getOrders() {
        return orders;
    }

    public void setOrders(List<TraOrderPT> orders) {
        this.orders = orders;
    }

    public TraInvoicePT paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    /**
     * Get paid
     *
     * @return paid
     **/
    @ApiModelProperty(value = "")
    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public TraInvoicePT paymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
        return this;
    }

    /**
     * Get price
     *
     * @return price
     **/
    @ApiModelProperty(value = "")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public TraInvoicePT price(BigDecimal price) {
        this.price = price;
        return this;
    }

    /**
     * Get paymentDay
     *
     * @return paymentDay
     **/
    @ApiModelProperty(value = "")
    public LocalDate getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
    }


    public TraInvoicePT statusId(CorDictionaryDTO traStatus) {
        this.statusId = traStatus;
        return this;
    }

    /**
     * Get paymentDay
     *
     * @return paymentDay
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getStatusId() {
        return statusId;
    }

    public void setStatusId(CorDictionaryDTO statusId) {
        this.statusId = statusId;
    }


    public TraInvoicePT customerId(TraInvoiceCustomerThinDTO customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * Get customerPT
     *
     * @return customerPT
     **/
    @ApiModelProperty(value = "")
    public TraInvoiceCustomerThinDTO getCustomerId() {
        return customerId;
    }

    public void setCustomerId(TraInvoiceCustomerThinDTO customerPT) {
        this.customerId = customerPT;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraInvoicePT traInvoicePT = (TraInvoicePT) o;
        return Objects.equals(this.id, traInvoicePT.id) &&
            Objects.equals(this.orders, traInvoicePT.orders) &&
            Objects.equals(this.paid, traInvoicePT.paid) &&
            Objects.equals(this.paymentDay, traInvoicePT.paymentDay) &&
            Objects.equals(this.customerId, traInvoicePT.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orders, paid, paymentDay);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraInvoicePT {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    order: ").append(toIndentedString(orders)).append("\n");
        sb.append("    paid: ").append(toIndentedString(paid)).append("\n");
        sb.append("    paymentDay: ").append(toIndentedString(paymentDay)).append("\n");
        sb.append("    customer: ").append(toIndentedString(customerId)).append("\n");
        sb.append("    status: ").append(toIndentedString(statusId)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
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

