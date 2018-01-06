package io.protone.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A CrmTask.
 */
@Entity
@Table(name = "crm_task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmTask extends AbstractAuditingEntity implements Serializable {

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
    @PodamExclude
    private CorUser assignedTo;

    @ManyToOne
    @PodamExclude
    private CorDictionary status;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @ManyToOne
    @PodamExclude
    private CrmOpportunity opportunity;

    @ManyToOne
    @PodamExclude
    private CrmContact contact;

    @ManyToOne
    @PodamExclude
    private CrmAccount account;

    @ManyToOne
    @PodamExclude
    private CrmLead lead;

    @ManyToOne
    @PodamExclude
    private CrmTask tasks;

    @OneToMany(mappedBy = "taskComment")
    @JsonIgnore
    @PodamExclude
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CrmTaskComment> comments = new HashSet<>();

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

    public CorDictionary getStatus() {
        return status;
    }

    public CrmTask status(CorDictionary corDictionary) {
        this.status = corDictionary;
        return this;
    }

    public void setStatus(CorDictionary corDictionary) {
        this.status = corDictionary;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public CrmTask channel(CorChannel corNetwork) {
        this.channel = corNetwork;
        return this;
    }

    public void setChannel(CorChannel corNetwork) {
        this.channel = corNetwork;
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

    public Set<CrmTaskComment> getComments() {
        return comments;
    }

    public CrmTask comments(Set<CrmTaskComment> crmTaskComments) {
        this.comments = crmTaskComments;
        return this;
    }

    public CrmTask addComments(CrmTaskComment crmTaskComment) {
        this.comments.add(crmTaskComment);
        crmTaskComment.setTaskComment(this);
        return this;
    }

    public CrmTask removeComments(CrmTaskComment crmTaskComment) {
        this.comments.remove(crmTaskComment);
        crmTaskComment.setTaskComment(null);
        return this;
    }

    public void setComments(Set<CrmTaskComment> crmTaskComments) {
        this.comments = crmTaskComments;
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
