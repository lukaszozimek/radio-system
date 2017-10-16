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
import java.util.Set;

/**
 * A Block.
 */
@Entity
@Table(name = "sch_event", uniqueConstraints = @UniqueConstraint(columnNames = {"channel_id", "short_name", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchEvent extends SchEventBase implements Serializable {

    private static final long serialVersionUID = 1L;


    @OneToMany(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sch_event_sch_event_configuration",
            joinColumns = @JoinColumn(name = "sch_event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sch_event_configuration_id", referencedColumnName = "id"))
    @PodamExclude
    private Set<SchEventConfiguration> schEventConfigurations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sch_event_sch_event",
            joinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"))
    @PodamExclude
    private Set<SchEvent> schEvents = new HashSet<>();

    @PodamExclude
    @OneToMany(mappedBy = "schEvent", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEventEmission> emissions = new HashSet<>();


    public Set<SchEventConfiguration> getSchEventConfigurations() {
        return schEventConfigurations;
    }

    public SchEvent schEventConfigurations(Set<SchEventConfiguration> schEventConfigurations) {
        this.schEventConfigurations = schEventConfigurations;
        return this;
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

    public SchEvent addSchEvents(SchEvent schEvent) {
        this.schEvents.add(schEvent);
        return this;
    }

    public SchEvent schEvents(Set<SchEvent> schEvents) {
        this.schEvents = schEvents;
        return this;
    }

    public Set<SchEventEmission> getEmissions() {
        return emissions;
    }

    public SchEvent emissions(Set<SchEventEmission> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchEvent channel(CorChannel channel) {
        super.setChannel(channel);
        return this;
    }

    public SchEvent length(Long length) {
        this.length = length;
        return this;
    }

    public SchEvent network(CorNetwork network) {
        super.setNetwork(network);
        return this;
    }

    public SchEvent eventType(EventTypeEnum eventType) {
        super.setEventType(eventType);
        return this;
    }

    public SchEvent sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }

    public SchEvent schLogConfiguration(SchLogConfiguration schLogConfiguration) {
        super.setSchLogConfiguration(schLogConfiguration);
        return this;
    }

    public SchEvent addEventEmissions(SchEventEmission emissions) {
        emissions.setSchEvent(this);
        this.emissions.add(emissions);
        return this;
    }

    public void setEmissions(Set<SchEventEmission> emissions) {
        this.emissions = emissions;
    }


}
