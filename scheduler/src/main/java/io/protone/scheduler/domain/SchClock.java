package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Clock.
 */
@Entity
@Table(name = "sch_clock", uniqueConstraints = @UniqueConstraint(columnNames = {"channel_id", "short_name", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchClock extends SchClockBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @PodamExclude
    @ManyToOne
    private SchSchedule schSchedule;

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

    public SchClock shortName(String shortName) {
        super.setShortName(shortName);
        return this;
    }

    public SchClock name(String name) {
        super.setName(name);
        return this;
    }

    public SchSchedule getSchSchedule() {
        return schSchedule;
    }

    public void setSchSchedule(SchSchedule schSchedule) {
        this.schSchedule = schSchedule;
    }

    public SchClock grid(SchSchedule schSchedule) {
        this.schSchedule = schSchedule;
        return this;
    }


    public Set<SchBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<SchBlock> blocks) {
        this.blocks = blocks;
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

    public Set<SchEmission> getEmissions() {
        return emissions;
    }

    public void setEmissions(Set<SchEmission> emissions) {
        this.emissions = emissions;
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


    public SchClock network(CorNetwork network) {
        super.setNetwork(network);
        return this;
    }


    public SchClock channel(CorChannel channel) {
        super.setChannel(channel);
        return this;
    }


    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public SchClock sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public SchClock length(Long length) {
        this.length = length;
        return this;
    }

    public SchClock startTime(LocalDateTime startTime) {
        super.setStartTime(startTime);
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
        return "SchClock{" +
                "id=" + id +
                ", schSchedule=" + schSchedule +
                ", sequence=" + sequence +
                ", length=" + length +
                ", blocks=" + blocks +
                ", emissions=" + emissions +

                '}';
    }

    public SchClock schedule(SchSchedule finalEntity) {
        this.schSchedule = finalEntity;
        return this;
    }
}
