package io.protone.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the CrmStage entity.
 */
public class CrmStageDTO implements Serializable {

    private Long id;

    private String name;


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

        CrmStageDTO cRMStageDTO = (CrmStageDTO) o;

        if ( ! Objects.equals(id, cRMStageDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmStageDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
