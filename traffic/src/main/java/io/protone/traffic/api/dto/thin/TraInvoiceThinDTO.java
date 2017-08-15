package io.protone.traffic.api.dto.thin;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * TraInvoiceDTO
 */

public class TraInvoiceThinDTO implements Serializable {

    private Long id = null;

    private Boolean paid = null;

    private LocalDate paymentDay = null;

    private BigDecimal price = null;

    private Boolean discountPerOrder = null;

    private Boolean discountPerInvoice = null;

    @NotNull
    private TraInvoiceCustomerThinDTO customerId = null;

    private CorDictionaryDTO statusId = null;

    private CoreUserThinDTO createdBy;

    private ZonedDateTime createdDate;

    private CoreUserThinDTO lastModifiedBy;

    private ZonedDateTime lastModifiedDate;


    public TraInvoiceThinDTO id(Long id) {
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

    public TraInvoiceThinDTO paid(Boolean paid) {
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

    public TraInvoiceThinDTO paymentDay(LocalDate paymentDay) {
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


    public TraInvoiceThinDTO price(BigDecimal price) {
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


    public TraInvoiceThinDTO statusId(CorDictionaryDTO traStatus) {
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


    public TraInvoiceThinDTO customerId(TraInvoiceCustomerThinDTO customerId) {
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

    public CoreUserThinDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CoreUserThinDTO createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public CoreUserThinDTO getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(CoreUserThinDTO lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Boolean getDiscountPerOrder() {
        return discountPerOrder;
    }

    public void setDiscountPerOrder(Boolean discountPerOrder) {
        this.discountPerOrder = discountPerOrder;
    }

    public Boolean getDiscountPerInvoice() {
        return discountPerInvoice;
    }

    public void setDiscountPerInvoice(Boolean discountPerInvoice) {
        this.discountPerInvoice = discountPerInvoice;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraInvoiceThinDTO that = (TraInvoiceThinDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (paid != null ? !paid.equals(that.paid) : that.paid != null) return false;
        if (paymentDay != null ? !paymentDay.equals(that.paymentDay) : that.paymentDay != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastModifiedBy != null ? !lastModifiedBy.equals(that.lastModifiedBy) : that.lastModifiedBy != null)
            return false;
        return lastModifiedDate != null ? lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (paid != null ? paid.hashCode() : 0);
        result = 31 * result + (paymentDay != null ? paymentDay.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "TraInvoiceThinDTO{" +
                "id=" + id +
                ", paid=" + paid +
                ", paymentDay=" + paymentDay +
                ", price=" + price +
                ", discountPerOrder=" + discountPerOrder +
                ", discountPerInvoice=" + discountPerInvoice +
                ", customerId=" + customerId +
                ", statusId=" + statusId +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}

