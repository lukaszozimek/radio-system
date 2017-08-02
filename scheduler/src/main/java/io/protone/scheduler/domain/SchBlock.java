package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Block.
 */
@Entity
@Table(name = "sch_block")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchBlock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "length")
    private Long length;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventTypeEnum eventType;

    @PodamExclude
    @ManyToOne
    private SchClock clock;

    @PodamExclude
    @OneToOne
    @JoinColumn(unique = true)
    private SchQueueParams queueParams;

    @PodamExclude
    @OneToOne
    @JoinColumn(unique = true)
    private SchTimeParams timeParams;

    @PodamExclude
    @ManyToOne
    private SchBlock block;

    @PodamExclude
    @OneToMany(mappedBy = "block")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchBlock> blocks = new HashSet<>();

    @PodamExclude
    @OneToMany(mappedBy = "block")
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

    public SchBlock name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLength() {
        return length;
    }

    public SchBlock length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public EventTypeEnum getEventType() {
        return eventType;
    }

    public SchBlock eventType(EventTypeEnum eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public SchClock getClock() {
        return clock;
    }

    public SchBlock clock(SchClock clock) {
        this.clock = clock;
        return this;
    }

    public void setClock(SchClock clock) {
        this.clock = clock;
    }

    public SchQueueParams getQueueParams() {
        return queueParams;
    }

    public SchBlock queueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
        return this;
    }

    public void setQueueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
    }

    public SchTimeParams getTimeParams() {
        return timeParams;
    }

    public SchBlock timeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
        return this;
    }

    public void setTimeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
    }

    public SchBlock getBlock() {
        return block;
    }

    public SchBlock block(SchBlock block) {
        this.block = block;
        return this;
    }

    public void setBlock(SchBlock block) {
        this.block = block;
    }

    public Set<SchBlock> getBlocks() {
        return blocks;
    }

    public SchBlock blocks(Set<SchBlock> blocks) {
        this.blocks = blocks;
        return this;
    }

    public SchBlock addBlock(SchBlock block) {
        this.blocks.add(block);
        return this;
    }

    public SchBlock removeBlock(SchBlock block) {
        this.blocks.remove(block);
        return this;
    }

    public void setBlocks(Set<SchBlock> blocks) {
        this.blocks = blocks;
    }

    public Set<SchEmission> getEmissions() {
        return emissions;
    }

    public SchBlock emissions(Set<SchEmission> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchBlock addEmission(SchEmission emission) {
        this.emissions.add(emission);
        emission.setBlock(this);
        return this;
    }

    public SchBlock removeEmission(SchEmission emission) {
        this.emissions.remove(emission);
        emission.setBlock(null);
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
        SchBlock block = (SchBlock) o;
        if (block.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), block.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Block{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", length='" + getLength() + "'" +
            ", eventType='" + getEventType() + "'" +
            "}";
    }
}
