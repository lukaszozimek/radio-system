package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TRAInvoice.
 */
@Entity
@Table(name = "tra_invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TRAInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "payment_day")
    private LocalDate paymentDay;

    @ManyToOne
    private CORNetwork network;

    public TRAInvoice network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CORNetwork network) {
        this.network = network;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPaid() {
        return paid;
    }

    public TRAInvoice paid(Boolean paid) {
        this.paid = paid;
        return this;
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

    public TRAInvoice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDate getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
    }

    public TRAInvoice paymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TRAInvoice tRAInvoice = (TRAInvoice) o;
        if (tRAInvoice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tRAInvoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRAInvoice{" +
            "id=" + id +
            ", paid='" + paid + "'" +
            ", price='" + price + "'" +
            ", paymentDay='" + paymentDay + "'" +
            '}';
    }
}
