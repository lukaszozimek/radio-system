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

    @Column(name = "discount_per_order")
    private Boolean discountPerOrder;

    @Column(name = "discount_per_invoice")
    private Boolean discountPerInvoice;

    @Column(name = "price", precision = 10, scale = 2)
    @PodamExclude
    private BigDecimal price;

    @Column(name = "payment_day")
    private LocalDate paymentDay;

    @Column(name = "custom_discount")
    private Integer customDiscount;

    @ManyToOne
    @PodamExclude
    private CrmAccount customer;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorDictionary status;

    @ManyToOne
    @PodamExclude
    private TraCompany company;

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

    public TraCompany getCompany() {
        return company;
    }

    public void setCompany(TraCompany company) {
        this.company = company;
    }

    public TraInvoice company(TraCompany traCompany) {
        this.company = traCompany;
        return this;
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


    public Integer getCustomDiscount() {
        return customDiscount;
    }

    public void setCustomDiscount(Integer customDiscount) {
        this.customDiscount = customDiscount;
    }

    public Boolean getPaid() {
        return paid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraInvoice that = (TraInvoice) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (paid != null ? !paid.equals(that.paid) : that.paid != null) return false;
        if (discountPerOrder != null ? !discountPerOrder.equals(that.discountPerOrder) : that.discountPerOrder != null)
            return false;
        if (discountPerInvoice != null ? !discountPerInvoice.equals(that.discountPerInvoice) : that.discountPerInvoice != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (paymentDay != null ? !paymentDay.equals(that.paymentDay) : that.paymentDay != null) return false;
        if (customDiscount != null ? !customDiscount.equals(that.customDiscount) : that.customDiscount != null)
            return false;
        if (customer != null ? !customer.equals(that.customer) : that.customer != null) return false;
        if (network != null ? !network.equals(that.network) : that.network != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        return orders != null ? orders.equals(that.orders) : that.orders == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (paid != null ? paid.hashCode() : 0);
        result = 31 * result + (discountPerOrder != null ? discountPerOrder.hashCode() : 0);
        result = 31 * result + (discountPerInvoice != null ? discountPerInvoice.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (paymentDay != null ? paymentDay.hashCode() : 0);
        result = 31 * result + (customDiscount != null ? customDiscount.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (network != null ? network.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraInvoice{" +
                "id=" + id +
                ", paid=" + paid +
                ", discountPerOrder=" + discountPerOrder +
                ", discountPerInvoice=" + discountPerInvoice +
                ", price=" + price +
                ", paymentDay=" + paymentDay +
                ", customDiscount=" + customDiscount +
                ", customer=" + customer +
                ", network=" + network +
                ", status=" + status +
                ", company=" + company +
                ", orders=" + orders +
                '}';
    }
}
