package io.protone.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CrmLead entity.
 */
public class CrmLeadDTO implements Serializable {

    private Long id;

    private String name;

    private String shortname;

    private String description;

    private Long personId;

    private Long addresId;

    private Long leadStatusId;

    private Long leadSourceId;

    private Long keeperId;

    private Long industryId;

    private Long areaId;

    private Long networkId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long corPersonId) {
        this.personId = corPersonId;
    }

    public Long getAddresId() {
        return addresId;
    }

    public void setAddresId(Long corAddressId) {
        this.addresId = corAddressId;
    }

    public Long getLeadStatusId() {
        return leadStatusId;
    }

    public void setLeadStatusId(Long crmLeadStatusId) {
        this.leadStatusId = crmLeadStatusId;
    }

    public Long getLeadSourceId() {
        return leadSourceId;
    }

    public void setLeadSourceId(Long crmLeadSourceId) {
        this.leadSourceId = crmLeadSourceId;
    }

    public Long getKeeperId() {
        return keeperId;
    }

    public void setKeeperId(Long corUserId) {
        this.keeperId = corUserId;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long traIndustryId) {
        this.industryId = traIndustryId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long corAreaId) {
        this.areaId = corAreaId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CrmLeadDTO crmLeadDTO = (CrmLeadDTO) o;

        if ( ! Objects.equals(id, crmLeadDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmLeadDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shortname='" + shortname + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
