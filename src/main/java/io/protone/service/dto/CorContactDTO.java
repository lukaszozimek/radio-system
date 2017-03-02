package io.protone.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.protone.domain.enumeration.CorContactTypeEnum;

/**
 * A DTO for the CorContact entity.
 */
public class CorContactDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String contact;

    @NotNull
    private CorContactTypeEnum contactType;

    private Long networkId;

    private Long personId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public CorContactTypeEnum getContactType() {
        return contactType;
    }

    public void setContactType(CorContactTypeEnum contactType) {
        this.contactType = contactType;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long corPersonId) {
        this.personId = corPersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorContactDTO corContactDTO = (CorContactDTO) o;

        if ( ! Objects.equals(id, corContactDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorContactDTO{" +
            "id=" + id +
            ", contact='" + contact + "'" +
            ", contactType='" + contactType + "'" +
            '}';
    }
}
