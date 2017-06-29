package io.protone.traffic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmAccount;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

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
public class TraInvoice extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "price", precision = 10, scale = 2)
    @PodamExclude
    private BigDecimal price;

    @Column(name = "payment_day")
    private LocalDate paymentDay;

    @ManyToOne
    @PodamExclude
    private CrmAccount customer;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorDictionary status;

    @OneToMany(mappedBy = "invoice")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

    @PodamExclude
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

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TraInvoice price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDate getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
    }

    public TraInvoice paymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
        return this;
    }

    public CrmAccount getCustomer() {
        return customer;
    }

    public void setCustomer(CrmAccount crmAccount) {
        this.customer = crmAccount;
    }

    public TraInvoice customer(CrmAccount crmAccount) {
        this.customer = crmAccount;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraInvoice network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public CorDictionary getStatus() {
        return status;
    }

    public void setStatus(CorDictionary corDictionary) {
        this.status = corDictionary;
    }

    public TraInvoice status(CorDictionary corDictionary) {
        this.status = corDictionary;
        return this;
    }

    public Set<TraOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<TraOrder> traOrders) {
        this.orders = traOrders;
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
