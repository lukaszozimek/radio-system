package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.domain.TraInvoiceStatus;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
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
    private List<TraOrderPT> order = null;

    @JsonProperty("paid")
    private Boolean paid = null;

    @JsonProperty("paymentDay")
    private LocalDate paymentDay = null;

    @JsonProperty("price")
    private BigDecimal price = null;

    @JsonProperty("customer")
    private TraCustomerPT customerPT = null;

    @JsonProperty("status")
    private TraInvoiceStatus traStatus = null;

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
        this.order = order;
        return this;
    }

    /**
     * Get order
     *
     * @return order
     **/
    @ApiModelProperty(value = "")
    public List<TraOrderPT> getOrder() {
        return order;
    }

    public void setOrder(List<TraOrderPT> order) {
        this.order = order;
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


    public TraInvoicePT traStatus(TraInvoiceStatus traStatus) {
        this.traStatus = traStatus;
        return this;
    }

    /**
     * Get paymentDay
     *
     * @return paymentDay
     **/
    @ApiModelProperty(value = "")
    public TraInvoiceStatus getTraStatus() {
        return traStatus;
    }

    public void setTraStatus(TraInvoiceStatus traStatus) {
        this.traStatus = traStatus;
    }


    public TraInvoicePT customerId(TraCustomerPT customerId) {
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
            Objects.equals(this.order, traInvoicePT.order) &&
            Objects.equals(this.paid, traInvoicePT.paid) &&
            Objects.equals(this.paymentDay, traInvoicePT.paymentDay) &&
            Objects.equals(this.customerPT, traInvoicePT.customerPT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, paid, paymentDay);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraInvoicePT {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    order: ").append(toIndentedString(order)).append("\n");
        sb.append("    paid: ").append(toIndentedString(paid)).append("\n");
        sb.append("    paymentDay: ").append(toIndentedString(paymentDay)).append("\n");
        sb.append("    customer: ").append(toIndentedString(customerPT)).append("\n");
        sb.append("    status: ").append(toIndentedString(traStatus)).append("\n");
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

