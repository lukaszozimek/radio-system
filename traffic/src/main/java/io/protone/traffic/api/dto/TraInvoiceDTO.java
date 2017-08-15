package io.protone.traffic.api.dto;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.traffic.api.dto.thin.TraInvoiceCustomerThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * TraInvoiceDTO
 */

public class TraInvoiceDTO implements Serializable {

    private Long id = null;

    private List<TraOrderDTO> orders = null;

    private Boolean paid = null;

    private LocalDate paymentDay = null;

    private BigDecimal price = null;

    private Boolean discountPerOrder;

    private Boolean discountPerInvoice;

    @NotNull
    private TraCompanyDTO traCompany;

    private CoreUserThinDTO createdBy;

    private ZonedDateTime createdDate;

    private CoreUserThinDTO lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

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

        TraInvoiceDTO that = (TraInvoiceDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (orders != null ? !orders.equals(that.orders) : that.orders != null) return false;
        if (paid != null ? !paid.equals(that.paid) : that.paid != null) return false;
        if (paymentDay != null ? !paymentDay.equals(that.paymentDay) : that.paymentDay != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastModifiedBy != null ? !lastModifiedBy.equals(that.lastModifiedBy) : that.lastModifiedBy != null)
            return false;
        if (lastModifiedDate != null ? !lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate != null)
            return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        return statusId != null ? statusId.equals(that.statusId) : that.statusId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        result = 31 * result + (paid != null ? paid.hashCode() : 0);
        result = 31 * result + (paymentDay != null ? paymentDay.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        return result;
    }

    public TraCompanyDTO getTraCompany() {
        return traCompany;
    }

    public void setTraCompany(TraCompanyDTO traCompany) {
        this.traCompany = traCompany;
    }

    @Override
    public String toString() {
        return "TraInvoiceDTO{" +
                "id=" + id +
                ", orders=" + orders +
                ", paid=" + paid +
                ", paymentDay=" + paymentDay +
                ", price=" + price +
                ", traCompany=" + traCompany +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                ", customerId=" + customerId +
                ", statusId=" + statusId +
                '}';
    }


}

