package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CrmTask.
 */
@Entity
@Table(name = "crm_task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "activity_date")
    private LocalDate activityDate;

    @Column(name = "activity_length")
    private Long activityLength;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private CorUser createdBy;

    @ManyToOne
    private CorUser assignedTo;

    @ManyToOne
    private CrmTaskStatus status;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    private CrmOpportunity opportunity;

    @ManyToOne
    private CrmContact contact;

    @ManyToOne
    private CrmAccount account;

    @ManyToOne
    private CrmLead lead;

    @ManyToOne
    private CrmTask tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public CrmTask subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public CrmTask activityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
        return this;
    }

    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }

    public Long getActivityLength() {
        return activityLength;
    }

    public CrmTask activityLength(Long activityLength) {
        this.activityLength = activityLength;
        return this;
    }

    public void setActivityLength(Long activityLength) {
        this.activityLength = activityLength;
    }

    public String getComment() {
        return comment;
    }

    public CrmTask comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CorUser getCreatedBy() {
        return createdBy;
    }

    public CrmTask createdBy(CorUser corUser) {
        this.createdBy = corUser;
        return this;
    }

    public void setCreatedBy(CorUser corUser) {
        this.createdBy = corUser;
    }

    public CorUser getAssignedTo() {
        return assignedTo;
    }

    public CrmTask assignedTo(CorUser corUser) {
        this.assignedTo = corUser;
        return this;
    }

    public void setAssignedTo(CorUser corUser) {
        this.assignedTo = corUser;
    }

    public CrmTaskStatus getStatus() {
        return status;
    }

    public CrmTask status(CrmTaskStatus crmTaskStatus) {
        this.status = crmTaskStatus;
        return this;
    }

    public void setStatus(CrmTaskStatus crmTaskStatus) {
        this.status = crmTaskStatus;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CrmTask network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CrmOpportunity getOpportunity() {
        return opportunity;
    }

    public CrmTask opportunity(CrmOpportunity crmOpportunity) {
        this.opportunity = crmOpportunity;
        return this;
    }

    public void setOpportunity(CrmOpportunity crmOpportunity) {
        this.opportunity = crmOpportunity;
    }

    public CrmContact getContact() {
        return contact;
    }

    public CrmTask contact(CrmContact crmContact) {
        this.contact = crmContact;
        return this;
    }

    public void setContact(CrmContact crmContact) {
        this.contact = crmContact;
    }

    public CrmAccount getAccount() {
        return account;
    }

    public CrmTask account(CrmAccount crmAccount) {
        this.account = crmAccount;
        return this;
    }

    public void setAccount(CrmAccount crmAccount) {
        this.account = crmAccount;
    }

    public CrmLead getLead() {
        return lead;
    }

    public CrmTask lead(CrmLead crmLead) {
        this.lead = crmLead;
        return this;
    }

    public void setLead(CrmLead crmLead) {
        this.lead = crmLead;
    }

    public CrmTask getTasks() {
        return tasks;
    }

    public CrmTask tasks(CrmTask crmTask) {
        this.tasks = crmTask;
        return this;
    }

    public void setTasks(CrmTask crmTask) {
        this.tasks = crmTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrmTask crmTask = (CrmTask) o;
        if (crmTask.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, crmTask.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmTask{" +
            "id=" + id +
            ", subject='" + subject + "'" +
            ", activityDate='" + activityDate + "'" +
            ", activityLength='" + activityLength + "'" +
            ", comment='" + comment + "'" +
            '}';
    }


}
