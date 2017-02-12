package io.protone.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CrmTask entity.
 */
public class CrmTaskDTO implements Serializable {

    private Long id;

    private String subject;

    private LocalDate activityDate;

    private Long activityLength;

    private String comment;

    private Long createdById;

    private Long assignedToId;

    private Long statusId;

    private Long networkId;

    private Long opportunityId;

    private Long contactId;

    private Long accountId;

    private Long leadId;

    private Long tasksId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public LocalDate getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }
    public Long getActivityLength() {
        return activityLength;
    }

    public void setActivityLength(Long activityLength) {
        this.activityLength = activityLength;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long corUserId) {
        this.createdById = corUserId;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long corUserId) {
        this.assignedToId = corUserId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long crmTaskStatusId) {
        this.statusId = crmTaskStatusId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    public Long getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Long crmOpportunityId) {
        this.opportunityId = crmOpportunityId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long crmContactId) {
        this.contactId = crmContactId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long crmAccountId) {
        this.accountId = crmAccountId;
    }

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long crmLeadId) {
        this.leadId = crmLeadId;
    }

    public Long getTasksId() {
        return tasksId;
    }

    public void setTasksId(Long crmTaskId) {
        this.tasksId = crmTaskId;
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

        if ( ! Objects.equals(id, crmTaskDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmTaskDTO{" +
            "id=" + id +
            ", subject='" + subject + "'" +
            ", activityDate='" + activityDate + "'" +
            ", activityLength='" + activityLength + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
