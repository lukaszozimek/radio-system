package io.protone.crm.api.dto.thin;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * CrmTaskDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CrmTaskThinDTO implements Serializable {
    private Long id = null;

    private CorDictionaryDTO status = null;

    private CoreUserThinDTO createdBy;

    private ZonedDateTime createdDate;

    private CoreUserThinDTO lastModifiedBy;

    private ZonedDateTime lastModifiedDate;
    private CoreUserThinDTO assignedTo = null;

    @NotNull
    private String subject = null;

    private LocalDate activityDate = null;

    private Long activityLength = null;

    private String comment = null;

    public CrmTaskThinDTO id(Long id) {
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

    public CrmTaskThinDTO createdBy(CoreUserThinDTO createdBy) {
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

    public CrmTaskThinDTO assignedTo(CoreUserThinDTO assignedTo) {
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

    public CrmTaskThinDTO subject(String subject) {
        this.subject = subject;
        return this;
    }

    @ApiModelProperty(value = "")
    public CorDictionaryDTO getStatus() {
        return status;
    }

    public void setStatus(CorDictionaryDTO status) {
        this.status = status;
    }

    public CrmTaskThinDTO crmTaskStatus(CorDictionaryDTO crmTaskStatus) {
        this.status = crmTaskStatus;
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

    public CrmTaskThinDTO activityDate(LocalDate activityDate) {
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

    public CrmTaskThinDTO activityLenght(Long activityLenght) {
        this.activityLength = activityLenght;
        return this;
    }

    /**
     * Get activityLength
     *
     * @return activityLength
     **/
    @ApiModelProperty(value = "")
    public Long getActivityLength() {
        return activityLength;
    }

    public void setActivityLength(Long activityLength) {
        this.activityLength = activityLength;
    }

    public CrmTaskThinDTO comment(String comment) {
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

        CrmTaskThinDTO that = (CrmTaskThinDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastModifiedBy != null ? !lastModifiedBy.equals(that.lastModifiedBy) : that.lastModifiedBy != null)
            return false;
        if (lastModifiedDate != null ? !lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate != null)
            return false;
        if (assignedTo != null ? !assignedTo.equals(that.assignedTo) : that.assignedTo != null) return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
        if (activityDate != null ? !activityDate.equals(that.activityDate) : that.activityDate != null) return false;
        if (activityLength != null ? !activityLength.equals(that.activityLength) : that.activityLength != null)
            return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        result = 31 * result + (assignedTo != null ? assignedTo.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (activityDate != null ? activityDate.hashCode() : 0);
        result = 31 * result + (activityLength != null ? activityLength.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CrmTaskThinDTO{" +
                "id=" + id +
                ", status=" + status +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                ", assignedTo=" + assignedTo +
                ", subject='" + subject + '\'' +
                ", activityDate=" + activityDate +
                ", activityLength=" + activityLength +
                ", comment='" + comment + '\'' +
                '}';
    }
}

