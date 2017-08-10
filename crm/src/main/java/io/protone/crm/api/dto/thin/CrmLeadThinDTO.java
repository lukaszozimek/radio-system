package io.protone.crm.api.dto.thin;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.CoreAddressDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.crm.api.dto.CrmCustomerPersonDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * CrmLeadDTO
 */

public class CrmLeadThinDTO {

    private Long id = null;

    @NotNull
    private String name = null;

    @NotNull
    private String shortname = null;

    private String description = null;

    private CorDictionaryDTO source = null;

    private CorDictionaryDTO status = null;

    private CoreAddressDTO addres = null;

    private CorDictionaryDTO area = null;

    private CorDictionaryDTO industry = null;

    private CoreUserThinDTO leadOwner = null;

    private CrmCustomerPersonDTO person = null;

    private CoreUserThinDTO createdBy;

    private ZonedDateTime createdDate;

    private CoreUserThinDTO lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    public CrmLeadThinDTO id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(required = true, value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CrmLeadThinDTO name(String name) {
        this.name = name;
        return this;
    }

    public String getShortname() {
        return this.shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public CrmLeadThinDTO shortName(String shortname) {
        this.shortname = shortname;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrmLeadThinDTO description(String description) {
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

    public CrmLeadThinDTO source(CorDictionaryDTO source) {
        this.source = source;
        return this;
    }

    @ApiModelProperty(value = "")
    public CoreUserThinDTO getOwner() {
        return leadOwner;
    }

    public void setOwner(CoreUserThinDTO leadOwner) {
        this.leadOwner = leadOwner;
    }

    public CrmLeadThinDTO owner(CoreUserThinDTO leadOwner) {
        this.leadOwner = leadOwner;
        return this;
    }

    /**
     * Get source
     *
     * @return source
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getSource() {
        return source;
    }

    public void setSource(CorDictionaryDTO source) {
        this.source = source;
    }

    public CrmLeadThinDTO status(CorDictionaryDTO status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getStatus() {
        return status;
    }

    public void setStatus(CorDictionaryDTO status) {
        this.status = status;
    }

    public CrmLeadThinDTO addres(CoreAddressDTO addres) {
        this.addres = addres;
        return this;
    }

    /**
     * Get addres
     *
     * @return addres
     **/
    @ApiModelProperty(value = "")
    public CoreAddressDTO getAddres() {
        return addres;
    }

    public void setAddres(CoreAddressDTO addres) {
        this.addres = addres;
    }

    public CrmLeadThinDTO area(CorDictionaryDTO area) {
        this.area = area;
        return this;
    }

    /**
     * Get area
     *
     * @return area
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getArea() {
        return area;
    }

    public void setArea(CorDictionaryDTO area) {
        this.area = area;
    }

    public CrmLeadThinDTO industry(CorDictionaryDTO industry) {
        this.industry = industry;
        return this;
    }

    /**
     * Get industry
     *
     * @return industry
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getIndustry() {
        return industry;
    }

    public void setIndustry(CorDictionaryDTO industry) {
        this.industry = industry;
    }

    public CrmLeadThinDTO person(CrmCustomerPersonDTO person) {
        this.person = person;
        return this;
    }

    /**
     * Get person
     *
     * @return person
     **/
    @ApiModelProperty(value = "")
    public CrmCustomerPersonDTO getPerson() {
        return person;
    }

    public void setPerson(CrmCustomerPersonDTO person) {
        this.person = person;
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


    public CoreUserThinDTO getLeadOwner() {
        return leadOwner;
    }

    public void setLeadOwner(CoreUserThinDTO leadOwner) {
        this.leadOwner = leadOwner;
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

        CrmLeadThinDTO that = (CrmLeadThinDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (shortname != null ? !shortname.equals(that.shortname) : that.shortname != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (addres != null ? !addres.equals(that.addres) : that.addres != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (industry != null ? !industry.equals(that.industry) : that.industry != null) return false;
        if (leadOwner != null ? !leadOwner.equals(that.leadOwner) : that.leadOwner != null) return false;
        if (person != null ? !person.equals(that.person) : that.person != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastModifiedBy != null ? !lastModifiedBy.equals(that.lastModifiedBy) : that.lastModifiedBy != null)
            return false;
        return lastModifiedDate != null ? lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortname != null ? shortname.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (addres != null ? addres.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (industry != null ? industry.hashCode() : 0);
        result = 31 * result + (leadOwner != null ? leadOwner.hashCode() : 0);
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CrmLeadThinDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortname='" + shortname + '\'' +
                ", description='" + description + '\'' +
                ", source=" + source +
                ", status=" + status +
                ", addres=" + addres +
                ", area=" + area +
                ", industry=" + industry +
                ", leadOwner=" + leadOwner +
                ", person=" + person +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}

