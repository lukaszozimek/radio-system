package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the CORAddress entity.
 */
public class CORAddressDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String street;

    @NotNull
    @Size(max = 100)
    private String number;

    @NotNull
    @Size(max = 100)
    private String postalCode;

    @NotNull
    @Size(max = 100)
    private String city;

    @NotNull
    @Size(max = 100)
    private String country;


    private Long networkId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long cORNetworkId) {
        this.networkId = cORNetworkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CORAddressDTO cORAddressDTO = (CORAddressDTO) o;

        if ( ! Objects.equals(id, cORAddressDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORAddressDTO{" +
            "id=" + id +
            ", street='" + street + "'" +
            ", number='" + number + "'" +
            ", postalCode='" + postalCode + "'" +
            ", city='" + city + "'" +
            ", country='" + country + "'" +
            '}';
    }
}
