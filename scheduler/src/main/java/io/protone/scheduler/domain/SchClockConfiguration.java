package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class SchClockConfiguration extends SchConfigurationTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name", unique = true, nullable = false)
    private String shortName;

    @ManyToOne(fetch = FetchType.LAZY)
    @PodamExclude
    private CorDictionary clockCategory;

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

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
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
                ", clockCategory=" + clockCategory +
                ", events=" + events +
                ", emissions=" + emissions +
                ", network=" + network +
                ", channel=" + channel +
                '}';
    }


    public SchClockConfiguration length(Long length) {
        this.length = length;
        return this;
    }

}
