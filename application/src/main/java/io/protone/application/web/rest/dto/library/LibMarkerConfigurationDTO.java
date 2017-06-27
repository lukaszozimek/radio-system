package io.protone.application.web.rest.dto.library;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.protone.domain.enumeration.LibMarkerTypeEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * LibMarkerConfigurationDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class LibMarkerConfigurationDTO implements Serializable {
    @NotNull
    private String color = null;

    @NotNull
    private String displayName = null;

    private Long id = null;

    @NotNull
    private String keyboardShortcut = null;

    @NotNull
    private String name = null;

    @NotNull
    private LibMarkerTypeEnum type = null;

    /**
     * Gets or Sets type
     */


    @JsonCreator
    public static LibMarkerTypeEnum fromValue(String text) {
        for (LibMarkerTypeEnum b : LibMarkerTypeEnum.values()) {
            if (String.valueOf(b).equals(text)) {
                return b;
            }
        }
        return null;
    }

    public LibMarkerConfigurationDTO color(String color) {
        this.color = color;
        return this;
    }

    /**
     * Get color
     *
     * @return color
     **/
    @ApiModelProperty(required = true, value = "")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LibMarkerConfigurationDTO displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get displayName
     *
     * @return displayName
     **/
    @ApiModelProperty(required = true, value = "")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LibMarkerConfigurationDTO id(Long id) {
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

    public LibMarkerConfigurationDTO keyboardShortcut(String keyboardShortcut) {
        this.keyboardShortcut = keyboardShortcut;
        return this;
    }

    /**
     * Get keyboardShortcut
     *
     * @return keyboardShortcut
     **/
    @ApiModelProperty(required = true, value = "")
    public String getKeyboardShortcut() {
        return keyboardShortcut;
    }

    public void setKeyboardShortcut(String keyboardShortcut) {
        this.keyboardShortcut = keyboardShortcut;
    }

    public LibMarkerConfigurationDTO name(String name) {
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

    public LibMarkerConfigurationDTO type(LibMarkerTypeEnum type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     *
     * @return type
     **/
    @ApiModelProperty(required = true, value = "")
    public LibMarkerTypeEnum getType() {
        return type;
    }

    public void setType(LibMarkerTypeEnum type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibMarkerConfigurationDTO libMarkerConfigurationDTO = (LibMarkerConfigurationDTO) o;
        return Objects.equals(this.color, libMarkerConfigurationDTO.color) &&
            Objects.equals(this.displayName, libMarkerConfigurationDTO.displayName) &&
            Objects.equals(this.id, libMarkerConfigurationDTO.id) &&
            Objects.equals(this.keyboardShortcut, libMarkerConfigurationDTO.keyboardShortcut) &&
            Objects.equals(this.name, libMarkerConfigurationDTO.name) &&
            Objects.equals(this.type, libMarkerConfigurationDTO.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, displayName, id, keyboardShortcut, name, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LibMarkerConfigurationDTO {\n");

        sb.append("    color: ").append(toIndentedString(color)).append("\n");
        sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    keyboardShortcut: ").append(toIndentedString(keyboardShortcut)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

