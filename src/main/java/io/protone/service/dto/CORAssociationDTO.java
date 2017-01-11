package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the CORAssociation entity.
 */
public class CORAssociationDTO implements Serializable {

    private Long id;

    private String name;

    @NotNull
    private String sourceClass;

    @NotNull
    private Long sourceId;

    @NotNull
    private String targetClass;

    @NotNull
    private Long targetId;


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
    public String getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(String sourceClass) {
        this.sourceClass = sourceClass;
    }
    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }
    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
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

        CORAssociationDTO cORAssociationDTO = (CORAssociationDTO) o;

        if ( ! Objects.equals(id, cORAssociationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORAssociationDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", sourceClass='" + sourceClass + "'" +
            ", sourceId='" + sourceId + "'" +
            ", targetClass='" + targetClass + "'" +
            ", targetId='" + targetId + "'" +
            '}';
    }
}
