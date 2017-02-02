package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CRMTaskComment.
 */
@Entity
@Table(name = "crm_task_comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CRMTaskComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private CRMTask cRMTask;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public CRMTaskComment comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CRMTask getCRMTask() {
        return cRMTask;
    }

    public CRMTaskComment cRMTask(CRMTask cRMTask) {
        this.cRMTask = cRMTask;
        return this;
    }

    public void setCRMTask(CRMTask cRMTask) {
        this.cRMTask = cRMTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CRMTaskComment cRMTaskComment = (CRMTaskComment) o;
        if (cRMTaskComment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cRMTaskComment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CRMTaskComment{" +
            "id=" + id +
            ", comment='" + comment + "'" +
            '}';
    }
}
