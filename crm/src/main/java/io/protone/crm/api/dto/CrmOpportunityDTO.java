package io.protone.crm.api.dto;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.crm.api.dto.thin.CrmTaskThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * CrmOpportunityDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CrmOpportunityDTO implements Serializable {
    private Long id = null;

    private CoreUserThinDTO opportunityOwner = null;

    @NotNull
    private String name = null;

    @NotNull
    private String shortName = null;

    private String description = null;

    private String contactId = null;

    private String leadId = null;

    private String accountId = null;

    private LocalDate lastTry = null;

    private LocalDate closeDate = null;

    private CorDictionaryDTO stage = null;

    private Integer probability = null;

    private CoreUserThinDTO createdBy;

    private ZonedDateTime createdDate;

    private CoreUserThinDTO lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    private List<CrmTaskThinDTO> tasks = new ArrayList<CrmTaskThinDTO>();


    public CrmOpportunityDTO id(Long id) {
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

    public CrmOpportunityDTO opportunityOwner(CoreUserThinDTO opportunityOwner) {
        this.opportunityOwner = opportunityOwner;
        return this;
    }

    /**
     * Get opportunityOwner
     *
     * @return opportunityOwner
     **/
    @ApiModelProperty(value = "")
    public CoreUserThinDTO getOpportunityOwner() {
        return opportunityOwner;
    }

    public void setOpportunityOwner(CoreUserThinDTO opportunityOwner) {
        this.opportunityOwner = opportunityOwner;
    }

    public CrmOpportunityDTO name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(value = "")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CrmOpportunityDTO shortName(String shortName) {
        this.shortName = shortName;
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

    public CrmOpportunityDTO contactId(String contact) {
        this.contactId = contact;
        return this;
    }

    /**
     * Get contactId
     *
     * @return contactId
     **/
    @ApiModelProperty(value = "")
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public CrmOpportunityDTO lastTry(LocalDate lastTry) {
        this.lastTry = lastTry;
        return this;
    }

    public CrmOpportunityDTO LeadId(Long Lead) {
        this.leadId = leadId;
        return this;
    }

    /**
     * Get contactId
     *
     * @return contactId
     **/
    @ApiModelProperty(value = "")
    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public CrmOpportunityDTO accountId(String account) {
        this.accountId = account;
        return this;
    }

    /**
     * Get contactId
     *
     * @return contactId
     **/
    @ApiModelProperty(value = "")
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String account) {
        this.accountId = account;
    }

    /**
     * Get lastTry
     *
     * @return lastTry
     **/
    @ApiModelProperty(value = "")
    public LocalDate getLastTry() {
        return lastTry;
    }

    public void setLastTry(LocalDate lastTry) {
        this.lastTry = lastTry;
    }

    public CrmOpportunityDTO closeDate(LocalDate closeDate) {
        this.closeDate = closeDate;
        return this;
    }

    /**
     * Get closeDate
     *
     * @return closeDate
     **/
    @ApiModelProperty(value = "")
    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public CrmOpportunityDTO stage(CorDictionaryDTO stage) {
        this.stage = stage;
        return this;
    }

    /**
     * Get stage
     *
     * @return stage
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getStage() {
        return stage;
    }

    public void setStage(CorDictionaryDTO stage) {
        this.stage = stage;
    }

    public CrmOpportunityDTO propability(Integer propability) {
        this.probability = propability;
        return this;
    }

    /**
     * Get probability
     *
     * @return probability
     **/
    @ApiModelProperty(value = "")
    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public CrmOpportunityDTO tasks(List<CrmTaskThinDTO> tasks) {
        this.tasks = tasks;
        return this;
    }

    public CrmOpportunityDTO addTasksItem(CrmTaskThinDTO tasksItem) {
        this.tasks.add(tasksItem);
        return this;
    }

    /**
     * Get tasks
     *
     * @return tasks
     **/
    @ApiModelProperty(value = "")
    public List<CrmTaskThinDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<CrmTaskThinDTO> tasks) {
        this.tasks = tasks;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CrmOpportunityDTO that = (CrmOpportunityDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (opportunityOwner != null ? !opportunityOwner.equals(that.opportunityOwner) : that.opportunityOwner != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (contactId != null ? !contactId.equals(that.contactId) : that.contactId != null) return false;
        if (leadId != null ? !leadId.equals(that.leadId) : that.leadId != null) return false;
        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (lastTry != null ? !lastTry.equals(that.lastTry) : that.lastTry != null) return false;
        if (closeDate != null ? !closeDate.equals(that.closeDate) : that.closeDate != null) return false;
        if (stage != null ? !stage.equals(that.stage) : that.stage != null) return false;
        if (probability != null ? !probability.equals(that.probability) : that.probability != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastModifiedBy != null ? !lastModifiedBy.equals(that.lastModifiedBy) : that.lastModifiedBy != null)
            return false;
        if (lastModifiedDate != null ? !lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate != null)
            return false;
        if (tasks != null ? !tasks.equals(that.tasks) : that.tasks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (opportunityOwner != null ? opportunityOwner.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (contactId != null ? contactId.hashCode() : 0);
        result = 31 * result + (leadId != null ? leadId.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (lastTry != null ? lastTry.hashCode() : 0);
        result = 31 * result + (closeDate != null ? closeDate.hashCode() : 0);
        result = 31 * result + (stage != null ? stage.hashCode() : 0);
        result = 31 * result + (probability != null ? probability.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CrmOpportunityDTO{" +
                "id=" + id +
                ", opportunityOwner=" + opportunityOwner +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", description='" + description + '\'' +
                ", contactId=" + contactId +
                ", leadId=" + leadId +
                ", accountId=" + accountId +
                ", lastTry=" + lastTry +
                ", closeDate=" + closeDate +
                ", stage=" + stage +
                ", probability=" + probability +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                ", tasks=" + tasks +
                '}';
    }
}

