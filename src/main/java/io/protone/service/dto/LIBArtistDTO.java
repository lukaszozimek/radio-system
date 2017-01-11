package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.protone.domain.enumeration.LIBArtistTypeEnum;

/**
 * A DTO for the LIBArtist entity.
 */
public class LIBArtistDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    private LIBArtistTypeEnum type;

    private String description;


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
    public LIBArtistTypeEnum getType() {
        return type;
    }

    public void setType(LIBArtistTypeEnum type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        LIBArtistDTO lIBArtistDTO = (LIBArtistDTO) o;

        if ( ! Objects.equals(id, lIBArtistDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBArtistDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
