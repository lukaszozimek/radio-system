package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TRACustomer.
 */
@Entity
@Table(name = "tra_customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TRACustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "size")
    private Long size;

    @Column(name = "range")
    private Long range;

    @Column(name = "area")
    private String area;

    @Column(name = "vat_number")
    private String vatNumber;

    @Column(name = "id_number_1")
    private String idNumber1;

    @Column(name = "id_number_2")
    private String idNumber2;

    @Column(name = "payment_date")
    private Integer paymentDate;

    @ManyToOne
    private TRAIndustry industry;

    @ManyToOne
    private CORNetwork network;

    @ManyToOne
    private User account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TRACustomer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public TRACustomer size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getRange() {
        return range;
    }

    public TRACustomer range(Long range) {
        this.range = range;
        return this;
    }

    public void setRange(Long range) {
        this.range = range;
    }

    public String getArea() {
        return area;
    }

    public TRACustomer area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public TRACustomer vatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
        return this;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getIdNumber1() {
        return idNumber1;
    }

    public TRACustomer idNumber1(String idNumber1) {
        this.idNumber1 = idNumber1;
        return this;
    }

    public void setIdNumber1(String idNumber1) {
        this.idNumber1 = idNumber1;
    }

    public String getIdNumber2() {
        return idNumber2;
    }

    public TRACustomer idNumber2(String idNumber2) {
        this.idNumber2 = idNumber2;
        return this;
    }

    public void setIdNumber2(String idNumber2) {
        this.idNumber2 = idNumber2;
    }

    public Integer getPaymentDate() {
        return paymentDate;
    }

    public TRACustomer paymentDate(Integer paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(Integer paymentDate) {
        this.paymentDate = paymentDate;
    }

    public TRAIndustry getIndustry() {
        return industry;
    }

    public TRACustomer industry(TRAIndustry tRAIndustry) {
        this.industry = tRAIndustry;
        return this;
    }

    public void setIndustry(TRAIndustry tRAIndustry) {
        this.industry = tRAIndustry;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public TRACustomer network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public User getAccount() {
        return account;
    }

    public TRACustomer account(User user) {
        this.account = user;
        return this;
    }

    public void setAccount(User user) {
        this.account = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TRACustomer tRACustomer = (TRACustomer) o;
        if (tRACustomer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tRACustomer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRACustomer{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", size='" + size + "'" +
            ", range='" + range + "'" +
            ", area='" + area + "'" +
            ", vatNumber='" + vatNumber + "'" +
            ", idNumber1='" + idNumber1 + "'" +
            ", idNumber2='" + idNumber2 + "'" +
            ", paymentDate='" + paymentDate + "'" +
            '}';
    }
}
