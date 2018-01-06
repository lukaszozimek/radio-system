package io.protone.core.api.dto;

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


    @NotNull
    private CorOrganizationDTO corOrganizationDTO;


    @Override
    public String toString() {
        return "CorNetworkDTO{" +
                "id=" + id +
                ", shortcut='" + shortcut + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", publicUrl='" + publicUrl + '\'' +
                '}';
    }

    private String publicUrl = null;

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

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public CorNetworkDTO publicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
        return this;
    }

    public CorOrganizationDTO getCorOrganizationDTO() {
        return corOrganizationDTO;
    }

    public void setCorOrganizationDTO(CorOrganizationDTO corOrganizationDTO) {
        this.corOrganizationDTO = corOrganizationDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorNetworkDTO corChannelDTO = (CorNetworkDTO) o;

        return Objects.equals(id, corChannelDTO.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, shortcut, name, description, publicUrl);
    }


}

