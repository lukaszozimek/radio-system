package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CORCurrency.
 */
@Entity
@Table(name = "cor_currency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORCurrency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "name")
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "delimiter")
    private String delimiter;

    @Column(name = "short_name")
    private String shortName;

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

    public CORCurrency name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public CORCurrency symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public CORCurrency delimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getShortName() {
        return shortName;
    }

    public CORCurrency shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CORCurrency network(CORNetwork cORNetwork) {
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
        CORCurrency cORCurrency = (CORCurrency) o;
        if (cORCurrency.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORCurrency.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORCurrency{" +
            "id=" + id +
            ", networkId='"+
            ", name='" + name + "'" +
            ", symbol='" + symbol + "'" +
            ", delimiter='" + delimiter + "'" +
            ", shortName='" + shortName + "'" +
            '}';
    }
}
