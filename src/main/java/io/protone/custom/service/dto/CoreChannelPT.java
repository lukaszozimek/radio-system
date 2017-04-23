package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.service.dto.CorUserDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * CoreChannelPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CoreChannelPT {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("shortcut")
    private String shortcut = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("description")
    private String description = null;

    public CoreChannelPT id(Long id) {
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

    public CoreChannelPT shortcut(String shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    /**
     * Get shortcut
     *
     * @return shortcut
     **/
    @ApiModelProperty(required = true, value = "")
    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public CoreChannelPT name(String name) {
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

    public CoreChannelPT description(String description) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoreChannelPT coreChannelPT = (CoreChannelPT) o;
        return Objects.equals(this.id, coreChannelPT.id) &&
            Objects.equals(this.shortcut, coreChannelPT.shortcut) &&
            Objects.equals(this.name, coreChannelPT.name) &&
            Objects.equals(this.description, coreChannelPT.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortcut, name, description);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CoreChannelPT {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    shortcut: ").append(toIndentedString(shortcut)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");

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

