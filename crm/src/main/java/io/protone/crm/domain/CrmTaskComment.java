package io.protone.crm.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CrmTaskComment.
 */
@Entity
@Table(name = "crm_task_comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmTaskComment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @ManyToOne
    @PodamExclude
    private CrmTask taskComment;


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

    public CorChannel getChannel() {
        return channel;
    }

    public CrmTaskComment channel(CorChannel corNetwork) {
        this.channel = corNetwork;
        return this;
    }

    public void setChannel(CorChannel corNetwork) {
        this.channel = corNetwork;
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
