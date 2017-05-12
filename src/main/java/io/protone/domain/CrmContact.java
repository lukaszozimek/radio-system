package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A CrmContact.
 */
@Entity
@Table(name = "crm_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "external_id_1")
    private String externalId1;

    @Column(name = "external_id_2")
    private String externalId2;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "name")
    private String name;

    @Column(name = "payment_delay")
    private Integer paymentDelay;

    @Column(name = "vat_number")
    private String vatNumber;

    @OneToOne
    @JoinColumn(unique = true)
    @PodamExclude
    private CorAddress addres;

    @OneToOne
    @JoinColumn(unique = true)
    @PodamExclude
    private CorCountry country;

    @OneToOne
    @JoinColumn(unique = true)
    @PodamExclude
    private CorPerson person;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

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

    @ManyToOne
    @PodamExclude
    private CorUser keeper;

    @ManyToOne
    @PodamExclude
    private CorDictionary status;

    @OneToMany(mappedBy = "contact")
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

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CrmContact shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getExternalId1() {
        return externalId1;
    }

    public void setExternalId1(String externalId1) {
        this.externalId1 = externalId1;
    }

    public CrmContact externalId1(String externalId1) {
        this.externalId1 = externalId1;
        return this;
    }

    public String getExternalId2() {
        return externalId2;
    }

    public void setExternalId2(String externalId2) {
        this.externalId2 = externalId2;
    }

    public CrmContact externalId2(String externalId2) {
        this.externalId2 = externalId2;
        return this;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public CrmContact paymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrmContact name(String name) {
        this.name = name;
        return this;
    }

    public Integer getPaymentDelay() {
        return paymentDelay;
    }

    public void setPaymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public CrmContact paymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
        return this;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public CrmContact vatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
        return this;
    }

    public CorAddress getAddres() {
        return addres;
    }

    public void setAddres(CorAddress corAddress) {
        this.addres = corAddress;
    }

    public CrmContact addres(CorAddress corAddress) {
        this.addres = corAddress;
        return this;
    }

    public CorCountry getCountry() {
        return country;
    }

    public void setCountry(CorCountry corCountry) {
        this.country = corCountry;
    }

    public CrmContact country(CorCountry corCountry) {
        this.country = corCountry;
        return this;
    }

    public CorPerson getPerson() {
        return person;
    }

    public void setPerson(CorPerson corPerson) {
        this.person = corPerson;
    }

    public CrmContact person(CorPerson corPerson) {
        this.person = corPerson;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CrmContact network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public CorDictionary getRange() {
        return range;
    }

    public void setRange(CorDictionary corRange) {
        this.range = corRange;
    }

    public CrmContact range(CorDictionary corRange) {
        this.range = corRange;
        return this;
    }

    public CorDictionary getSize() {
        return size;
    }

    public void setSize(CorDictionary corSize) {
        this.size = corSize;
    }

    public CrmContact size(CorDictionary corSize) {
        this.size = corSize;
        return this;
    }

    public CorDictionary getIndustry() {
        return industry;
    }

    public void setIndustry(CorDictionary traIndustry) {
        this.industry = traIndustry;
    }

    public CrmContact industry(CorDictionary traIndustry) {
        this.industry = traIndustry;
        return this;
    }

    public CorDictionary getArea() {
        return area;
    }

    public void setArea(CorDictionary corArea) {
        this.area = corArea;
    }

    public CrmContact area(CorDictionary corArea) {
        this.area = corArea;
        return this;
    }

    public CorUser getKeeper() {
        return keeper;
    }

    public void setKeeper(CorUser corUser) {
        this.keeper = corUser;
    }

    public CrmContact keeper(CorUser corUser) {
        this.keeper = corUser;
        return this;
    }

    public CorDictionary getStatus() {
        return status;
    }

    public void setStatus(CorDictionary crmContactStatus) {
        this.status = crmContactStatus;
    }

    public CrmContact status(CorDictionary crmContactStatus) {
        this.status = crmContactStatus;
        return this;
    }

    public Set<CrmTask> getTasks() {
        return tasks;
    }

    public void setTasks(Set<CrmTask> crmTasks) {
        this.tasks = crmTasks;
    }

    public CrmContact tasks(Set<CrmTask> crmTasks) {
        this.tasks = crmTasks;
        return this;
    }

    public CrmContact addTasks(CrmTask crmTask) {
        this.tasks.add(crmTask);
        crmTask.setContact(this);
        return this;
    }

    public CrmContact removeTasks(CrmTask crmTask) {
        this.tasks.remove(crmTask);
        crmTask.setContact(null);
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
        CrmContact crmContact = (CrmContact) o;
        if (crmContact.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, crmContact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmContact{" +
            "id=" + id +
            ", shortName='" + shortName + "'" +
            ", externalId1='" + externalId1 + "'" +
            ", externalId2='" + externalId2 + "'" +
            ", paymentDate='" + paymentDate + "'" +
            ", name='" + name + "'" +
            ", paymentDelay='" + paymentDelay + "'" +
            ", vatNumber='" + vatNumber + "'" +
            '}';
    }
}
