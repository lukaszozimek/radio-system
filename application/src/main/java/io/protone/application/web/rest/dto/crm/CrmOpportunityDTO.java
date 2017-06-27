package io.protone.application.web.rest.dto.crm;

import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.cor.thin.CoreUserThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private Long contactId = null;

    private Long leadId = null;

    private Long accountId = null;

    private LocalDate lastTry = null;

    private LocalDate closeDate = null;

    private CorDictionaryDTO stage = null;

    private Integer probability = null;

    private List<CrmTaskDTO> tasks = new ArrayList<CrmTaskDTO>();


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

    public CrmOpportunityDTO contactId(Long contact) {
        this.contactId = contact;
        return this;
    }

    /**
     * Get contactId
     *
     * @return contactId
     **/
    @ApiModelProperty(value = "")
    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
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
    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

    public CrmOpportunityDTO accountId(Long account) {
        this.accountId = account;
        return this;
    }

    /**
     * Get contactId
     *
     * @return contactId
     **/
    @ApiModelProperty(value = "")
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long account) {
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

    public CrmOpportunityDTO tasks(List<CrmTaskDTO> tasks) {
        this.tasks = tasks;
        return this;
    }

    public CrmOpportunityDTO addTasksItem(CrmTaskDTO tasksItem) {
        this.tasks.add(tasksItem);
        return this;
    }

    /**
     * Get tasks
     *
     * @return tasks
     **/
    @ApiModelProperty(value = "")
    public List<CrmTaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<CrmTaskDTO> tasks) {
        this.tasks = tasks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrmOpportunityDTO crmOpportunityDTO = (CrmOpportunityDTO) o;
        return Objects.equals(this.id, crmOpportunityDTO.id) &&
            Objects.equals(this.opportunityOwner, crmOpportunityDTO.opportunityOwner) &&
            Objects.equals(this.name, crmOpportunityDTO.name) &&
            Objects.equals(this.contactId, crmOpportunityDTO.contactId) &&
            Objects.equals(this.lastTry, crmOpportunityDTO.lastTry) &&
            Objects.equals(this.closeDate, crmOpportunityDTO.closeDate) &&
            Objects.equals(this.stage, crmOpportunityDTO.stage) &&
            Objects.equals(this.probability, crmOpportunityDTO.probability) &&
            Objects.equals(this.tasks, crmOpportunityDTO.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opportunityOwner, name, contactId, lastTry, closeDate, stage, probability, tasks);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CrmOpportunityDTO {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    opportunityOwner: ").append(toIndentedString(opportunityOwner)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
        sb.append("    contactId: ").append(toIndentedString(contactId)).append("\n");
        sb.append("    leadId: ").append(toIndentedString(leadId)).append("\n");
        sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
        sb.append("    lastTry: ").append(toIndentedString(lastTry)).append("\n");
        sb.append("    closeDate: ").append(toIndentedString(closeDate)).append("\n");
        sb.append("    stage: ").append(toIndentedString(stage)).append("\n");
        sb.append("    probability: ").append(toIndentedString(probability)).append("\n");
        sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
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

