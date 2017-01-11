package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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
    private CORNetwork network;

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
