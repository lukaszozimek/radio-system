package io.protone.scheduler.domain;

import com.google.common.base.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * A SchBlock.
 */
@Embeddable
public class SchBlockSchBlockId implements Serializable {


    @Column(name = "sequence")
    protected Long sequence;

    @ManyToOne
    private SchBlock parentTemplate;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private SchBlock childTemplate;


    public SchBlock getParentTemplate() {
        return parentTemplate;
    }

    public void setParentTemplate(SchBlock parentTemplate) {
        this.parentTemplate = parentTemplate;
    }

    public SchBlock getChildTemplate() {
        return childTemplate;
    }

    public void setChildTemplate(SchBlock childTemplate) {
        this.childTemplate = childTemplate;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "SchBlockSchBlockId{" +
                "parentTemplate=" + parentTemplate +
                ", childTemplate=" + childTemplate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchBlockSchBlockId)) return false;
        SchBlockSchBlockId that = (SchBlockSchBlockId) o;
        return Objects.equal(getSequence(), that.getSequence()) &&
                Objects.equal(getParentTemplate(), that.getParentTemplate()) &&
                Objects.equal(getChildTemplate(), that.getChildTemplate());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSequence(), getParentTemplate(), getChildTemplate());
    }
}
