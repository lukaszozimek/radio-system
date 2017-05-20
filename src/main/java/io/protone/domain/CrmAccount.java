package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A CrmAccount.
 */
@Entity
@Table(name = "crm_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @PodamExclude
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "short_name",nullable = false)
    private String shortName;

    @Column(name = "external_id_1")
    private String externalId1;

    @Column(name = "external_id_2")
    private String externalId2;

    @Column(name = "name")
    private String name;

    @Column(name = "payment_delay")
    private Integer paymentDelay;

    @Column(name = "vat_number")
    private String vatNumber;

    @OneToOne
    @PodamExclude
    @JoinColumn(unique = true)
    private CorPerson person;

    @OneToOne
    @PodamExclude
    @JoinColumn(unique = true)
    private CorAddress addres;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private TraDiscount discount;

    @ManyToOne
    @PodamExclude
    private CorUser keeper;

    @ManyToOne
    @PodamExclude
    private CorCountry country;

    @ManyToOne
    @PodamExclude
    private CorDictionary range;

    @ManyToOne
    @PodamExclude
    private CorDictionary size;

    @ManyToOne
    @PodamExclude
    private CorDictionary industry;

    @ManyToOne
    @PodamExclude
    private CorDictionary area;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    @PodamExclude
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CrmTask> tasks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public CrmAccount shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getExternalId1() {
        return externalId1;
    }

    public CrmAccount externalId1(String externalId1) {
        this.externalId1 = externalId1;
        return this;
    }

    public void setExternalId1(String externalId1) {
        this.externalId1 = externalId1;
    }

    public String getExternalId2() {
        return externalId2;
    }

    public CrmAccount externalId2(String externalId2) {
        this.externalId2 = externalId2;
        return this;
    }

    public void setExternalId2(String externalId2) {
        this.externalId2 = externalId2;
    }

    public String getName() {
        return name;
    }

    public CrmAccount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPaymentDelay() {
        return paymentDelay;
    }

    public CrmAccount paymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
        return this;
    }

    public void setPaymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public CrmAccount vatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
        return this;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public CorPerson getPerson() {
        return person;
    }

    public CrmAccount person(CorPerson corPerson) {
        this.person = corPerson;
        return this;
    }

    public void setPerson(CorPerson corPerson) {
        this.person = corPerson;
    }

    public CorAddress getAddres() {
        return addres;
    }

    public CrmAccount addres(CorAddress corAddress) {
        this.addres = corAddress;
        return this;
    }

    public void setAddres(CorAddress corAddress) {
        this.addres = corAddress;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CrmAccount network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraDiscount getDiscount() {
        return discount;
    }

    public CrmAccount discount(TraDiscount traDiscount) {
        this.discount = traDiscount;
        return this;
    }

    public void setDiscount(TraDiscount traDiscount) {
        this.discount = traDiscount;
    }

    public CorUser getKeeper() {
        return keeper;
    }

    public CrmAccount keeper(CorUser corUser) {
        this.keeper = corUser;
        return this;
    }

    public void setKeeper(CorUser corUser) {
        this.keeper = corUser;
    }

    public CorCountry getCountry() {
        return country;
    }

    public CrmAccount country(CorCountry corCountry) {
        this.country = corCountry;
        return this;
    }

    public void setCountry(CorCountry corCountry) {
        this.country = corCountry;
    }

    public CorDictionary getRange() {
        return range;
    }

    public CrmAccount range(CorDictionary corRange) {
        this.range = corRange;
        return this;
    }

    public void setRange(CorDictionary corRange) {
        this.range = corRange;
    }

    public CorDictionary getSize() {
        return size;
    }

    public CrmAccount size(CorDictionary corSize) {
        this.size = corSize;
        return this;
    }

    public void setSize(CorDictionary corSize) {
        this.size = corSize;
    }

    public CorDictionary getIndustry() {
        return industry;
    }

    public CrmAccount industry(CorDictionary traIndustry) {
        this.industry = traIndustry;
        return this;
    }

    public void setIndustry(CorDictionary traIndustry) {
        this.industry = traIndustry;
    }

    public CorDictionary getArea() {
        return area;
    }

    public CrmAccount area(CorDictionary corArea) {
        this.area = corArea;
        return this;
    }

    public void setArea(CorDictionary corArea) {
        this.area = corArea;
    }

    public Set<CrmTask> getTasks() {
        return tasks;
    }

    public CrmAccount tasks(Set<CrmTask> crmTasks) {
        this.tasks = crmTasks;
        return this;
    }

    public CrmAccount addTasks(CrmTask crmTask) {
        this.tasks.add(crmTask);
        crmTask.setAccount(this);
        return this;
    }

    public CrmAccount removeTasks(CrmTask crmTask) {
        this.tasks.remove(crmTask);
        crmTask.setAccount(null);
        return this;
    }

    public void setTasks(Set<CrmTask> crmTasks) {
        this.tasks = crmTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrmAccount crmAccount = (CrmAccount) o;
        if (crmAccount.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, crmAccount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmAccount{" +
            "id=" + id +
            ", shortName='" + shortName + "'" +
            ", externalId1='" + externalId1 + "'" +
            ", externalId2='" + externalId2 + "'" +
            ", name='" + name + "'" +
            ", paymentDelay='" + paymentDelay + "'" +
            ", vatNumber='" + vatNumber + "'" +
            '}';
    }
}
