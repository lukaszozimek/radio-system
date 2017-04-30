package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.web.rest.dto.thin.CoreUserThinDTO;
import io.swagger.annotations.ApiModelProperty;
import uk.co.jemos.podam.common.PodamExclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CrmTaskPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CrmTaskPT {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("status")
    private CorDictionaryPT crmTaskStatus = null;

    @JsonProperty("createdBy")
    private CoreUserThinDTO createdBy = null;

    @JsonProperty("assignedTo")
    private CoreUserThinDTO assignedTo = null;

    @JsonProperty("subject")
    private String subject = null;

    @JsonProperty("activityDate")
    private LocalDate activityDate = null;

    @JsonProperty("activityLenght")
    private Long activityLenght = null;

    @JsonProperty("comment")
    private String comment = null;

    @JsonProperty("relatedTasks")
    @PodamExclude
    private List<CrmTaskPT> relatedTasks = new ArrayList<CrmTaskPT>();

    public CrmTaskPT id(Long id) {
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

    public CrmTaskPT createdBy(CoreUserThinDTO createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Get createdBy
     *
     * @return createdBy
     **/
    @ApiModelProperty(value = "")
    public CoreUserThinDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CoreUserThinDTO createdBy) {
        this.createdBy = createdBy;
    }

    public CrmTaskPT assignedTo(CoreUserThinDTO assignedTo) {
        this.assignedTo = assignedTo;
        return this;
    }

    /**
     * Get assignedTo
     *
     * @return assignedTo
     **/
    @ApiModelProperty(value = "")
    public CoreUserThinDTO getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(CoreUserThinDTO assignedTo) {
        this.assignedTo = assignedTo;
    }

    public CrmTaskPT subject(String subject) {
        this.subject = subject;
        return this;
    }

    @ApiModelProperty(value = "")
    public CorDictionaryPT getCrmTaskStatus() {
        return crmTaskStatus;
    }

    public void setCrmTaskStatus(CorDictionaryPT crmTaskStatus) {
        this.crmTaskStatus = crmTaskStatus;
    }

    public CrmTaskPT crmTaskStatus(CorDictionaryPT crmTaskStatus) {
        this.crmTaskStatus = crmTaskStatus;
        return this;
    }

    /**
     * Get subject
     *
     * @return subject
     **/
    @ApiModelProperty(value = "")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public CrmTaskPT activityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
        return this;
    }

    /**
     * Get activityDate
     *
     * @return activityDate
     **/
    @ApiModelProperty(value = "")
    public LocalDate getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }

    public CrmTaskPT activityLenght(Long activityLenght) {
        this.activityLenght = activityLenght;
        return this;
    }

    /**
     * Get activityLenght
     *
     * @return activityLenght
     **/
    @ApiModelProperty(value = "")
    public Long getActivityLenght() {
        return activityLenght;
    }

    public void setActivityLenght(Long activityLenght) {
        this.activityLenght = activityLenght;
    }

    public CrmTaskPT comment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Get comment
     *
     * @return comment
     **/
    @ApiModelProperty(value = "")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CrmTaskPT relatedTasks(List<CrmTaskPT> relatedTasks) {
        this.relatedTasks = relatedTasks;
        return this;
    }

    public CrmTaskPT addRelatedTasksItem(CrmTaskPT relatedTasksItem) {
        this.relatedTasks.add(relatedTasksItem);
        return this;
    }

    /**
     * Get relatedTasks
     *
     * @return relatedTasks
     **/
    @ApiModelProperty(value = "")
    public List<CrmTaskPT> getRelatedTasks() {
        return relatedTasks;
    }

    public void setRelatedTasks(List<CrmTaskPT> relatedTasks) {
        this.relatedTasks = relatedTasks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrmTaskPT crmTaskPT = (CrmTaskPT) o;
        return Objects.equals(this.id, crmTaskPT.id) &&
            Objects.equals(this.createdBy, crmTaskPT.createdBy) &&
            Objects.equals(this.assignedTo, crmTaskPT.assignedTo) &&
            Objects.equals(this.subject, crmTaskPT.subject) &&
            Objects.equals(this.activityDate, crmTaskPT.activityDate) &&
            Objects.equals(this.activityLenght, crmTaskPT.activityLenght) &&
            Objects.equals(this.comment, crmTaskPT.comment) &&
            Objects.equals(this.relatedTasks, crmTaskPT.relatedTasks) &&
            Objects.equals(this.crmTaskStatus, crmTaskPT.crmTaskStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdBy, assignedTo, subject, activityDate, activityLenght, comment, relatedTasks, crmTaskStatus);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CrmTaskPT {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
        sb.append("    assignedTo: ").append(toIndentedString(assignedTo)).append("\n");
        sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
        sb.append("    activityDate: ").append(toIndentedString(activityDate)).append("\n");
        sb.append("    activityLenght: ").append(toIndentedString(activityLenght)).append("\n");
        sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
        sb.append("    relatedTasks: ").append(toIndentedString(relatedTasks)).append("\n");
        sb.append("    status: ").append(toIndentedString(crmTaskStatus)).append("\n");

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

