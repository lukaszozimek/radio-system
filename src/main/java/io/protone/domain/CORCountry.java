package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CORCountry.
 */
@Entity
@Table(name = "cor_country")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORCountry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @OneToOne
    @JoinColumn(unique = true)
    private CORTax tax;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToOne
    @JoinColumn(unique = true)
    private CORCurrency currnecy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CORCountry name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public CORCountry shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CORTax getTax() {
        return tax;
    }

    public CORCountry tax(CORTax cORTax) {
        this.tax = cORTax;
        return this;
    }

    public void setTax(CORTax cORTax) {
        this.tax = cORTax;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CORCountry network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public CORCurrency getCurrnecy() {
        return currnecy;
    }

    public CORCountry currnecy(CORCurrency cORCurrency) {
        this.currnecy = cORCurrency;
        return this;
    }

    public void setCurrnecy(CORCurrency cORCurrency) {
        this.currnecy = cORCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORCountry cORCountry = (CORCountry) o;
        if (cORCountry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORCountry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORCountry{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shortName='" + shortName + "'" +
            '}';
    }
}
