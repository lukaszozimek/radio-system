package io.protone.scheduler.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A SchEventTemplate.
 */
@Entity
@Table(name = "sch_event_sch_event")
@AssociationOverrides({
        @AssociationOverride(name = "pk.parentTemplate",
                joinColumns = @JoinColumn(name = "parent_id")),
        @AssociationOverride(name = "pk.childTemplate",
                joinColumns = @JoinColumn(name = "child_id"))})
public class SchEventTemplateEvnetTemplate implements Serializable {

    @EmbeddedId
    private SchEventTemplateEvnetTemplateId pk = new SchEventTemplateEvnetTemplateId();

    @Column(name = "sequence")
    protected Long sequence;

    public SchEventTemplateEvnetTemplateId getPk() {
        return pk;
    }

    public void setPk(SchEventTemplateEvnetTemplateId pk) {
        this.pk = pk;
    }

    public SchEventTemplate getParent() {
        return pk.getParentTemplate();
    }

    public void setParent(SchEventTemplate parent) {
        this.pk.setParentTemplate(parent);
    }

    public SchEventTemplate getChild() {
        return pk.getChildTemplate();
    }

    public void setChild(SchEventTemplate parent) {
        this.pk.setChildTemplate(parent);
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public SchEventTemplateEvnetTemplate sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }
}
