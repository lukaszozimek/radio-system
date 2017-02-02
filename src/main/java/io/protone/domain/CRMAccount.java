package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A CRMAccount.
 */
@Entity
@Table(name = "crm_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CRMAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_number_1")
    private String idNumber1;

    @Column(name = "id_number_2")
    private String idNumber2;

    @Column(name = "name")
    private String name;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "vat_number")
    private String vatNumber;

    @Column(name = "payment_delay")
    private Long paymentDelay;

    @Column(name = "get_short_name")
    private String getShortName;

    @OneToOne
    @JoinColumn(unique = true)
    private CORAddress addres;

    @OneToOne
    @JoinColumn(unique = true)
    private CORCountry country;

    @OneToOne
    @JoinColumn(unique = true)
    private CORRange range;

    @OneToOne
    @JoinColumn(unique = true)
    private CORSize size;

    @OneToOne
    @JoinColumn(unique = true)
    private TRAIndustry industry;

    @OneToOne
    @JoinColumn(unique = true)
    private CORArea area;

    @OneToOne
    @JoinColumn(unique = true)
    private CORPerson person;

    @OneToOne
    @JoinColumn(unique = true)
    private CORUser keeper;

    @OneToOne
    @JoinColumn(unique = true)
    private TRADiscount discount;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToMany(mappedBy = "cRMAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CRMOpportunity> opportunities = new HashSet<>();

    @OneToMany(mappedBy = "cRMAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CRMTask> tasks = new HashSet<>();

    @OneToMany(mappedBy = "cRMAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TRAOrder> orders = new HashSet<>();

    @OneToMany(mappedBy = "cRMAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TRACampaign> campaigns = new HashSet<>();

    @OneToMany(mappedBy = "cRMAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TRAInvoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "cRMAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TRAAdvertisement> advertisments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNumber1() {
        return idNumber1;
    }

    public CRMAccount idNumber1(String idNumber1) {
        this.idNumber1 = idNumber1;
        return this;
    }

    public void setIdNumber1(String idNumber1) {
        this.idNumber1 = idNumber1;
    }

    public String getIdNumber2() {
        return idNumber2;
    }

    public CRMAccount idNumber2(String idNumber2) {
        this.idNumber2 = idNumber2;
        return this;
    }

    public void setIdNumber2(String idNumber2) {
        this.idNumber2 = idNumber2;
    }

    public String getName() {
        return name;
    }

    public CRMAccount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public CRMAccount paymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public CRMAccount vatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
        return this;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public Long getPaymentDelay() {
        return paymentDelay;
    }

    public CRMAccount paymentDelay(Long paymentDelay) {
        this.paymentDelay = paymentDelay;
        return this;
    }

    public void setPaymentDelay(Long paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public String getGetShortName() {
        return getShortName;
    }

    public CRMAccount getShortName(String getShortName) {
        this.getShortName = getShortName;
        return this;
    }

    public void setGetShortName(String getShortName) {
        this.getShortName = getShortName;
    }

    public CORAddress getAddres() {
        return addres;
    }

    public CRMAccount addres(CORAddress cORAddress) {
        this.addres = cORAddress;
        return this;
    }

    public void setAddres(CORAddress cORAddress) {
        this.addres = cORAddress;
    }

    public CORCountry getCountry() {
        return country;
    }

    public CRMAccount country(CORCountry cORCountry) {
        this.country = cORCountry;
        return this;
    }

    public void setCountry(CORCountry cORCountry) {
        this.country = cORCountry;
    }

    public CORRange getRange() {
        return range;
    }

    public CRMAccount range(CORRange cORRange) {
        this.range = cORRange;
        return this;
    }

    public void setRange(CORRange cORRange) {
        this.range = cORRange;
    }

    public CORSize getSize() {
        return size;
    }

    public CRMAccount size(CORSize cORSize) {
        this.size = cORSize;
        return this;
    }

    public void setSize(CORSize cORSize) {
        this.size = cORSize;
    }

    public TRAIndustry getIndustry() {
        return industry;
    }

    public CRMAccount industry(TRAIndustry tRAIndustry) {
        this.industry = tRAIndustry;
        return this;
    }

    public void setIndustry(TRAIndustry tRAIndustry) {
        this.industry = tRAIndustry;
    }

    public CORArea getArea() {
        return area;
    }

    public CRMAccount area(CORArea cORArea) {
        this.area = cORArea;
        return this;
    }

    public void setArea(CORArea cORArea) {
        this.area = cORArea;
    }

    public CORPerson getPerson() {
        return person;
    }

    public CRMAccount person(CORPerson cORPerson) {
        this.person = cORPerson;
        return this;
    }

    public void setPerson(CORPerson cORPerson) {
        this.person = cORPerson;
    }

    public CORUser getKeeper() {
        return keeper;
    }

    public CRMAccount keeper(CORUser cORUser) {
        this.keeper = cORUser;
        return this;
    }

    public void setKeeper(CORUser cORUser) {
        this.keeper = cORUser;
    }

    public TRADiscount getDiscount() {
        return discount;
    }

    public CRMAccount discount(TRADiscount tRADiscount) {
        this.discount = tRADiscount;
        return this;
    }

    public void setDiscount(TRADiscount tRADiscount) {
        this.discount = tRADiscount;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CRMAccount network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public Set<CRMOpportunity> getOpportunities() {
        return opportunities;
    }

    public CRMAccount opportunities(Set<CRMOpportunity> cRMOpportunities) {
        this.opportunities = cRMOpportunities;
        return this;
    }

    public CRMAccount addOpportunity(CRMOpportunity cRMOpportunity) {
        opportunities.add(cRMOpportunity);
        cRMOpportunity.setCRMAccount(this);
        return this;
    }

    public CRMAccount removeOpportunity(CRMOpportunity cRMOpportunity) {
        opportunities.remove(cRMOpportunity);
        cRMOpportunity.setCRMAccount(null);
        return this;
    }

    public void setOpportunities(Set<CRMOpportunity> cRMOpportunities) {
        this.opportunities = cRMOpportunities;
    }

    public Set<CRMTask> getTasks() {
        return tasks;
    }

    public CRMAccount tasks(Set<CRMTask> cRMTasks) {
        this.tasks = cRMTasks;
        return this;
    }

    public CRMAccount addTask(CRMTask cRMTask) {
        tasks.add(cRMTask);
        cRMTask.setCRMAccount(this);
        return this;
    }

    public CRMAccount removeTask(CRMTask cRMTask) {
        tasks.remove(cRMTask);
        cRMTask.setCRMAccount(null);
        return this;
    }

    public void setTasks(Set<CRMTask> cRMTasks) {
        this.tasks = cRMTasks;
    }

    public Set<TRAOrder> getOrders() {
        return orders;
    }

    public CRMAccount orders(Set<TRAOrder> tRAOrders) {
        this.orders = tRAOrders;
        return this;
    }

    public CRMAccount addOrders(TRAOrder tRAOrder) {
        orders.add(tRAOrder);
        tRAOrder.setCRMAccount(this);
        return this;
    }

    public CRMAccount removeOrders(TRAOrder tRAOrder) {
        orders.remove(tRAOrder);
        tRAOrder.setCRMAccount(null);
        return this;
    }

    public void setOrders(Set<TRAOrder> tRAOrders) {
        this.orders = tRAOrders;
    }

    public Set<TRACampaign> getCampaigns() {
        return campaigns;
    }

    public CRMAccount campaigns(Set<TRACampaign> tRACampaigns) {
        this.campaigns = tRACampaigns;
        return this;
    }

    public CRMAccount addCampaigns(TRACampaign tRACampaign) {
        campaigns.add(tRACampaign);
        tRACampaign.setCRMAccount(this);
        return this;
    }

    public CRMAccount removeCampaigns(TRACampaign tRACampaign) {
        campaigns.remove(tRACampaign);
        tRACampaign.setCRMAccount(null);
        return this;
    }

    public void setCampaigns(Set<TRACampaign> tRACampaigns) {
        this.campaigns = tRACampaigns;
    }

    public Set<TRAInvoice> getInvoices() {
        return invoices;
    }

    public CRMAccount invoices(Set<TRAInvoice> tRAInvoices) {
        this.invoices = tRAInvoices;
        return this;
    }

    public CRMAccount addInvoices(TRAInvoice tRAInvoice) {
        invoices.add(tRAInvoice);
        tRAInvoice.setCRMAccount(this);
        return this;
    }

    public CRMAccount removeInvoices(TRAInvoice tRAInvoice) {
        invoices.remove(tRAInvoice);
        tRAInvoice.setCRMAccount(null);
        return this;
    }

    public void setInvoices(Set<TRAInvoice> tRAInvoices) {
        this.invoices = tRAInvoices;
    }

    public Set<TRAAdvertisement> getAdvertisments() {
        return advertisments;
    }

    public CRMAccount advertisments(Set<TRAAdvertisement> tRAAdvertisements) {
        this.advertisments = tRAAdvertisements;
        return this;
    }

    public CRMAccount addAdvertisments(TRAAdvertisement tRAAdvertisement) {
        advertisments.add(tRAAdvertisement);
        tRAAdvertisement.setCRMAccount(this);
        return this;
    }

    public CRMAccount removeAdvertisments(TRAAdvertisement tRAAdvertisement) {
        advertisments.remove(tRAAdvertisement);
        tRAAdvertisement.setCRMAccount(null);
        return this;
    }

    public void setAdvertisments(Set<TRAAdvertisement> tRAAdvertisements) {
        this.advertisments = tRAAdvertisements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CRMAccount cRMAccount = (CRMAccount) o;
        if (cRMAccount.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cRMAccount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CRMAccount{" +
            "id=" + id +
            ", idNumber1='" + idNumber1 + "'" +
            ", idNumber2='" + idNumber2 + "'" +
            ", name='" + name + "'" +
            ", paymentDate='" + paymentDate + "'" +
            ", vatNumber='" + vatNumber + "'" +
            ", paymentDelay='" + paymentDelay + "'" +
            ", getShortName='" + getShortName + "'" +
            '}';
    }
}
