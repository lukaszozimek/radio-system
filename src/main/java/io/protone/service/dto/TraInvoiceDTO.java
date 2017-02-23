package io.protone.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TraInvoice entity.
 */
public class TraInvoiceDTO implements Serializable {

    private Long id;

    private Boolean paid;

    private BigDecimal price;

    private LocalDate paymentDay;

    private Long customerId;

    private Long networkId;

    private Long statusId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public LocalDate getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long crmAccountId) {
        this.customerId = crmAccountId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long traInvoiceStatusId) {
        this.statusId = traInvoiceStatusId;
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

        if ( ! Objects.equals(id, traInvoiceDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraInvoiceDTO{" +
            "id=" + id +
            ", paid='" + paid + "'" +
            ", price='" + price + "'" +
            ", paymentDay='" + paymentDay + "'" +
            '}';
    }
}
