package io.protone.traffic.api.dto;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.traffic.api.dto.thin.TraCustomerThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * TraAdvertisementDTO
 */

public class TraAdvertisementDTO implements Serializable {

    private Long id = null;

    private TraCustomerThinDTO customerId = null;

    @NotNull
    private String name = null;

    private String description = null;

    private CorDictionaryDTO industryId = null;

    private List<LibMediaItemThinDTO> libMediaItemThinDTOList = null;

    private CorDictionaryDTO typeId;


    public TraAdvertisementDTO type(CorDictionaryDTO typePT) {
        this.typeId = typePT;
        return this;
    }

    @ApiModelProperty(value = "")
    public CorDictionaryDTO getTypeId() {
        return typeId;
    }

    public void setTypeId(CorDictionaryDTO typeId) {
        this.typeId = typeId;
    }

    public TraAdvertisementDTO customerId(TraCustomerThinDTO customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * Get customerId
     *
     * @return customerId
     **/
    @ApiModelProperty(value = "")
    public TraCustomerThinDTO getCustomerId() {
        return customerId;
    }

    public void setCustomerId(TraCustomerThinDTO customerId) {
        this.customerId = customerId;
    }

    public TraAdvertisementDTO description(String description) {
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

    public TraAdvertisementDTO id(Long id) {
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

    public TraAdvertisementDTO industryId(CorDictionaryDTO industryId) {
        this.industryId = industryId;
        return this;
    }

    /**
     * Get industryId
     *
     * @return industryId
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getIndustryId() {
        return industryId;
    }

    public void setIndustryId(CorDictionaryDTO industryId) {
        this.industryId = industryId;
    }

    public TraAdvertisementDTO mediaItemId(List<LibMediaItemThinDTO> mediaItemId) {
        this.libMediaItemThinDTOList = mediaItemId;
        return this;
    }

    /**
     * Get libMediaItemThinDTOList
     *
     * @return libMediaItemThinDTOList
     **/
    @ApiModelProperty(value = "")
    public List<LibMediaItemThinDTO> getLibMediaItemThinDTOList() {
        return libMediaItemThinDTOList;
    }

    public void setLibMediaItemThinDTOList(List<LibMediaItemThinDTO> libMediaItemThinDTOList) {
        this.libMediaItemThinDTOList = libMediaItemThinDTOList;
    }

    public TraAdvertisementDTO name(String name) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraAdvertisementDTO traAdvertisementDTO = (TraAdvertisementDTO) o;
        return Objects.equals(this.customerId, traAdvertisementDTO.customerId) &&
                Objects.equals(this.description, traAdvertisementDTO.description) &&
                Objects.equals(this.id, traAdvertisementDTO.id) &&
                Objects.equals(this.industryId, traAdvertisementDTO.industryId) &&
                Objects.equals(this.libMediaItemThinDTOList, traAdvertisementDTO.libMediaItemThinDTOList) &&
                Objects.equals(this.name, traAdvertisementDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, description, id, industryId, libMediaItemThinDTOList, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraAdvertisementDTO {\n");

        sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    industryId: ").append(toIndentedString(industryId)).append("\n");
        sb.append("    libMediaItemThinDTOList: ").append(toIndentedString(libMediaItemThinDTOList)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

