package io.protone.library.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.core.api.dto.CorChannelDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * LibMediaLibraryDTO
 */

public class LibMediaLibraryDTO implements Serializable {

    private Long id = null;

    @NotNull
    private String name = null;

    @NotNull
    private Long counter;

    @NotNull
    private String shortcut = null;

    @NotNull
    private String prefix = null;

    private List<CorChannelDTO> channels = new ArrayList();

    private String description = null;


    private String publicUrl = null;

    public LibMediaLibraryDTO id(Long id) {
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

    public LibMediaLibraryDTO prefix(String prefix) {
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

    public LibMediaLibraryDTO name(String name) {
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


    public LibMediaLibraryDTO description(String description) {
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


    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public List<CorChannelDTO> getChannels() {
        return channels;
    }

    public void setChannels(List<CorChannelDTO> channels) {
        this.channels = channels;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public LibMediaLibraryDTO publicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
        return this;
    }

    @Override
    public String toString() {
        return "LibMediaLibraryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", counter=" + counter +
                ", shortcut='" + shortcut + '\'' +
                ", prefix='" + prefix + '\'' +
                ", channels=" + channels +
                ", description='" + description + '\'' +
                ", publicUrl='" + publicUrl + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LibMediaLibraryDTO)) return false;

        LibMediaLibraryDTO libMediaLibraryDTO = (LibMediaLibraryDTO) o;

        if (getId() != null ? !getId().equals(libMediaLibraryDTO.getId()) : libMediaLibraryDTO.getId() != null) return false;
        if (getPrefix() != null ? !getPrefix().equals(libMediaLibraryDTO.getPrefix()) : libMediaLibraryDTO.getPrefix() != null)
            return false;
        if (getName() != null ? !getName().equals(libMediaLibraryDTO.getName()) : libMediaLibraryDTO.getName() != null)
            return false;
        if (getCounter() != null ? !getCounter().equals(libMediaLibraryDTO.getCounter()) : libMediaLibraryDTO.getCounter() != null)
            return false;
        if (getShortcut() != null ? !getShortcut().equals(libMediaLibraryDTO.getShortcut()) : libMediaLibraryDTO.getShortcut() != null)
            return false;
        if (getPrefix() != null ? !getPrefix().equals(libMediaLibraryDTO.getPrefix()) : libMediaLibraryDTO.getPrefix() != null)
            return false;
        return getDescription() != null ? getDescription().equals(libMediaLibraryDTO.getDescription()) : libMediaLibraryDTO.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getPrefix() != null ? getPrefix().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCounter() != null ? getCounter().hashCode() : 0);
        result = 31 * result + (getShortcut() != null ? getShortcut().hashCode() : 0);
        result = 31 * result + (getPrefix() != null ? getPrefix().hashCode() : 0);

        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
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
