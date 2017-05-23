package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CorAddress.
 */
@Entity
@Table(name = "cor_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "street", length = 100 )
    private String street;

    @NotNull
    @Size(max = 100)
    @Column(name = "number", length = 100)
    private String number;

    @NotNull
    @Size(max = 100)
    @Column(name = "postal_code", length = 100)
    private String postalCode;

    @NotNull
    @Size(max = 100)
    @Column(name = "city", length = 100)
    private String city;

    @NotNull
    @Size(max = 100)
    @Column(name = "country", length = 100)
    private String country;

    @ManyToOne
    private CorNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public CorAddress street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public CorAddress number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public CorAddress postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public CorAddress city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public CorAddress country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CorAddress network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CorAddress corAddress = (CorAddress) o;
        if (corAddress.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corAddress.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorAddress{" +
            "id=" + id +
            ", street='" + street + "'" +
            ", number='" + number + "'" +
            ", postalCode='" + postalCode + "'" +
            ", city='" + city + "'" +
            ", country='" + country + "'" +
            '}';
    }
}
