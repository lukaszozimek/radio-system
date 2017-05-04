package io.protone.custom.service.dto;

import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.cor.thin.CoreUserThinDTO;
import io.swagger.annotations.ApiModelProperty;
import uk.co.jemos.podam.common.PodamExclude;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CrmTaskDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CrmTaskDTO implements Serializable {
    private Long id = null;

    private CorDictionaryDTO status = null;

    private CoreUserThinDTO createdBy = null;

    private CoreUserThinDTO assignedTo = null;

    @NotNull
    private String subject = null;

    private LocalDate activityDate = null;

    private Long activityLength = null;

    private String comment = null;

    @PodamExclude
    private List<CrmTaskDTO> relatedTasks = new ArrayList<CrmTaskDTO>();

    public CrmTaskDTO id(Long id) {
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

    public CrmTaskDTO createdBy(CoreUserThinDTO createdBy) {
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

    public CrmTaskDTO assignedTo(CoreUserThinDTO assignedTo) {
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

    public CrmTaskDTO subject(String subject) {
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

    public CrmTaskDTO crmTaskStatus(CorDictionaryDTO crmTaskStatus) {
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

    public CrmTaskDTO activityDate(LocalDate activityDate) {
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

    public CrmTaskDTO activityLenght(Long activityLenght) {
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

    public CrmTaskDTO comment(String comment) {
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

    public CrmTaskDTO relatedTasks(List<CrmTaskDTO> relatedTasks) {
        this.relatedTasks = relatedTasks;
        return this;
    }

    public CrmTaskDTO addRelatedTasksItem(CrmTaskDTO relatedTasksItem) {
        this.relatedTasks.add(relatedTasksItem);
        return this;
    }

    /**
     * Get relatedTasks
     *
     * @return relatedTasks
     **/
    @ApiModelProperty(value = "")
    public List<CrmTaskDTO> getRelatedTasks() {
        return relatedTasks;
    }

    public void setRelatedTasks(List<CrmTaskDTO> relatedTasks) {
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
        CrmTaskDTO crmTaskDTO = (CrmTaskDTO) o;
        return Objects.equals(this.id, crmTaskDTO.id) &&
            Objects.equals(this.createdBy, crmTaskDTO.createdBy) &&
            Objects.equals(this.assignedTo, crmTaskDTO.assignedTo) &&
            Objects.equals(this.subject, crmTaskDTO.subject) &&
            Objects.equals(this.activityDate, crmTaskDTO.activityDate) &&
            Objects.equals(this.activityLength, crmTaskDTO.activityLength) &&
            Objects.equals(this.comment, crmTaskDTO.comment) &&
            Objects.equals(this.relatedTasks, crmTaskDTO.relatedTasks) &&
            Objects.equals(this.status, crmTaskDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdBy, assignedTo, subject, activityDate, activityLength, comment, relatedTasks, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CrmTaskDTO {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
        sb.append("    assignedTo: ").append(toIndentedString(assignedTo)).append("\n");
        sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
        sb.append("    activityDate: ").append(toIndentedString(activityDate)).append("\n");
        sb.append("    activityLength: ").append(toIndentedString(activityLength)).append("\n");
        sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
        sb.append("    relatedTasks: ").append(toIndentedString(relatedTasks)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");

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

