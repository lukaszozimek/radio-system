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
    private CorAddress addres;

    @OneToOne
    @JoinColumn(unique = true)
    private CorCountry country;

    @OneToOne
    @JoinColumn(unique = true)
    private CorPerson person;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    private CorRange range;

    @ManyToOne
    private CorSize size;

    @ManyToOne
    private TraIndustry industry;

    @ManyToOne
    private CorArea area;

    @ManyToOne
    private CorUser keeper;

    @ManyToOne
    private CrmContactStatus status;

    @OneToMany(mappedBy = "contact")
    @JsonIgnore
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

    public CrmContact shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getExternalId1() {
        return externalId1;
    }

    public CrmContact externalId1(String externalId1) {
        this.externalId1 = externalId1;
        return this;
    }

    public void setExternalId1(String externalId1) {
        this.externalId1 = externalId1;
    }

    public String getExternalId2() {
        return externalId2;
    }

    public CrmContact externalId2(String externalId2) {
        this.externalId2 = externalId2;
        return this;
    }

    public void setExternalId2(String externalId2) {
        this.externalId2 = externalId2;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public CrmContact paymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getName() {
        return name;
    }

    public CrmContact name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPaymentDelay() {
        return paymentDelay;
    }

    public CrmContact paymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
        return this;
    }

    public void setPaymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public CrmContact vatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
        return this;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public CorAddress getAddres() {
        return addres;
    }

    public CrmContact addres(CorAddress corAddress) {
        this.addres = corAddress;
        return this;
    }

    public void setAddres(CorAddress corAddress) {
        this.addres = corAddress;
    }

    public CorCountry getCountry() {
        return country;
    }

    public CrmContact country(CorCountry corCountry) {
        this.country = corCountry;
        return this;
    }

    public void setCountry(CorCountry corCountry) {
        this.country = corCountry;
    }

    public CorPerson getPerson() {
        return person;
    }

    public CrmContact person(CorPerson corPerson) {
        this.person = corPerson;
        return this;
    }

    public void setPerson(CorPerson corPerson) {
        this.person = corPerson;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CrmContact network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CorRange getRange() {
        return range;
    }

    public CrmContact range(CorRange corRange) {
        this.range = corRange;
        return this;
    }

    public void setRange(CorRange corRange) {
        this.range = corRange;
    }

    public CorSize getSize() {
        return size;
    }

    public CrmContact size(CorSize corSize) {
        this.size = corSize;
        return this;
    }

    public void setSize(CorSize corSize) {
        this.size = corSize;
    }

    public TraIndustry getIndustry() {
        return industry;
    }

    public CrmContact industry(TraIndustry traIndustry) {
        this.industry = traIndustry;
        return this;
    }

    public void setIndustry(TraIndustry traIndustry) {
        this.industry = traIndustry;
    }

    public CorArea getArea() {
        return area;
    }

    public CrmContact area(CorArea corArea) {
        this.area = corArea;
        return this;
    }

    public void setArea(CorArea corArea) {
        this.area = corArea;
    }

    public CorUser getKeeper() {
        return keeper;
    }

    public CrmContact keeper(CorUser corUser) {
        this.keeper = corUser;
        return this;
    }

    public void setKeeper(CorUser corUser) {
        this.keeper = corUser;
    }

    public CrmContactStatus getStatus() {
        return status;
    }

    public CrmContact status(CrmContactStatus crmContactStatus) {
        this.status = crmContactStatus;
        return this;
    }

    public void setStatus(CrmContactStatus crmContactStatus) {
        this.status = crmContactStatus;
    }

    public Set<CrmTask> getTasks() {
        return tasks;
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
