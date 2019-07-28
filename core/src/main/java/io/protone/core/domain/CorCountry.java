package io.protone.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CorCountry.
 */
@Entity
@Table(name = "cor_country")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorCountry extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_name", nullable = false, unique = true)
    private String shortName;

    @OneToOne
    @JoinColumn(unique = true)
    private CorTax tax;

    @OneToOne
    @JoinColumn(unique = true)
    private CorCurrency currnecy;

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

    public CorCountry name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public CorCountry shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CorTax getTax() {
        return tax;
    }

    public CorCountry tax(CorTax corTax) {
        this.tax = corTax;
        return this;
    }

    public void setTax(CorTax corTax) {
        this.tax = corTax;
    }

    public CorCurrency getCurrnecy() {
        return currnecy;
    }

    public CorCountry currnecy(CorCurrency corCurrency) {
        this.currnecy = corCurrency;
        return this;
    }

    public void setCurrnecy(CorCurrency corCurrency) {
        this.currnecy = corCurrency;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CorCountry network(CorNetwork corNetwork) {
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
        CorCountry corCountry = (CorCountry) o;
        if (corCountry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corCountry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorCountry{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shortName='" + shortName + "'" +
            '}';
    }
}
