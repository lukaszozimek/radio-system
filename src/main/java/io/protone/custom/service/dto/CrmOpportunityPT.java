package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.service.dto.thin.CoreUserThinDTO;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CrmOpportunityPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CrmOpportunityPT {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("opportunityOwner")
    private CoreUserThinDTO opportunityOwner = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("contactId")
    private Long contactId = null;

    @JsonProperty("leadId")
    private Long leadId = null;

    @JsonProperty("accountId")
    private Long accountId = null;

    @JsonProperty("lastTry")
    private LocalDate lastTry = null;

    @JsonProperty("closeDate")
    private LocalDate closeDate = null;

    @JsonProperty("stage")
    private CorDictionaryPT stage = null;

    @JsonProperty("propability")
    private Integer propability = null;

    @JsonProperty("tasks")
    private List<CrmTaskPT> tasks = new ArrayList<CrmTaskPT>();


    public CrmOpportunityPT id(Long id) {
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

    public CrmOpportunityPT opportunityOwner(CoreUserThinDTO opportunityOwner) {
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

    public CrmOpportunityPT name(String name) {
        this.name = name;
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

    public CrmOpportunityPT contactId(Long contact) {
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

    public CrmOpportunityPT lastTry(LocalDate lastTry) {
        this.lastTry = lastTry;
        return this;
    }

    public CrmOpportunityPT LeadId(Long Lead) {
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

    public CrmOpportunityPT accountId(Long account) {
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

    public CrmOpportunityPT closeDate(LocalDate closeDate) {
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

    public CrmOpportunityPT stage(CorDictionaryPT stage) {
        this.stage = stage;
        return this;
    }

    /**
     * Get stage
     *
     * @return stage
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryPT getStage() {
        return stage;
    }

    public void setStage(CorDictionaryPT stage) {
        this.stage = stage;
    }

    public CrmOpportunityPT propability(Integer propability) {
        this.propability = propability;
        return this;
    }

    /**
     * Get propability
     *
     * @return propability
     **/
    @ApiModelProperty(value = "")
    public Integer getPropability() {
        return propability;
    }

    public void setPropability(Integer propability) {
        this.propability = propability;
    }

    public CrmOpportunityPT tasks(List<CrmTaskPT> tasks) {
        this.tasks = tasks;
        return this;
    }

    public CrmOpportunityPT addTasksItem(CrmTaskPT tasksItem) {
        this.tasks.add(tasksItem);
        return this;
    }

    /**
     * Get tasks
     *
     * @return tasks
     **/
    @ApiModelProperty(value = "")
    public List<CrmTaskPT> getTasks() {
        return tasks;
    }

    public void setTasks(List<CrmTaskPT> tasks) {
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
        CrmOpportunityPT crmOpportunityPT = (CrmOpportunityPT) o;
        return Objects.equals(this.id, crmOpportunityPT.id) &&
            Objects.equals(this.opportunityOwner, crmOpportunityPT.opportunityOwner) &&
            Objects.equals(this.name, crmOpportunityPT.name) &&
            Objects.equals(this.contactId, crmOpportunityPT.contactId) &&
            Objects.equals(this.lastTry, crmOpportunityPT.lastTry) &&
            Objects.equals(this.closeDate, crmOpportunityPT.closeDate) &&
            Objects.equals(this.stage, crmOpportunityPT.stage) &&
            Objects.equals(this.propability, crmOpportunityPT.propability) &&
            Objects.equals(this.tasks, crmOpportunityPT.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opportunityOwner, name, contactId, lastTry, closeDate, stage, propability, tasks);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CrmOpportunityPT {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    opportunityOwner: ").append(toIndentedString(opportunityOwner)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    contactId: ").append(toIndentedString(contactId)).append("\n");
        sb.append("    leadId: ").append(toIndentedString(leadId)).append("\n");
        sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
        sb.append("    lastTry: ").append(toIndentedString(lastTry)).append("\n");
        sb.append("    closeDate: ").append(toIndentedString(closeDate)).append("\n");
        sb.append("    stage: ").append(toIndentedString(stage)).append("\n");
        sb.append("    propability: ").append(toIndentedString(propability)).append("\n");
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

