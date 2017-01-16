package io.protone.custom.service.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.domain.enumeration.LIBMarkerTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ConfMarkerConfigurationPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class ConfMarkerConfigurationPT {
    @JsonProperty("color")
    private String color = null;

    @JsonProperty("displayName")
    private String displayName = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("keyboardShortcut")
    private String keyboardShortcut = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("networkId")
    private Long networkId = null;
    @JsonProperty("type")
    private LIBMarkerTypeEnum type = null;

    /**
     * Gets or Sets type
     */


    @JsonCreator
    public static LIBMarkerTypeEnum fromValue(String text) {
        for (LIBMarkerTypeEnum b : LIBMarkerTypeEnum.values()) {
            if (String.valueOf(b).equals(text)) {
                return b;
            }
        }
        return null;
    }

    public ConfMarkerConfigurationPT color(String color) {
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

    public ConfMarkerConfigurationPT displayName(String displayName) {
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

    public ConfMarkerConfigurationPT id(Long id) {
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

    public ConfMarkerConfigurationPT keyboardShortcut(String keyboardShortcut) {
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

    public ConfMarkerConfigurationPT name(String name) {
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

    public ConfMarkerConfigurationPT networkId(Long networkId) {
        this.networkId = networkId;
        return this;
    }

    /**
     * Get networkId
     *
     * @return networkId
     **/
    @ApiModelProperty(value = "")
    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public ConfMarkerConfigurationPT type(LIBMarkerTypeEnum type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     *
     * @return type
     **/
    @ApiModelProperty(required = true, value = "")
    public LIBMarkerTypeEnum getType() {
        return type;
    }

    public void setType(LIBMarkerTypeEnum type) {
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
        ConfMarkerConfigurationPT confMarkerConfigurationPT = (ConfMarkerConfigurationPT) o;
        return Objects.equals(this.color, confMarkerConfigurationPT.color) &&
            Objects.equals(this.displayName, confMarkerConfigurationPT.displayName) &&
            Objects.equals(this.id, confMarkerConfigurationPT.id) &&
            Objects.equals(this.keyboardShortcut, confMarkerConfigurationPT.keyboardShortcut) &&
            Objects.equals(this.name, confMarkerConfigurationPT.name) &&
            Objects.equals(this.networkId, confMarkerConfigurationPT.networkId) &&
            Objects.equals(this.type, confMarkerConfigurationPT.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, displayName, id, keyboardShortcut, name, networkId, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ConfMarkerConfigurationPT {\n");

        sb.append("    color: ").append(toIndentedString(color)).append("\n");
        sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    keyboardShortcut: ").append(toIndentedString(keyboardShortcut)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
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

