package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;


/**
 * A DTO for the CORDocument entity.
 */
public class CORDocumentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String key;

    private String description;

    @NotNull
    @Lob
    private String value;


    private Long networkId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

        CORDocumentDTO cORDocumentDTO = (CORDocumentDTO) o;

        if ( ! Objects.equals(id, cORDocumentDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORDocumentDTO{" +
            "id=" + id +
            ", key='" + key + "'" +
            ", description='" + description + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
