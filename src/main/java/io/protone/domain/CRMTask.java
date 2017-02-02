package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CRMTask.
 */
@Entity
@Table(name = "crm_task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CRMTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private CRMLead cRMLead;

    @OneToOne
    @JoinColumn(unique = true)
    private CORUser createdBy;

    @OneToOne
    @JoinColumn(unique = true)
    private CORUser assignedTo;

    @OneToOne
    @JoinColumn(unique = true)
    private CFGTaskStatus status;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @ManyToOne
    private CRMTask cRMTask;

    @OneToMany(mappedBy = "cRMTask")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CRMTask> tasks = new HashSet<>();

    @OneToMany(mappedBy = "cRMTask")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CRMTaskComment> comments = new HashSet<>();

    @ManyToOne
    private CRMContact cRMContact;

    @ManyToOne
    private CRMAccount cRMAccount;
    @ManyToOne
    private CRMOpportunity crmOpportunity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public CRMTask subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public CRMTask activityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
        return this;
    }

    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }

    public Long getActivityLength() {
        return activityLength;
    }

    public CRMTask activityLength(Long activityLength) {
        this.activityLength = activityLength;
        return this;
    }

    public void setActivityLength(Long activityLength) {
        this.activityLength = activityLength;
    }

    public String getComment() {
        return comment;
    }

    public CRMTask comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CRMLead getCRMLead() {
        return cRMLead;
    }

    public CRMTask cRMLead(CRMLead cRMLead) {
        this.cRMLead = cRMLead;
        return this;
    }

    public void setCRMLead(CRMLead cRMLead) {
        this.cRMLead = cRMLead;
    }

    public CORUser getCreatedBy() {
        return createdBy;
    }

    public CRMTask createdBy(CORUser cORUser) {
        this.createdBy = cORUser;
        return this;
    }

    public void setCreatedBy(CORUser cORUser) {
        this.createdBy = cORUser;
    }

    public CORUser getAssignedTo() {
        return assignedTo;
    }

    public CRMTask assignedTo(CORUser cORUser) {
        this.assignedTo = cORUser;
        return this;
    }

    public void setAssignedTo(CORUser cORUser) {
        this.assignedTo = cORUser;
    }

    public CFGTaskStatus getStatus() {
        return status;
    }

    public CRMTask status(CFGTaskStatus cFGTaskStatus) {
        this.status = cFGTaskStatus;
        return this;
    }

    public void setStatus(CFGTaskStatus cFGTaskStatus) {
        this.status = cFGTaskStatus;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CRMTask network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public CRMTask getCRMTask() {
        return cRMTask;
    }

    public CRMTask cRMTask(CRMTask cRMTask) {
        this.cRMTask = cRMTask;
        return this;
    }

    public void setCRMTask(CRMTask cRMTask) {
        this.cRMTask = cRMTask;
    }

    public Set<CRMTask> getTasks() {
        return tasks;
    }

    public CRMTask tasks(Set<CRMTask> cRMTasks) {
        this.tasks = cRMTasks;
        return this;
    }

    public CRMTask addTasks(CRMTask cRMTask) {
        tasks.add(cRMTask);
        cRMTask.setCRMTask(this);
        return this;
    }

    public CRMTask removeTasks(CRMTask cRMTask) {
        tasks.remove(cRMTask);
        cRMTask.setCRMTask(null);
        return this;
    }

    public void setTasks(Set<CRMTask> cRMTasks) {
        this.tasks = cRMTasks;
    }

    public Set<CRMTaskComment> getComments() {
        return comments;
    }

    public CRMTask comments(Set<CRMTaskComment> cRMTaskComments) {
        this.comments = cRMTaskComments;
        return this;
    }

    public CRMTask addComment(CRMTaskComment cRMTaskComment) {
        comments.add(cRMTaskComment);
        cRMTaskComment.setCRMTask(this);
        return this;
    }

    public CRMTask removeComment(CRMTaskComment cRMTaskComment) {
        comments.remove(cRMTaskComment);
        cRMTaskComment.setCRMTask(null);
        return this;
    }

    public void setComments(Set<CRMTaskComment> cRMTaskComments) {
        this.comments = cRMTaskComments;
    }

    public CRMContact getCRMContact() {
        return cRMContact;
    }

    public CRMTask cRMContact(CRMContact cRMContact) {
        this.cRMContact = cRMContact;
        return this;
    }

    public void setCRMContact(CRMContact cRMContact) {
        this.cRMContact = cRMContact;
    }

    public CRMAccount getCRMAccount() {
        return cRMAccount;
    }

    public CRMTask cRMAccount(CRMAccount cRMAccount) {
        this.cRMAccount = cRMAccount;
        return this;
    }

    public void setCRMAccount(CRMAccount cRMAccount) {
        this.cRMAccount = cRMAccount;
    }
    public CRMOpportunity getCRMOpportunity() {
        return crmOpportunity;
    }

    public CRMTask cRMOpportunity(CRMOpportunity crmOpportunity) {
        this.crmOpportunity = crmOpportunity;
        return this;
    }

    public void setCRMOpportunity(CRMOpportunity crmOpportunity) {
        this.crmOpportunity = crmOpportunity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CRMTask cRMTask = (CRMTask) o;
        if (cRMTask.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cRMTask.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CRMTask{" +
            "id=" + id +
            ", subject='" + subject + "'" +
            ", activityDate='" + activityDate + "'" +
            ", activityLength='" + activityLength + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
