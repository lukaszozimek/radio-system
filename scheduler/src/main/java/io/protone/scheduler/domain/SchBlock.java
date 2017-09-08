package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
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
public class SchBlock extends SchTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;



    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventTypeEnum eventType;

    @PodamExclude
    @ManyToOne
    private SchClock clock;



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

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchBlock name(String name) {
        this.name = name;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public SchBlock length(Long length) {
        this.length = length;
        return this;
    }

    public EventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public SchBlock eventType(EventTypeEnum eventType) {
        this.eventType = eventType;
        return this;
    }

    public SchClock getClock() {
        return clock;
    }

    public void setClock(SchClock clock) {
        this.clock = clock;
    }

    public SchBlock clock(SchClock clock) {
        this.clock = clock;
        return this;
    }

    public SchBlock getBlock() {
        return block;
    }

    public void setBlock(SchBlock block) {
        this.block = block;
    }

    public SchBlock block(SchBlock block) {
        this.block = block;
        return this;
    }

    public Set<SchBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<SchBlock> blocks) {
        this.blocks = blocks;
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

    public Set<SchEmission> getEmissions() {
        return emissions;
    }

    public void setEmissions(Set<SchEmission> emissions) {
        this.emissions = emissions;
    }

    public SchBlock emissions(Set<SchEmission> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchBlock addEmission(SchEmission emission) {
        this.emissions.add(emission);
        return this;
    }

    public SchBlock removeEmission(SchEmission emission) {
        this.emissions.remove(emission);
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchBlock network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchBlock channel(CorChannel channel) {
        this.channel = channel;
        return this;
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

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public SchBlock sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }
}
