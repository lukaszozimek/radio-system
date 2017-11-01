package io.protone.scheduler.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A Clock.
 */
@Entity
@Table(name = "sch_template_join_tabel")
public class SchTemplateJoin implements Serializable {

    private Long sequence;

    @ManyToOne(cascade = CascadeType.ALL)
    private SchEventTemplate parentEvent;

    @ManyToOne(cascade = CascadeType.ALL)
    private SchEventTemplate childEvent;

    @ManyToOne(cascade = CascadeType.ALL)
    private SchClockTemplate schClockTemplate;

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public SchEventTemplate getParent() {
        return parentEvent;
    }


    public void setParent(SchEventTemplate parentEvent) {
        this.parentEvent = parentEvent;
    }

    public SchEventTemplate getChildEvent() {
        return childEvent;
    }

    public void setChildEvent(SchEventTemplate childEvent) {
        this.childEvent = childEvent;
    }

    public SchClockTemplate getSchClockTemplate() {
        return schClockTemplate;
    }

    public void setSchClockTemplate(SchClockTemplate schClockTemplate) {
        this.schClockTemplate = schClockTemplate;
    }
}
