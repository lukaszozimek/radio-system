package io.protone.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.*;
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
@Table(name = "crm_account", uniqueConstraints =
@UniqueConstraint(columnNames = {"short_name", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmAccount extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @PodamExclude
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "short_name", nullable = false)
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
    @JoinColumn
    private CorPerson person;

    @OneToOne
    @PodamExclude
    @JoinColumn
    private CorAddress addres;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CrmDiscount discount;

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


    @OneToOne
    @JoinColumn(unique = true)
    @PodamExclude
    private CorImageItem corImageItem;

    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
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

    public CrmAccount shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getExternalId1() {
        return externalId1;
    }

    public void setExternalId1(String externalId1) {
        this.externalId1 = externalId1;
    }

    public CrmAccount externalId1(String externalId1) {
        this.externalId1 = externalId1;
        return this;
    }

    public String getExternalId2() {
        return externalId2;
    }

    public void setExternalId2(String externalId2) {
        this.externalId2 = externalId2;
    }

    public CrmAccount externalId2(String externalId2) {
        this.externalId2 = externalId2;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrmAccount name(String name) {
        this.name = name;
        return this;
    }

    public Integer getPaymentDelay() {
        return paymentDelay;
    }

    public void setPaymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public CrmAccount paymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
        return this;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public CrmAccount vatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
        return this;
    }

    public CorPerson getPerson() {
        return person;
    }

    public void setPerson(CorPerson corPerson) {
        this.person = corPerson;
    }

    public CrmAccount person(CorPerson corPerson) {
        this.person = corPerson;
        return this;
    }

    public CorAddress getAddres() {
        return addres;
    }

    public void setAddres(CorAddress corAddress) {
        this.addres = corAddress;
    }

    public CrmAccount addres(CorAddress corAddress) {
        this.addres = corAddress;
        return this;
    }

    public CorImageItem getCorImageItem() {
        return corImageItem;
    }

    public void setCorImageItem(CorImageItem corImageItem) {
        this.corImageItem = corImageItem;
    }

    public CrmAccount avatar(CorImageItem corImageItem) {
        this.corImageItem = corImageItem;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CrmAccount network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public CrmDiscount getDiscount() {
        return discount;
    }

    public void setDiscount(CrmDiscount traDiscount) {
        this.discount = traDiscount;
    }

    public CrmAccount discount(CrmDiscount traDiscount) {
        this.discount = traDiscount;
        return this;
    }

    public CorUser getKeeper() {
        return keeper;
    }

    public void setKeeper(CorUser corUser) {
        this.keeper = corUser;
    }

    public CrmAccount keeper(CorUser corUser) {
        this.keeper = corUser;
        return this;
    }

    public CorCountry getCountry() {
        return country;
    }

    public void setCountry(CorCountry corCountry) {
        this.country = corCountry;
    }

    public CrmAccount country(CorCountry corCountry) {
        this.country = corCountry;
        return this;
    }

    public CorDictionary getRange() {
        return range;
    }

    public void setRange(CorDictionary corRange) {
        this.range = corRange;
    }

    public CrmAccount range(CorDictionary corRange) {
        this.range = corRange;
        return this;
    }

    public CorDictionary getSize() {
        return size;
    }

    public void setSize(CorDictionary corSize) {
        this.size = corSize;
    }

    public CrmAccount size(CorDictionary corSize) {
        this.size = corSize;
        return this;
    }

    public CorDictionary getIndustry() {
        return industry;
    }

    public void setIndustry(CorDictionary traIndustry) {
        this.industry = traIndustry;
    }

    public CrmAccount industry(CorDictionary traIndustry) {
        this.industry = traIndustry;
        return this;
    }

    public CorDictionary getArea() {
        return area;
    }

    public void setArea(CorDictionary corArea) {
        this.area = corArea;
    }

    public CrmAccount area(CorDictionary corArea) {
        this.area = corArea;
        return this;
    }

    public Set<CrmTask> getTasks() {
        return tasks;
    }

    public void setTasks(Set<CrmTask> crmTasks) {
        this.tasks = crmTasks;
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
