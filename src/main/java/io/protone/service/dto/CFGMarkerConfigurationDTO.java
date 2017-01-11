package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.protone.domain.enumeration.LIBMarkerTypeEnum;

/**
 * A DTO for the CFGMarkerConfiguration entity.
 */
public class CFGMarkerConfigurationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 100)
    private String displayName;

    @NotNull
    @Size(max = 100)
    private String color;

    @NotNull
    @Size(max = 100)
    private String keyboardShortcut;

    @NotNull
    private LIBMarkerTypeEnum type;


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
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public String getKeyboardShortcut() {
        return keyboardShortcut;
    }

    public void setKeyboardShortcut(String keyboardShortcut) {
        this.keyboardShortcut = keyboardShortcut;
    }
    public LIBMarkerTypeEnum getType() {
        return type;
    }

    public void setType(LIBMarkerTypeEnum type) {
        this.type = type;
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

        CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO = (CFGMarkerConfigurationDTO) o;

        if ( ! Objects.equals(id, cFGMarkerConfigurationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CFGMarkerConfigurationDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", displayName='" + displayName + "'" +
            ", color='" + color + "'" +
            ", keyboardShortcut='" + keyboardShortcut + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
