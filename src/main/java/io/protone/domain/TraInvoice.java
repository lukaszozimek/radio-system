package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A TraInvoice.
 */
@Entity
@Table(name = "tra_invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    @Column(name = "payment_day")
    private LocalDate paymentDay;

    @ManyToOne
    private CrmAccount customer;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    private TraInvoiceStatus status;

    @OneToMany(mappedBy = "invoice")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TraOrder> orders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPaid() {
        return paid;
    }

    public TraInvoice paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TraInvoice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPaymentDay() {
        return paymentDay;
    }

    public TraInvoice paymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
        return this;
    }

    public void setPaymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
    }

    public CrmAccount getCustomer() {
        return customer;
    }

    public TraInvoice customer(CrmAccount crmAccount) {
        this.customer = crmAccount;
        return this;
    }

    public void setCustomer(CrmAccount crmAccount) {
        this.customer = crmAccount;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public TraInvoice network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraInvoiceStatus getStatus() {
        return status;
    }

    public TraInvoice status(TraInvoiceStatus traInvoiceStatus) {
        this.status = traInvoiceStatus;
        return this;
    }

    public void setStatus(TraInvoiceStatus traInvoiceStatus) {
        this.status = traInvoiceStatus;
    }

    public Set<TraOrder> getOrders() {
        return orders;
    }

    public TraInvoice orders(Set<TraOrder> traOrders) {
        this.orders = traOrders;
        return this;
    }

    public TraInvoice addOrders(TraOrder traOrder) {
        this.orders.add(traOrder);
        traOrder.setInvoice(this);
        return this;
    }

    public TraInvoice removeOrders(TraOrder traOrder) {
        this.orders.remove(traOrder);
        traOrder.setInvoice(null);
        return this;
    }

    public void setOrders(Set<TraOrder> traOrders) {
        this.orders = traOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraInvoice traInvoice = (TraInvoice) o;
        if (traInvoice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traInvoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraInvoice{" +
            "id=" + id +
            ", paid='" + paid + "'" +
            ", price='" + price + "'" +
            ", paymentDay='" + paymentDay + "'" +
            '}';
    }
}
