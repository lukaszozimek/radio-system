package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
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
@Table(name = "sch_clock", uniqueConstraints = @UniqueConstraint(columnNames = {"channel_id", "short_name", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchClock extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name", unique = true, nullable = false)
    private String shortName;


    @PodamExclude
    @ManyToOne
    private SchSchedule schSchedule;

    @Column(name = "sequence")
    private Long sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @PodamExclude
    private CorDictionary clockCategory;

    @Embedded
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public SchClock shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchClock name(String name) {
        this.name = name;
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

    public SchTimeParams getTimeParams() {
        return timeParams;
    }

    public void setTimeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
    }

    public SchClock timeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
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

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchClock network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchClock channel(CorChannel channel) {
        this.channel = channel;
        return this;
    }

    public CorDictionary getClockCategory() {
        return clockCategory;
    }

    public void setClockCategory(CorDictionary clockCategory) {
        this.clockCategory = clockCategory;
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
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", schSchedule=" + schSchedule +
                ", clockCategory=" + clockCategory +
                ", timeParams=" + timeParams +
                ", blocks=" + blocks +
                ", emissions=" + emissions +
                ", network=" + network +
                ", channel=" + channel +
                '}';
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
}
