package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CorCurrency.
 */
@Entity
@Table(name = "cor_currency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorCurrency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "delimiter")
    private String delimiter;

    @Column(name = "short_name")
    private String shortName;

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

    public CorCurrency name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public CorCurrency symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public CorCurrency delimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getShortName() {
        return shortName;
    }

    public CorCurrency shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CorCurrency network(CorNetwork corNetwork) {
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
        CorCurrency corCurrency = (CorCurrency) o;
        if (corCurrency.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corCurrency.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorCurrency{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", symbol='" + symbol + "'" +
            ", delimiter='" + delimiter + "'" +
            ", shortName='" + shortName + "'" +
            '}';
    }
}
