package io.protone.scheduler.domain;

import javax.persistence.*;

/**
 * Created by lukaszozimek on 21/11/2017.
 */
@Entity
@Table(name = "sch_block_sch_block")
@AssociationOverrides({
        @AssociationOverride(name = "pk.parentTemplate",
                joinColumns = @JoinColumn(name = "parent_id")),
        @AssociationOverride(name = "pk.childTemplate",
                joinColumns = @JoinColumn(name = "child_id"))})
public class SchBlockSchBlock {

    @EmbeddedId
    private SchBlockSchBlockId pk = new SchBlockSchBlockId();


    public SchBlockSchBlockId getPk() {
        return pk;
    }

    public void setPk(SchBlockSchBlockId pk) {
        this.pk = pk;
    }

    public SchBlock getParent() {
        return pk.getParentTemplate();
    }

    public void setParent(SchBlock parent) {
        this.pk.setParentTemplate(parent);
    }

    public SchBlock getChild() {
        return pk.getChildTemplate();
    }

    public void setChild(SchBlock parent) {
        this.pk.setChildTemplate(parent);
    }

    public SchBlockSchBlock child(SchBlock parent) {
        this.pk.setChildTemplate(parent);
        return this;
    }

    public SchBlockSchBlock parent(SchBlock parent) {
        this.pk.setParentTemplate(parent);
        return this;
    }


    public Long getSequence() {
        return pk.getSequence();
    }

    public void setSequence(Long sequence) {
        pk.setSequence(sequence);
    }

    public SchBlockSchBlock sequence(Long sequence) {
        pk.setSequence(sequence);
        return this;
    }
}
