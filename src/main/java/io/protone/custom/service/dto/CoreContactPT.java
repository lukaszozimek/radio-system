package io.protone.custom.service.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.domain.enumeration.CorContactTypeEnum;
import io.protone.service.dto.CorContactDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * CoreContactPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CoreContactPT   {
    private Long id;

    @NotNull
    @Size(max = 100)
    private String contact;

    @NotNull
    private CorContactTypeEnum contactType;


    private Long networkId;

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

        CoreContactPT cORContactDTO = (CoreContactPT) o;

        if ( ! Objects.equals(id, cORContactDTO.id)) return false;

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

