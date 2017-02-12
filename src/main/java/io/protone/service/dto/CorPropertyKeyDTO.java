package io.protone.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CorPropertyKey entity.
 */
public class CorPropertyKeyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String key;

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

        CorPropertyKeyDTO corPropertyKeyDTO = (CorPropertyKeyDTO) o;

        if ( ! Objects.equals(id, corPropertyKeyDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorPropertyKeyDTO{" +
            "id=" + id +
            ", key='" + key + "'" +
            '}';
    }
}
