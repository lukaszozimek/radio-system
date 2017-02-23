package io.protone.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CorCountry entity.
 */
public class CorCountryDTO implements Serializable {

    private Long id;

    private String name;

    private String shortName;

    private Long taxId;

    private Long currnecyId;

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
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long corTaxId) {
        this.taxId = corTaxId;
    }

    public Long getCurrnecyId() {
        return currnecyId;
    }

    public void setCurrnecyId(Long corCurrencyId) {
        this.currnecyId = corCurrencyId;
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

        CorCountryDTO corCountryDTO = (CorCountryDTO) o;

        if ( ! Objects.equals(id, corCountryDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorCountryDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shortName='" + shortName + "'" +
            '}';
    }
}
