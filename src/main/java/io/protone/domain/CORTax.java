package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CORTax.
 */
@Entity
@Table(name = "cor_tax")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORTax implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "active")
    private Boolean active;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CORTax name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public CORTax value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean isActive() {
        return active;
    }

    public CORTax active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CORTax network(CORNetwork cORNetwork) {
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
        CORTax cORTax = (CORTax) o;
        if (cORTax.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORTax.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORTax{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", value='" + value + "'" +
            ", active='" + active + "'" +
            '}';
    }
}
