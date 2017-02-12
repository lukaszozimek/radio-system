package io.protone.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CorCurrency entity.
 */
public class CorCurrencyDTO implements Serializable {

    private Long id;

    private String name;

    private String symbol;

    private String delimiter;

    private String shortName;

    private Long networkId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorCurrencyDTO corCurrencyDTO = (CorCurrencyDTO) o;

        if ( ! Objects.equals(id, corCurrencyDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorCurrencyDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", symbol='" + symbol + "'" +
            ", delimiter='" + delimiter + "'" +
            ", shortName='" + shortName + "'" +
            '}';
    }
}
