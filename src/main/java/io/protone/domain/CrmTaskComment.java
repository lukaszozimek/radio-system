package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CrmTaskComment.
 */
@Entity
@Table(name = "crm_task_comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmTaskComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private CrmTask taskComment;

    @ManyToOne
    private CorUser createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public CrmTaskComment comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CrmTask getTaskComment() {
        return taskComment;
    }

    public CrmTaskComment taskComment(CrmTask crmTask) {
        this.taskComment = crmTask;
        return this;
    }

    public void setTaskComment(CrmTask crmTask) {
        this.taskComment = crmTask;
    }

    public CorUser getCreatedBy() {
        return createdBy;
    }

    public CrmTaskComment createdBy(CorUser corUser) {
        this.createdBy = corUser;
        return this;
    }

    public void setCreatedBy(CorUser corUser) {
        this.createdBy = corUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrmTaskComment crmTaskComment = (CrmTaskComment) o;
        if (crmTaskComment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, crmTaskComment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmTaskComment{" +
            "id=" + id +
            ", comment='" + comment + "'" +
            '}';
    }
}
