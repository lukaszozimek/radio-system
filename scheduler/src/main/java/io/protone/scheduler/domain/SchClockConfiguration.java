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
public class SchClockConfiguration extends SchClockBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @PodamExclude
    @OneToMany(mappedBy = "clock")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEmissionConfiguration> emissions = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sch_clock_configuration_sch_event",
            joinColumns = @JoinColumn(name = "sch_clock_configuration_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sch_event_id", referencedColumnName = "id"))
    @PodamExclude
    private Set<SchEvent> schEvents = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sch_clock_configuration_sch_event_configuration",
            joinColumns = @JoinColumn(name = "sch_clock_configuration_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sch_event_configuration_id", referencedColumnName = "id"))
    @PodamExclude
    private Set<SchEventConfiguration> schEventConfigurations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public SchClockConfiguration shortName(String shortName) {
        super.shortName(shortName);
        return this;
    }


    public SchClockConfiguration name(String name) {
        super.name(name);
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


    public SchClockConfiguration network(CorNetwork network) {
        super.network(network);
        return this;
    }

    public SchClockConfiguration channel(CorChannel channel) {
        super.channel(channel);
        return this;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
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
                ", emissions=" + emissions +
                '}';
    }


    public SchClockConfiguration length(Long length) {
        this.length = length;
        return this;
    }

    public SchClockConfiguration sequence(Long sequence) {
        super.setSequence(sequence);
        return this;
    }

    public SchClockConfiguration clockCategory(CorDictionary corDictionary) {
        super.setClockCategory(corDictionary);
        return this;
    }

    public Set<SchEventConfiguration> getSchEventConfigurations() {
        return schEventConfigurations;
    }

    public void setSchEventConfigurations(Set<SchEventConfiguration> schEventConfigurations) {
        this.schEventConfigurations = schEventConfigurations;
    }

    public Set<SchEvent> getSchEvents() {
        return schEvents;
    }

    public void setSchEvents(Set<SchEvent> schEvents) {
        this.schEvents = schEvents;
    }

    public SchClockConfiguration schEvents(Set<SchEvent> schEvents) {
        this.schEvents = schEvents;
        return this;
    }

}
