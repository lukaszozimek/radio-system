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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.protone.scheduler.domain.SchDiscriminators.BLOCK;

/**
 * A Block.
 */
@Entity
@Table(name = "sch_block")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(BLOCK)
public class SchBlock extends SchTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    protected String name;


    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventTypeEnum eventType;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.parentTemplate")
    @PodamExclude
    protected List<SchBlockSchBlock> blocks = new ArrayList<>();


    @PodamExclude
    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<SchEmission> emissions = new ArrayList<>();

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


    public List<SchBlockSchBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<SchBlockSchBlock> blocks) {
        this.blocks = blocks;
    }

    public SchBlock blocks(List<SchBlockSchBlock> blocks) {
        this.blocks = blocks;
        return this;
    }

    public SchBlock addBlock(SchBlockSchBlock block) {
        this.blocks.add(block);
        return this;
    }

    public SchBlock removeBlock(SchBlockSchBlock block) {
        this.blocks.remove(block);
        return this;
    }

    public List<SchEmission> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<SchEmission> emissions) {
        this.emissions = emissions;
    }

    public SchBlock emissions(List<SchEmission> emissions) {
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

    public SchBlock startTime(LocalDateTime startTime) {
        super.startTime(startTime);
        return this;
    }

    public SchBlock id(Long id) {
        this.id = id;
        return this;
    }


}
