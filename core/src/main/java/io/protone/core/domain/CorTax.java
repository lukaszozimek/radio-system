package io.protone.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CorTax.
 */
@Entity
@Table(name = "cor_tax")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorTax implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    private CorNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CorTax name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public CorTax value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public CorTax validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public CorTax validTo(LocalDate validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public Boolean isActive() {
        return active;
    }

    public CorTax active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CorTax network(CorNetwork corNetwork) {
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
        CorTax corTax = (CorTax) o;
        if (corTax.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corTax.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorTax{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", value='" + value + "'" +
            ", validFrom='" + validFrom + "'" +
            ", validTo='" + validTo + "'" +
            ", active='" + active + "'" +
            '}';
    }
}
