package io.protone.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TRAInvoice entity.
 */
public class TRAInvoiceDTO implements Serializable {

    private Long id;

    private Boolean paid;

    private BigDecimal price;

    private LocalDate paymentDay;


    private Long orderId;
    
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long tRAOrderId) {
        this.orderId = tRAOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TRAInvoiceDTO tRAInvoiceDTO = (TRAInvoiceDTO) o;

        if ( ! Objects.equals(id, tRAInvoiceDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRAInvoiceDTO{" +
            "id=" + id +
            ", paid='" + paid + "'" +
            ", price='" + price + "'" +
            ", paymentDay='" + paymentDay + "'" +
            '}';
    }
}
