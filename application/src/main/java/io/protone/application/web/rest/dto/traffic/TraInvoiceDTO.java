package io.protone.application.web.rest.dto.traffic;

import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.traffic.thin.TraInvoiceCustomerThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * TraInvoiceDTO
 */

public class TraInvoiceDTO implements Serializable {

    private Long id = null;

    private List<TraOrderDTO> orders = null;

    private Boolean paid = null;

    private LocalDate paymentDay = null;

    private BigDecimal price = null;

    @NotNull
    private TraInvoiceCustomerThinDTO customerId = null;

    private CorDictionaryDTO statusId = null;

    public TraInvoiceDTO id(Long id) {
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

    public TraInvoiceDTO order(List<TraOrderDTO> order) {
        this.orders = order;
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

    public TraInvoiceDTO paid(Boolean paid) {
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

    public TraInvoiceDTO paymentDay(LocalDate paymentDay) {
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


    public TraInvoiceDTO price(BigDecimal price) {
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


    public TraInvoiceDTO statusId(CorDictionaryDTO traStatus) {
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


    public TraInvoiceDTO customerId(TraInvoiceCustomerThinDTO customerId) {
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
        TraInvoiceDTO traInvoiceDTO = (TraInvoiceDTO) o;
        return Objects.equals(this.id, traInvoiceDTO.id) &&
            Objects.equals(this.orders, traInvoiceDTO.orders) &&
            Objects.equals(this.paid, traInvoiceDTO.paid) &&
            Objects.equals(this.paymentDay, traInvoiceDTO.paymentDay) &&
            Objects.equals(this.customerId, traInvoiceDTO.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orders, paid, paymentDay);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraInvoiceDTO {\n");

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

