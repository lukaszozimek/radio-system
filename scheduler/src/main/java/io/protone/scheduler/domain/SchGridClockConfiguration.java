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
@Table(name = "sch_grid_sch_clock_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchGridClockConfiguration extends SchBaseEntity {


    @Column(name = "sequence")
    protected Long sequence;

    @ManyToOne
    private SchGrid schGrid;

    @ManyToOne
    private SchClockConfiguration schClockConfiguration;


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

    public SchGridClockConfiguration grid(SchGrid schGrid) {
        this.schGrid = schGrid;
        return this;
    }

    public SchGridClockConfiguration schClockConfiguration(SchClockConfiguration schClockConfiguration) {
        this.schClockConfiguration = schClockConfiguration;
        return this;
    }

    public SchClockConfiguration getSchClockConfiguration() {
        return schClockConfiguration;
    }

    public void setSchClockConfiguration(SchClockConfiguration schClockConfiguration) {
        this.schClockConfiguration = schClockConfiguration;
    }

    public SchGridClockConfiguration sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }
}
