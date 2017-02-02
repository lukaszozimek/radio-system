package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TRACampaign.
 */
@Entity
@Table(name = "tra_campaign")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TRACampaign implements Serializable {

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

    @Column(name = "prize")
    private Long prize;

    @ManyToOne
    private CRMAccount cRMAccount;

    @OneToOne
    @JoinColumn(unique = true)
    private CRMAccount customer;

    @OneToOne
    @JoinColumn(unique = true)
    private TRAPrice price;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToOne
    @JoinColumn(unique = true)
    private TRAStatus status;

    @OneToMany(mappedBy = "tRACampaign")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SCHEmission> emissions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TRACampaign name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public TRACampaign startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public TRACampaign endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getPrize() {
        return prize;
    }

    public TRACampaign prize(Long prize) {
        this.prize = prize;
        return this;
    }

    public void setPrize(Long prize) {
        this.prize = prize;
    }

    public CRMAccount getCRMAccount() {
        return cRMAccount;
    }

    public TRACampaign cRMAccount(CRMAccount cRMAccount) {
        this.cRMAccount = cRMAccount;
        return this;
    }

    public void setCRMAccount(CRMAccount cRMAccount) {
        this.cRMAccount = cRMAccount;
    }

    public CRMAccount getCustomer() {
        return customer;
    }

    public TRACampaign customer(CRMAccount cRMAccount) {
        this.customer = cRMAccount;
        return this;
    }

    public void setCustomer(CRMAccount cRMAccount) {
        this.customer = cRMAccount;
    }

    public TRAPrice getPrice() {
        return price;
    }

    public TRACampaign price(TRAPrice tRAPrice) {
        this.price = tRAPrice;
        return this;
    }

    public void setPrice(TRAPrice tRAPrice) {
        this.price = tRAPrice;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public TRACampaign network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public TRAStatus getStatus() {
        return status;
    }

    public TRACampaign status(TRAStatus tRAStatus) {
        this.status = tRAStatus;
        return this;
    }

    public void setStatus(TRAStatus tRAStatus) {
        this.status = tRAStatus;
    }

    public Set<SCHEmission> getEmissions() {
        return emissions;
    }

    public TRACampaign emissions(Set<SCHEmission> sCHEmissions) {
        this.emissions = sCHEmissions;
        return this;
    }

    public TRACampaign addEmissions(SCHEmission sCHEmission) {
        emissions.add(sCHEmission);
        sCHEmission.setTRACampaign(this);
        return this;
    }

    public TRACampaign removeEmissions(SCHEmission sCHEmission) {
        emissions.remove(sCHEmission);
        sCHEmission.setTRACampaign(null);
        return this;
    }

    public void setEmissions(Set<SCHEmission> sCHEmissions) {
        this.emissions = sCHEmissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TRACampaign tRACampaign = (TRACampaign) o;
        if (tRACampaign.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tRACampaign.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRACampaign{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", prize='" + prize + "'" +
            '}';
    }
}
