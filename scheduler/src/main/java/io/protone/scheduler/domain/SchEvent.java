package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
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
public class SchEvent extends SchConfigurationTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "name")
    private String name;

    @Column(name = "short_name", unique = true, nullable = false)
    private String shortName;

    @ManyToOne
    @PodamExclude
    private CorDictionary eventCategory;

    @Column(name = "sequence")
    private Long sequence;

    @PodamExclude
    @ManyToOne
    private SchClockConfiguration clockConfiguration;

    @PodamExclude
    @OneToMany(mappedBy = "schEvent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEventEmission> emissions = new HashSet<>();

    @Transient
    private Set<SchEmission> emissionsLog = new HashSet<>();

    @PodamExclude
    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEvent> events = new HashSet<>();

    @PodamExclude
    @ManyToOne
    private SchEvent event;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventTypeEnum eventType;

    @PodamExclude
    @ManyToOne
    private SchLogConfiguration schLogConfiguration;


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

    public SchEvent shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchEvent name(String name) {
        this.name = name;
        return this;
    }

    public Set<SchEventEmission> getEmissions() {
        return emissions;
    }

    public void setEmissions(Set<SchEventEmission> emissions) {
        this.emissions = emissions;
    }

    public SchEvent emissions(Set<SchEventEmission> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchEvent addEmission(SchEventEmission emission) {
        this.emissions.add(emission);
        return this;
    }

    public SchEvent removeEmission(SchEmissionConfiguration emission) {
        this.emissions.remove(emission);
        return this;
    }

    public EventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public SchEvent eventType(EventTypeEnum eventType) {
        this.eventType = eventType;
        return this;
    }

    public Set<SchEvent> getBlocks() {
        return events;
    }

    public void setBlocks(Set<SchEvent> blocks) {
        this.events = blocks;
    }

    public SchEvent blocks(Set<SchEvent> blocks) {
        this.events = blocks;
        return this;
    }

    public SchEvent addBlock(SchEvent block) {
        this.events.add(block);
        return this;
    }

    public SchEvent removeBlock(SchEvent block) {
        this.events.remove(block);
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchEvent network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchEvent channel(CorChannel channel) {
        this.channel = channel;
        return this;
    }

    public CorDictionary getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(CorDictionary eventCategory) {
        this.eventCategory = eventCategory;
    }

    public SchEvent eventCategory(CorDictionary eventCategory) {
        this.eventCategory = eventCategory;
        return this;
    }

    public SchEvent getEvent() {
        return event;
    }

    public void setEvent(SchEvent event) {
        this.event = event;
    }

    public SchEvent event(SchEvent event) {
        this.event = event;
        return this;
    }

    public SchLogConfiguration getSchLogConfiguration() {
        return schLogConfiguration;
    }

    public void setSchLogConfiguration(SchLogConfiguration schLogConfiguration) {
        this.schLogConfiguration = schLogConfiguration;
    }


    public SchClockConfiguration getClockConfiguration() {
        return clockConfiguration;
    }

    public void setClockConfiguration(SchClockConfiguration clockConfiguration) {
        this.clockConfiguration = clockConfiguration;
    }

    public SchEvent clockConfiguration(SchClockConfiguration clockConfiguration) {
        this.clockConfiguration = clockConfiguration;
        return this;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", eventType='" + getEventType() + "'" +
                "}";
    }


    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public SchEvent sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }

    public SchEvent schLogConfiguration(SchLogConfiguration schLogConfiguration) {
        this.schLogConfiguration = schLogConfiguration;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchEvent schEvent = (SchEvent) o;

        if (getId() != null ? !getId().equals(schEvent.getId()) : schEvent.getId() != null) return false;
        if (getName() != null ? !getName().equals(schEvent.getName()) : schEvent.getName() != null) return false;
        if (getShortName() != null ? !getShortName().equals(schEvent.getShortName()) : schEvent.getShortName() != null)
            return false;
        if (getEventCategory() != null ? !getEventCategory().equals(schEvent.getEventCategory()) : schEvent.getEventCategory() != null)
            return false;
        if (getSequence() != null ? !getSequence().equals(schEvent.getSequence()) : schEvent.getSequence() != null)
            return false;
        if (getClockConfiguration() != null ? !getClockConfiguration().equals(schEvent.getClockConfiguration()) : schEvent.getClockConfiguration() != null)
            return false;
        if (getEvent() != null ? !getEvent().equals(schEvent.getEvent()) : schEvent.getEvent() != null) return false;
        if (getEventType() != schEvent.getEventType()) return false;
        if (getSchLogConfiguration() != null ? !getSchLogConfiguration().equals(schEvent.getSchLogConfiguration()) : schEvent.getSchLogConfiguration() != null)
            return false;
        if (getNetwork() != null ? !getNetwork().equals(schEvent.getNetwork()) : schEvent.getNetwork() != null)
            return false;
        return getChannel() != null ? getChannel().equals(schEvent.getChannel()) : schEvent.getChannel() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getShortName() != null ? getShortName().hashCode() : 0);
        result = 31 * result + (getEventCategory() != null ? getEventCategory().hashCode() : 0);
        result = 31 * result + (getSequence() != null ? getSequence().hashCode() : 0);
        result = 31 * result + (getClockConfiguration() != null ? getClockConfiguration().hashCode() : 0);
        result = 31 * result + (getEventType() != null ? getEventType().hashCode() : 0);
        result = 31 * result + (getSchLogConfiguration() != null ? getSchLogConfiguration().hashCode() : 0);
        result = 31 * result + (getNetwork() != null ? getNetwork().hashCode() : 0);
        result = 31 * result + (getChannel() != null ? getChannel().hashCode() : 0);
        return result;
    }

    public Set<SchEmission> getEmissionsLog() {
        return emissionsLog;
    }

    public void setEmissionsLog(Set<SchEmission> emissionsLog) {
        this.emissionsLog = emissionsLog;
    }

    public SchEvent addEmission(SchEmission emission) {
        this.emissionsLog.add(emission);
        return this;
    }

    public SchEvent removeEmission(SchEmission emission) {
        this.emissionsLog.remove(emission);
        return this;
    }
}
