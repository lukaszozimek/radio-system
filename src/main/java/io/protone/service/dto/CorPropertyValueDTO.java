package io.protone.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CorPropertyValue entity.
 */
public class CorPropertyValueDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String value;

    private Long propertyKeyId;

    private Long libItemPropertyValueId;

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

    public void setPropertyKeyId(Long corPropertyKeyId) {
        this.propertyKeyId = corPropertyKeyId;
    }

    public Long getLibItemPropertyValueId() {
        return libItemPropertyValueId;
    }

    public void setLibItemPropertyValueId(Long libMediaItemId) {
        this.libItemPropertyValueId = libMediaItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorPropertyValueDTO corPropertyValueDTO = (CorPropertyValueDTO) o;

        if ( ! Objects.equals(id, corPropertyValueDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorPropertyValueDTO{" +
            "id=" + id +
            ", value='" + value + "'" +
            '}';
    }
}
