package io.protone.web.rest.dto.cor;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * CorChannelDTO
 */
public class CorChannelDTO implements Serializable {
    private Long id = null;

    @NotNull
    private String shortcut = null;

    @NotNull
    private String name = null;

    private String description = null;

    public CorChannelDTO id(Long id) {
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

    public CorChannelDTO shortcut(String shortcut) {
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

    public CorChannelDTO name(String name) {
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

    public CorChannelDTO description(String description) {
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
        CorChannelDTO corChannelDTO = (CorChannelDTO) o;
        return Objects.equals(this.id, corChannelDTO.id) &&
            Objects.equals(this.shortcut, corChannelDTO.shortcut) &&
            Objects.equals(this.name, corChannelDTO.name) &&
            Objects.equals(this.description, corChannelDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortcut, name, description);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CorChannelDTO {\n");

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

