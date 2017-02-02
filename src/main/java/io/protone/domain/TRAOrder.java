package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TRAOrder.
 */
@Entity
@Table(name = "tra_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TRAOrder implements Serializable {

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
    private CRMAccount cRMAccount;

    @ManyToOne
    private TRAInvoice tRAInvoice;

    @OneToOne
    @JoinColumn(unique = true)
    private CRMAccount customer;

    @OneToOne
    @JoinColumn(unique = true)
    private TRACampaign campaign;

    @OneToOne
    @JoinColumn(unique = true)
    private TRAPrice price;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToOne
    @JoinColumn(unique = true)
    private TRAStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TRAOrder name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public TRAOrder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public TRAOrder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getCalculatedPrize() {
        return calculatedPrize;
    }

    public TRAOrder calculatedPrize(Long calculatedPrize) {
        this.calculatedPrize = calculatedPrize;
        return this;
    }

    public void setCalculatedPrize(Long calculatedPrize) {
        this.calculatedPrize = calculatedPrize;
    }

    public CRMAccount getCRMAccount() {
        return cRMAccount;
    }

    public TRAOrder cRMAccount(CRMAccount cRMAccount) {
        this.cRMAccount = cRMAccount;
        return this;
    }

    public void setCRMAccount(CRMAccount cRMAccount) {
        this.cRMAccount = cRMAccount;
    }

    public TRAInvoice getTRAInvoice() {
        return tRAInvoice;
    }

    public TRAOrder tRAInvoice(TRAInvoice tRAInvoice) {
        this.tRAInvoice = tRAInvoice;
        return this;
    }

    public void setTRAInvoice(TRAInvoice tRAInvoice) {
        this.tRAInvoice = tRAInvoice;
    }

    public CRMAccount getCustomer() {
        return customer;
    }

    public TRAOrder customer(CRMAccount cRMAccount) {
        this.customer = cRMAccount;
        return this;
    }

    public void setCustomer(CRMAccount cRMAccount) {
        this.customer = cRMAccount;
    }

    public TRACampaign getCampaign() {
        return campaign;
    }

    public TRAOrder campaign(TRACampaign tRACampaign) {
        this.campaign = tRACampaign;
        return this;
    }

    public void setCampaign(TRACampaign tRACampaign) {
        this.campaign = tRACampaign;
    }

    public TRAPrice getPrice() {
        return price;
    }

    public TRAOrder price(TRAPrice tRAPrice) {
        this.price = tRAPrice;
        return this;
    }

    public void setPrice(TRAPrice tRAPrice) {
        this.price = tRAPrice;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public TRAOrder network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public TRAStatus getStatus() {
        return status;
    }

    public TRAOrder status(TRAStatus tRAStatus) {
        this.status = tRAStatus;
        return this;
    }

    public void setStatus(TRAStatus tRAStatus) {
        this.status = tRAStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TRAOrder tRAOrder = (TRAOrder) o;
        if (tRAOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tRAOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRAOrder{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", calculatedPrize='" + calculatedPrize + "'" +
            '}';
    }
}
