package io.protone.crm.api.dto.thin;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.CoreAddressDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.crm.api.dto.CrmCustomerPersonDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrmLeadThinDTO crmLeadDTO = (CrmLeadThinDTO) o;
        return Objects.equals(this.id, crmLeadDTO.id) &&
            Objects.equals(this.name, crmLeadDTO.name) &&
            Objects.equals(this.description, crmLeadDTO.description) &&
            Objects.equals(this.source, crmLeadDTO.source) &&
            Objects.equals(this.status, crmLeadDTO.status) &&
            Objects.equals(this.addres, crmLeadDTO.addres) &&
            Objects.equals(this.area, crmLeadDTO.area) &&
            Objects.equals(this.industry, crmLeadDTO.industry) &&
            Objects.equals(this.person, crmLeadDTO.person) &&
            Objects.equals(this.shortname, crmLeadDTO.shortname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, source, status, addres, area, industry, person, shortname);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CrmLeadDTO {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    source: ").append(toIndentedString(source)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    addres: ").append(toIndentedString(addres)).append("\n");
        sb.append("    area: ").append(toIndentedString(area)).append("\n");
        sb.append("    industry: ").append(toIndentedString(industry)).append("\n");
        sb.append("    person: ").append(toIndentedString(person)).append("\n");
        sb.append("    shortname: ").append(toIndentedString(shortname)).append("\n");
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

