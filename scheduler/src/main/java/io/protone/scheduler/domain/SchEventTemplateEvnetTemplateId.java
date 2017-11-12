package io.protone.scheduler.domain;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * A SchEventTemplate.
 */
@Embeddable
public class SchEventTemplateEvnetTemplateId implements Serializable {


    @Column(name = "sequence")
    protected Long sequence;

    @ManyToOne
    private SchEventTemplate parentTemplate;

    @ManyToOne
    private SchEventTemplate childTemplate;


    public SchEventTemplate getParentTemplate() {
        return parentTemplate;
    }

    public void setParentTemplate(SchEventTemplate parentTemplate) {
        this.parentTemplate = parentTemplate;
    }

    public SchEventTemplate getChildTemplate() {
        return childTemplate;
    }

    public void setChildTemplate(SchEventTemplate childTemplate) {
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
        return "SchEventTemplateEvnetTemplateId{" +
                "parentTemplate=" + parentTemplate +
                ", childTemplate=" + childTemplate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchEventTemplateEvnetTemplateId)) return false;
        SchEventTemplateEvnetTemplateId that = (SchEventTemplateEvnetTemplateId) o;
        return Objects.equal(getSequence(), that.getSequence()) &&
                Objects.equal(getParentTemplate(), that.getParentTemplate()) &&
                Objects.equal(getChildTemplate(), that.getChildTemplate());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSequence(), getParentTemplate(), getChildTemplate());
    }
}
