package io.protone.scheduler.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A Block.
 */
@MappedSuperclass
public class SchEventBase extends SchTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "name")
    private String name;

    @Column(name = "short_name", unique = true, nullable = false)
    private String shortName;

    @ManyToOne
    @PodamExclude
    private CorDictionary eventCategory;

    @Transient
    @PodamExclude
    private List<SchEmission> emissionsLog = new ArrayList<>();

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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public EventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public SchEventBase eventType(EventTypeEnum eventType) {
        this.eventType = eventType;
        return this;
    }

    public SchEventBase length(Long length) {
        this.length = length;
        return this;
    }


    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }


    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }


    public CorDictionary getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(CorDictionary eventCategory) {
        this.eventCategory = eventCategory;
    }


    public SchLogConfiguration getSchLogConfiguration() {
        return schLogConfiguration;
    }

    public void setSchLogConfiguration(SchLogConfiguration schLogConfiguration) {
        this.schLogConfiguration = schLogConfiguration;
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

    public SchEventBase sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }

    public SchEventBase schLogConfiguration(SchLogConfiguration schLogConfiguration) {
        this.schLogConfiguration = schLogConfiguration;
        return this;
    }

    public SchEventBase startTime(LocalDateTime startTime) {
        super.setStartTime(startTime);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchEventBase schEvent = (SchEventBase) o;

        if (getId() != null ? !getId().equals(schEvent.getId()) : schEvent.getId() != null) return false;
        if (getName() != null ? !getName().equals(schEvent.getName()) : schEvent.getName() != null) return false;
        if (getShortName() != null ? !getShortName().equals(schEvent.getShortName()) : schEvent.getShortName() != null)
            return false;
        if (getEventCategory() != null ? !getEventCategory().equals(schEvent.getEventCategory()) : schEvent.getEventCategory() != null)
            return false;
        if (getSequence() != null ? !getSequence().equals(schEvent.getSequence()) : schEvent.getSequence() != null)
            return false;
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
        result = 31 * result + (getEventType() != null ? getEventType().hashCode() : 0);
        result = 31 * result + (getSchLogConfiguration() != null ? getSchLogConfiguration().hashCode() : 0);
        result = 31 * result + (getNetwork() != null ? getNetwork().hashCode() : 0);
        result = 31 * result + (getChannel() != null ? getChannel().hashCode() : 0);
        return result;
    }

    public List<SchEmission> getEmissionsLog() {
        return emissionsLog;
    }

    public void setEmissionsLog(List<SchEmission> emissionsLog) {
        this.emissionsLog = emissionsLog;
    }

    public SchEventBase addEmission(SchEmission emission) {
        this.emissionsLog.add(emission);
        return this;
    }

    public SchEventBase removeEmission(SchEmission emission) {
        this.emissionsLog.remove(emission);
        return this;
    }

}
