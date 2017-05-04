package io.protone.web.rest.dto.library;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.domain.enumeration.LibCounterTypeEnum;
import io.protone.domain.enumeration.LibObjectTypeEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * LibLibraryDTO
 */

public class LibLibraryDTO implements Serializable {

    private Long id = null;

    @NotNull
    private String name = null;

    @NotNull
    private Long counter;

    private LibCounterTypeEnum counterType;

    @NotNull
    private LibObjectTypeEnum libraryType;

    @NotNull
    private Integer idxLength;

    @NotNull
    private String shortcut = null;

    @NotNull
    private String prefix = null;


    private String description = null;

    public LibLibraryDTO id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LibLibraryDTO prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Get prefix
     *
     * @return prefix
     **/
    @ApiModelProperty(required = true, value = "")
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public LibLibraryDTO name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(required = true, value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public LibLibraryDTO description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     *
     * @return description
     **/
    @ApiModelProperty(value = "")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LibLibraryDTO)) return false;

        LibLibraryDTO libLibraryDTO = (LibLibraryDTO) o;

        if (getId() != null ? !getId().equals(libLibraryDTO.getId()) : libLibraryDTO.getId() != null) return false;
        if (getPrefix() != null ? !getPrefix().equals(libLibraryDTO.getPrefix()) : libLibraryDTO.getPrefix() != null)
            return false;
        if (getName() != null ? !getName().equals(libLibraryDTO.getName()) : libLibraryDTO.getName() != null) return false;
        if (getCounter() != null ? !getCounter().equals(libLibraryDTO.getCounter()) : libLibraryDTO.getCounter() != null)
            return false;
        if (getCounterType() != null ? !getCounterType().equals(libLibraryDTO.getCounterType()) : libLibraryDTO.getCounterType() != null)
            return false;
        if (getLibraryType() != null ? !getLibraryType().equals(libLibraryDTO.getLibraryType()) : libLibraryDTO.getLibraryType() != null)
            return false;
        if (getIdxLength() != null ? !getIdxLength().equals(libLibraryDTO.getIdxLength()) : libLibraryDTO.getIdxLength() != null)
            return false;
        if (getShortcut() != null ? !getShortcut().equals(libLibraryDTO.getShortcut()) : libLibraryDTO.getShortcut() != null)
            return false;
        if (getPrefix() != null ? !getPrefix().equals(libLibraryDTO.getPrefix()) : libLibraryDTO.getPrefix() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(libLibraryDTO.getDescription()) : libLibraryDTO.getDescription() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getPrefix() != null ? getPrefix().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCounter() != null ? getCounter().hashCode() : 0);
        result = 31 * result + (getCounterType() != null ? getCounterType().hashCode() : 0);
        result = 31 * result + (getLibraryType() != null ? getLibraryType().hashCode() : 0);
        result = 31 * result + (getIdxLength() != null ? getIdxLength().hashCode() : 0);
        result = 31 * result + (getShortcut() != null ? getShortcut().hashCode() : 0);
        result = 31 * result + (getPrefix() != null ? getPrefix().hashCode() : 0);

        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LibLibraryDTO{" +
            "id=" + id +
            ", prefix='" + prefix + '\'' +
            ", name='" + name + '\'' +
            ", counter=" + counter +
            ", counterType='" + counterType + '\'' +
            ", libraryType='" + libraryType + '\'' +
            ", idxLength=" + idxLength +
            ", shortcut='" + shortcut + '\'' +
            ", prefix='" + prefix + '\'' +
            ", description='" + description + '\'' +
            '}';
    }

    /**
     * Gets or Sets type
     */
    public enum TypeEnum {
        IMAGE("LT_IMAGE"),

        AUDIO("LT_AUDIO"),

        VIDEO("LT_VIDEO"),

        FILE("LT_FILE");

        private String value;

        TypeEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static TypeEnum fromValue(String text) {
            for (TypeEnum b : TypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }
}