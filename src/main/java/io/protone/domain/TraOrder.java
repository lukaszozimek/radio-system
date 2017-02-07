package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TraOrder.
 */
@Entity
@Table(name = "tra_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "calculated_prize")
    private Long calculatedPrize;

    @ManyToOne
    private CrmAccount customer;

    @ManyToOne
    private TraCampaign campaign;

    @ManyToOne
    private TraPrice price;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    private TraOrderStatus status;

    @ManyToOne
    private TraInvoice orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TraOrder name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public TraOrder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public TraOrder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getCalculatedPrize() {
        return calculatedPrize;
    }

    public TraOrder calculatedPrize(Long calculatedPrize) {
        this.calculatedPrize = calculatedPrize;
        return this;
    }

    public void setCalculatedPrize(Long calculatedPrize) {
        this.calculatedPrize = calculatedPrize;
    }

    public CrmAccount getCustomer() {
        return customer;
    }

    public TraOrder customer(CrmAccount crmAccount) {
        this.customer = crmAccount;
        return this;
    }

    public void setCustomer(CrmAccount crmAccount) {
        this.customer = crmAccount;
    }

    public TraCampaign getCampaign() {
        return campaign;
    }

    public TraOrder campaign(TraCampaign traCampaign) {
        this.campaign = traCampaign;
        return this;
    }

    public void setCampaign(TraCampaign traCampaign) {
        this.campaign = traCampaign;
    }

    public TraPrice getPrice() {
        return price;
    }

    public TraOrder price(TraPrice traPrice) {
        this.price = traPrice;
        return this;
    }

    public void setPrice(TraPrice traPrice) {
        this.price = traPrice;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public TraOrder network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraOrderStatus getStatus() {
        return status;
    }

    public TraOrder status(TraOrderStatus traOrderStatus) {
        this.status = traOrderStatus;
        return this;
    }

    public void setStatus(TraOrderStatus traOrderStatus) {
        this.status = traOrderStatus;
    }

    public TraInvoice getOrders() {
        return orders;
    }

    public TraOrder orders(TraInvoice traInvoice) {
        this.orders = traInvoice;
        return this;
    }

    public void setOrders(TraInvoice traInvoice) {
        this.orders = traInvoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraOrder traOrder = (TraOrder) o;
        if (traOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraOrder{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", calculatedPrize='" + calculatedPrize + "'" +
            '}';
    }
}
