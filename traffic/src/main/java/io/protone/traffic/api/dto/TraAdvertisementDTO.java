package io.protone.traffic.api.dto;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.traffic.api.dto.thin.TraCustomerThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

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

    private CoreUserThinDTO createdBy;

    private ZonedDateTime createdDate;

    private CoreUserThinDTO lastModifiedBy;

    private ZonedDateTime lastModifiedDate;


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

    public CoreUserThinDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CoreUserThinDTO createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public CoreUserThinDTO getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(CoreUserThinDTO lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraAdvertisementDTO that = (TraAdvertisementDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (industryId != null ? !industryId.equals(that.industryId) : that.industryId != null) return false;
        if (libMediaItemThinDTOList != null ? !libMediaItemThinDTOList.equals(that.libMediaItemThinDTOList) : that.libMediaItemThinDTOList != null)
            return false;
        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastModifiedBy != null ? !lastModifiedBy.equals(that.lastModifiedBy) : that.lastModifiedBy != null)
            return false;
        return lastModifiedDate != null ? lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (industryId != null ? industryId.hashCode() : 0);
        result = 31 * result + (libMediaItemThinDTOList != null ? libMediaItemThinDTOList.hashCode() : 0);
        result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraAdvertisementDTO{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", industryId=" + industryId +
                ", libMediaItemThinDTOList=" + libMediaItemThinDTOList +
                ", typeId=" + typeId +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}

