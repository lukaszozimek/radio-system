package io.protone.traffic.api.dto;

import io.protone.core.api.dto.CoreAddressDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A TraInvoice.
 */

public class TraCompanyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String shortName;
    @NotNull
    private String name;

    private String description;
    @NotNull
    private String taxId1;
    @NotNull
    private String taxId2;
    @NotNull
    private CoreAddressDTO addres;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaxId1() {
        return taxId1;
    }

    public void setTaxId1(String taxId1) {
        this.taxId1 = taxId1;
    }

    public String getTaxId2() {
        return taxId2;
    }

    public void setTaxId2(String taxId2) {
        this.taxId2 = taxId2;
    }

    public CoreAddressDTO getAddres() {
        return addres;
    }

    public void setAddres(CoreAddressDTO addres) {
        this.addres = addres;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraCompanyDTO that = (TraCompanyDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (taxId1 != null ? !taxId1.equals(that.taxId1) : that.taxId1 != null) return false;
        if (taxId2 != null ? !taxId2.equals(that.taxId2) : that.taxId2 != null) return false;
        return addres != null ? !addres.equals(that.addres) : that.addres != null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (taxId1 != null ? taxId1.hashCode() : 0);
        result = 31 * result + (taxId2 != null ? taxId2.hashCode() : 0);
        result = 31 * result + (addres != null ? addres.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraCompany{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taxId1='" + taxId1 + '\'' +
                ", taxId2='" + taxId2 + '\'' +
                ", addres=" + addres +
                '}';
    }
}
