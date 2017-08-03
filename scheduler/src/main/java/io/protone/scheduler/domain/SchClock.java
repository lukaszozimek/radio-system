package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Clock.
 */
@Entity
@Table(name = "sch_clock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchClock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @PodamExclude
    @ManyToOne
    private SchGrid grid;

    @PodamExclude
    @OneToOne
    @JoinColumn(unique = true)
    private SchQueueParams queueParams;

    @PodamExclude
    @OneToOne
    @JoinColumn(unique = true)
    private SchTimeParams timeParams;

    @PodamExclude
    @OneToMany(mappedBy = "clock")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchBlock> blocks = new HashSet<>();

    @PodamExclude
    @OneToMany(mappedBy = "clock")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEmission> emissions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SchClock name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchGrid getGrid() {
        return grid;
    }

    public SchClock grid(SchGrid grid) {
        this.grid = grid;
        return this;
    }

    public void setGrid(SchGrid grid) {
        this.grid = grid;
    }

    public SchQueueParams getQueueParams() {
        return queueParams;
    }

    public SchClock queueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
        return this;
    }

    public void setQueueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
    }

    public SchTimeParams getTimeParams() {
        return timeParams;
    }

    public SchClock timeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
        return this;
    }

    public void setTimeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
    }

    public Set<SchBlock> getBlocks() {
        return blocks;
    }

    public SchClock blocks(Set<SchBlock> blocks) {
        this.blocks = blocks;
        return this;
    }

    public SchClock addBlock(SchBlock block) {
        this.blocks.add(block);
        return this;
    }

    public SchClock removeBlock(SchBlock block) {
        this.blocks.remove(block);
        return this;
    }

    public void setBlocks(Set<SchBlock> blocks) {
        this.blocks = blocks;
    }

    public Set<SchEmission> getEmissions() {
        return emissions;
    }

    public SchClock emissions(Set<SchEmission> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchClock addEmission(SchEmission emission) {
        this.emissions.add(emission);
        return this;
    }

    public SchClock removeEmission(SchEmission emission) {
        this.emissions.remove(emission);
        return this;
    }

    public void setEmissions(Set<SchEmission> emissions) {
        this.emissions = emissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchClock clock = (SchClock) o;
        if (clock.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Clock{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
