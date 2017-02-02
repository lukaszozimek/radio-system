package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "payment_day")
    private LocalDate paymentDay;

    @ManyToOne
    private CRMAccount cRMAccount;

    @OneToOne
    @JoinColumn(unique = true)
    private CRMAccount customer;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToOne
    @JoinColumn(unique = true)
    private TRAStatus status;

    @OneToMany(mappedBy = "tRAInvoice")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TRAOrder> orders = new HashSet<>();

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

    public LocalDate getPaymentDay() {
        return paymentDay;
    }

    public TRAInvoice paymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
        return this;
    }

    public void setPaymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
    }

    public CRMAccount getCRMAccount() {
        return cRMAccount;
    }

    public TRAInvoice cRMAccount(CRMAccount cRMAccount) {
        this.cRMAccount = cRMAccount;
        return this;
    }

    public void setCRMAccount(CRMAccount cRMAccount) {
        this.cRMAccount = cRMAccount;
    }

    public CRMAccount getCustomer() {
        return customer;
    }

    public TRAInvoice customer(CRMAccount cRMAccount) {
        this.customer = cRMAccount;
        return this;
    }

    public void setCustomer(CRMAccount cRMAccount) {
        this.customer = cRMAccount;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public TRAInvoice network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public TRAStatus getStatus() {
        return status;
    }

    public TRAInvoice status(TRAStatus tRAStatus) {
        this.status = tRAStatus;
        return this;
    }

    public void setStatus(TRAStatus tRAStatus) {
        this.status = tRAStatus;
    }

    public Set<TRAOrder> getOrders() {
        return orders;
    }

    public TRAInvoice orders(Set<TRAOrder> tRAOrders) {
        this.orders = tRAOrders;
        return this;
    }

    public TRAInvoice addOrders(TRAOrder tRAOrder) {
        orders.add(tRAOrder);
        tRAOrder.setTRAInvoice(this);
        return this;
    }

    public TRAInvoice removeOrders(TRAOrder tRAOrder) {
        orders.remove(tRAOrder);
        tRAOrder.setTRAInvoice(null);
        return this;
    }

    public void setOrders(Set<TRAOrder> tRAOrders) {
        this.orders = tRAOrders;
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
            ", paymentDay='" + paymentDay + "'" +
            '}';
    }
}
