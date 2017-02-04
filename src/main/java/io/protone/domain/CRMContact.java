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
 * A CRMContact.
 */
@Entity
@Table(name = "crm_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CRMContact implements Serializable {

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

    @Column(name = "payment_delay")
    private Long paymentDelay;

    @Column(name = "vat_number")
    private String vatNumber;

    @Column(name = "short_name")
    private String shortName;

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
    private User keeper;

    @OneToOne
    @JoinColumn(unique = true)
    private CRMContactStatus status;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToMany(mappedBy = "cRMContact")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CRMTask> tasks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNumber1() {
        return idNumber1;
    }

    public CRMContact idNumber1(String idNumber1) {
        this.idNumber1 = idNumber1;
        return this;
    }

    public void setIdNumber1(String idNumber1) {
        this.idNumber1 = idNumber1;
    }

    public String getIdNumber2() {
        return idNumber2;
    }

    public CRMContact idNumber2(String idNumber2) {
        this.idNumber2 = idNumber2;
        return this;
    }

    public void setIdNumber2(String idNumber2) {
        this.idNumber2 = idNumber2;
    }

    public String getName() {
        return name;
    }

    public CRMContact name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public CRMContact paymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getPaymentDelay() {
        return paymentDelay;
    }

    public CRMContact paymentDelay(Long paymentDelay) {
        this.paymentDelay = paymentDelay;
        return this;
    }

    public void setPaymentDelay(Long paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public CRMContact vatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
        return this;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getShortName() {
        return shortName;
    }

    public CRMContact shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CORAddress getAddres() {
        return addres;
    }

    public CRMContact addres(CORAddress cORAddress) {
        this.addres = cORAddress;
        return this;
    }

    public void setAddres(CORAddress cORAddress) {
        this.addres = cORAddress;
    }

    public CORCountry getCountry() {
        return country;
    }

    public CRMContact country(CORCountry cORCountry) {
        this.country = cORCountry;
        return this;
    }

    public void setCountry(CORCountry cORCountry) {
        this.country = cORCountry;
    }

    public CORRange getRange() {
        return range;
    }

    public CRMContact range(CORRange cORRange) {
        this.range = cORRange;
        return this;
    }

    public void setRange(CORRange cORRange) {
        this.range = cORRange;
    }

    public CORSize getSize() {
        return size;
    }

    public CRMContact size(CORSize cORSize) {
        this.size = cORSize;
        return this;
    }

    public void setSize(CORSize cORSize) {
        this.size = cORSize;
    }

    public TRAIndustry getIndustry() {
        return industry;
    }

    public CRMContact industry(TRAIndustry tRAIndustry) {
        this.industry = tRAIndustry;
        return this;
    }

    public void setIndustry(TRAIndustry tRAIndustry) {
        this.industry = tRAIndustry;
    }

    public CORArea getArea() {
        return area;
    }

    public CRMContact area(CORArea cORArea) {
        this.area = cORArea;
        return this;
    }

    public void setArea(CORArea cORArea) {
        this.area = cORArea;
    }

    public CORPerson getPerson() {
        return person;
    }

    public CRMContact person(CORPerson cORPerson) {
        this.person = cORPerson;
        return this;
    }

    public void setPerson(CORPerson cORPerson) {
        this.person = cORPerson;
    }

    public User getKeeper() {
        return keeper;
    }

    public CRMContact keeper(User cORUser) {
        this.keeper = cORUser;
        return this;
    }

    public void setKeeper(User cORUser) {
        this.keeper = cORUser;
    }

    public CRMContactStatus getStatus() {
        return status;
    }

    public CRMContact status(CRMContactStatus cRMContactStatus) {
        this.status = cRMContactStatus;
        return this;
    }

    public void setStatus(CRMContactStatus cRMContactStatus) {
        this.status = cRMContactStatus;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CRMContact network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public Set<CRMTask> getTasks() {
        return tasks;
    }

    public CRMContact tasks(Set<CRMTask> cRMTasks) {
        this.tasks = cRMTasks;
        return this;
    }

    public CRMContact addTasks(CRMTask cRMTask) {
        tasks.add(cRMTask);
        cRMTask.setCRMContact(this);
        return this;
    }

    public CRMContact removeTasks(CRMTask cRMTask) {
        tasks.remove(cRMTask);
        cRMTask.setCRMContact(null);
        return this;
    }

    public void setTasks(Set<CRMTask> cRMTasks) {
        this.tasks = cRMTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CRMContact cRMContact = (CRMContact) o;
        if (cRMContact.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cRMContact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CRMContact{" +
            "id=" + id +
            ", idNumber1='" + idNumber1 + "'" +
            ", idNumber2='" + idNumber2 + "'" +
            ", name='" + name + "'" +
            ", paymentDate='" + paymentDate + "'" +
            ", paymentDelay='" + paymentDelay + "'" +
            ", vatNumber='" + vatNumber + "'" +
            ", shortName='" + shortName + "'" +
            '}';
    }
}
