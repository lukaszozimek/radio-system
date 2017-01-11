package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the CORPropertyValue entity.
 */
public class CORPropertyValueDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String value;


    private Long propertyKeyId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getPropertyKeyId() {
        return propertyKeyId;
    }

    public void setPropertyKeyId(Long cORPropertyKeyId) {
        this.propertyKeyId = cORPropertyKeyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CORPropertyValueDTO cORPropertyValueDTO = (CORPropertyValueDTO) o;

        if ( ! Objects.equals(id, cORPropertyValueDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORPropertyValueDTO{" +
            "id=" + id +
            ", value='" + value + "'" +
            '}';
    }
}
