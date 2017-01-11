package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Column(name = "short_name")
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

    @ManyToOne
    private CORNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getExternalId1() {
        return externalId1;
    }

    public CRMContact externalId1(String externalId1) {
        this.externalId1 = externalId1;
        return this;
    }

    public void setExternalId1(String externalId1) {
        this.externalId1 = externalId1;
    }

    public String getExternalId2() {
        return externalId2;
    }

    public CRMContact externalId2(String externalId2) {
        this.externalId2 = externalId2;
        return this;
    }

    public void setExternalId2(String externalId2) {
        this.externalId2 = externalId2;
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

    public Integer getPaymentDelay() {
        return paymentDelay;
    }

    public CRMContact paymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
        return this;
    }

    public void setPaymentDelay(Integer paymentDelay) {
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
            ", shortName='" + shortName + "'" +
            ", externalId1='" + externalId1 + "'" +
            ", externalId2='" + externalId2 + "'" +
            ", name='" + name + "'" +
            ", paymentDelay='" + paymentDelay + "'" +
            ", vatNumber='" + vatNumber + "'" +
            '}';
    }
}
