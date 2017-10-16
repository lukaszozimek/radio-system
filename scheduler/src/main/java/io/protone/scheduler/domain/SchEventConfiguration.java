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
import java.util.Set;

/**
 * A Block.
 */
@Entity
@Table(name = "sch_event_configuration", uniqueConstraints = @UniqueConstraint(columnNames = {"channel_id", "short_name", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchEventConfiguration extends SchEventBase implements Serializable {

    private static final long serialVersionUID = 1L;


    @OneToMany(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sch_event_configuration_sch_event_configuration",
            joinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"))
    @PodamExclude
    private Set<SchEventConfiguration> schEventConfigurations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sch_event_configuration_sch_event",
            joinColumns = @JoinColumn(name = "sch_event_configuration_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sch_event_id", referencedColumnName = "id"))
    @PodamExclude
    private Set<SchEvent> schEvents = new HashSet<>();


    @PodamExclude
    @OneToMany(mappedBy = "schEventConfiguration")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEmissionConfiguration> emissions = new HashSet<>();

    public Set<SchEventConfiguration> getSchEventConfigurations() {
        return schEventConfigurations;
    }

    public void setSchEventConfigurations(Set<SchEventConfiguration> schEventConfigurations) {
        this.schEventConfigurations = schEventConfigurations;
    }

    public SchEventConfiguration addEventConfiguration(SchEventConfiguration schEventConfiguration) {
        this.schEventConfigurations.add(schEventConfiguration);
        return this;
    }

    public Set<SchEvent> getSchEvents() {
        return schEvents;
    }

    public SchEventConfiguration addEvent(SchEvent schEvent) {
        this.schEvents.add(schEvent);
        return this;
    }

    public void setSchEvents(Set<SchEvent> schEvents) {
        this.schEvents = schEvents;
    }

    public Set<SchEmissionConfiguration> getEmissions() {
        return emissions;
    }

    public SchEventConfiguration emissions(Set<SchEmissionConfiguration> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchEventConfiguration name(String name) {
        super.setName(name);
        return this;
    }

    public SchEventConfiguration network(CorNetwork network) {
        super.setNetwork(network);
        return this;
    }

    public SchEventConfiguration shortName(String shortName) {
        super.setShortName(shortName);
        return this;
    }


    public SchEventConfiguration channel(CorChannel channel) {
        super.setChannel(channel);
        return this;
    }

    public SchEventConfiguration eventCategory(CorDictionary eventCategory) {
        super.setEventCategory(eventCategory);
        return this;
    }

    public SchEventConfiguration addEventEmissions(SchEmissionConfiguration emissions) {
        this.emissions.add(emissions);
        return this;
    }

    public void setEmissions(Set<SchEmissionConfiguration> emissions) {
        this.emissions = emissions;
    }
}
