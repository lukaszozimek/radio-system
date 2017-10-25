package io.protone.scheduler.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by lukaszozimek on 08.09.2017.
 */
@Entity
@Table(name = "sch_grid_sch_clock_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchGridClockTemplate extends SchBaseEntity {


    @Column(name = "sequence")
    protected Long sequence;

    @ManyToOne
    private SchGrid schGrid;

    @ManyToOne
    private SchClockTemplate schClockTemplate;


    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }


    public SchGrid getSchGrid() {
        return schGrid;
    }

    public void setSchGrid(SchGrid schGrid) {
        this.schGrid = schGrid;
    }

    public SchGridClockTemplate grid(SchGrid schGrid) {
        this.schGrid = schGrid;
        return this;
    }

    public SchGridClockTemplate schClockConfiguration(SchClockTemplate schClockTemplate) {
        this.schClockTemplate = schClockTemplate;
        return this;
    }

    public SchClockTemplate getSchClockTemplate() {
        return schClockTemplate;
    }

    public void setSchClockTemplate(SchClockTemplate schClockTemplate) {
        this.schClockTemplate = schClockTemplate;
    }

    public SchGridClockTemplate sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }
}
