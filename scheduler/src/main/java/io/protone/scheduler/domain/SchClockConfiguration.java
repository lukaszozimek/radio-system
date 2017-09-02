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
@Table(name = "sch_clock_configuration", uniqueConstraints = @UniqueConstraint(columnNames = {"channel_id", "short_name", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchClockConfiguration extends AbstractAuditingEntity implements Serializable {

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
    private SchGrid grid;

    @Embedded
    private SchQueueParams queueParams;

    @ManyToOne(fetch = FetchType.LAZY)
    @PodamExclude
    private CorDictionary clockCategory;

    @Embedded
    private SchConfigurationTimeParams timeParams;

    @PodamExclude
    @OneToMany(mappedBy = "clockConfiguration")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEvent> events = new HashSet<>();

    @PodamExclude
    @OneToMany(mappedBy = "clock")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEmissionConfiguration> emissions = new HashSet<>();

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

    public SchClockConfiguration shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchClockConfiguration name(String name) {
        this.name = name;
        return this;
    }

    public SchGrid getGrid() {
        return grid;
    }

    public void setGrid(SchGrid grid) {
        this.grid = grid;
    }

    public SchClockConfiguration grid(SchGrid grid) {
        this.grid = grid;
        return this;
    }

    public SchQueueParams getQueueParams() {
        return queueParams;
    }

    public void setQueueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
    }

    public SchClockConfiguration queueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
        return this;
    }

    public SchConfigurationTimeParams getTimeParams() {
        return timeParams;
    }

    public void setTimeParams(SchConfigurationTimeParams timeParams) {
        this.timeParams = timeParams;
    }

    public SchClockConfiguration timeParams(SchConfigurationTimeParams timeParams) {
        this.timeParams = timeParams;
        return this;
    }

    public Set<SchEvent> getEvents() {
        return events;
    }

    public void setEvents(Set<SchEvent> schEvents) {
        this.events = schEvents;
    }

    public SchClockConfiguration events(Set<SchEvent> schEvents) {
        this.events = schEvents;
        return this;
    }

    public SchClockConfiguration addBlock(SchEvent schEvent) {
        this.events.add(schEvent);
        return this;
    }

    public SchClockConfiguration removeBlock(SchEvent schEvent) {
        this.events.remove(schEvent);
        return this;
    }

    public Set<SchEmissionConfiguration> getEmissions() {
        return emissions;
    }

    public void setEmissions(Set<SchEmissionConfiguration> emissions) {
        this.emissions = emissions;
    }

    public SchClockConfiguration emissions(Set<SchEmissionConfiguration> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchClockConfiguration addEmission(SchEmissionConfiguration emission) {
        this.emissions.add(emission);
        return this;
    }

    public SchClockConfiguration removeEmission(SchEmissionConfiguration emission) {
        this.emissions.remove(emission);
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchClockConfiguration network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchClockConfiguration channel(CorChannel channel) {
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
        SchClockConfiguration clock = (SchClockConfiguration) o;
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
                ", grid=" + grid +
                ", queueParams=" + queueParams +
                ", clockCategory=" + clockCategory +
                ", timeParams=" + timeParams +
                ", events=" + events +
                ", emissions=" + emissions +
                ", network=" + network +
                ", channel=" + channel +
                '}';
    }
}
