package io.protone.application.web.rest.dto.cor;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * CorNetworkDTO
 */

public class CorNetworkDTO implements Serializable {

    private Long id = null;

    @NotNull
    private String shortcut = null;

    @NotNull
    private String name = null;

    private String description = null;

    public CorNetworkDTO id(Long id) {
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

    public CorNetworkDTO shortcut(String shortcut) {
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

    public CorNetworkDTO name(String name) {
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

    public CorNetworkDTO description(String description) {
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

        CorNetworkDTO corNetworkDTO = (CorNetworkDTO) o;

        if (!Objects.equals(id, corNetworkDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortcut, name, description);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CorNetworkDTO {\n");

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

