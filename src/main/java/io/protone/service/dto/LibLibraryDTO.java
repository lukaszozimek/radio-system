package io.protone.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.protone.domain.enumeration.LibCounterTypeEnum;
import io.protone.domain.enumeration.LibObjectTypeEnum;

/**
 * A DTO for the LibLibrary entity.
 */
public class LibLibraryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 1)
    private String prefix;

    @NotNull
    private Integer idxLength;

    @NotNull
    @Size(max = 3)
    private String shortcut;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    private Long counter;

    private LibCounterTypeEnum counterType;

    @NotNull
    private LibObjectTypeEnum libraryType;

    private String description;

    private Long networkId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public Integer getIdxLength() {
        return idxLength;
    }

    public void setIdxLength(Integer idxLength) {
        this.idxLength = idxLength;
    }
    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }
    public LibCounterTypeEnum getCounterType() {
        return counterType;
    }

    public void setCounterType(LibCounterTypeEnum counterType) {
        this.counterType = counterType;
    }
    public LibObjectTypeEnum getLibraryType() {
        return libraryType;
    }

    public void setLibraryType(LibObjectTypeEnum libraryType) {
        this.libraryType = libraryType;
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

        LibLibraryDTO libLibraryDTO = (LibLibraryDTO) o;

        if ( ! Objects.equals(id, libLibraryDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibLibraryDTO{" +
            "id=" + id +
            ", prefix='" + prefix + "'" +
            ", idxLength='" + idxLength + "'" +
            ", shortcut='" + shortcut + "'" +
            ", name='" + name + "'" +
            ", counter='" + counter + "'" +
            ", counterType='" + counterType + "'" +
            ", libraryType='" + libraryType + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
