package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.AbstractAuditingEntity;
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
import java.util.Objects;
import java.util.Set;

/**
 * A Block.
 */
@Entity
@Table(name = "sch_event", uniqueConstraints = @UniqueConstraint(columnNames = {"channel_id", "short_name", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchEvent extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name", unique = true, nullable = false)
    private String shortName;

    @ManyToOne
    @PodamExclude
    private CorDictionary eventCategory;

    @PodamExclude
    @ManyToOne
    private SchClockConfiguration clockConfiguration;

    @PodamExclude
    @OneToMany(mappedBy = "schEvent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEventEmission> emissions = new HashSet<>();

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

    @Embedded
    private SchQueueParams queueParams;

    @Embedded
    private SchConfigurationTimeParams timeParams;

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

    public SchQueueParams getQueueParams() {
        return queueParams;
    }

    public void setQueueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
    }

    public SchEvent queueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
        return this;
    }

    public SchConfigurationTimeParams getTimeParams() {
        return timeParams;
    }

    public void setTimeParams(SchConfigurationTimeParams timeParams) {
        this.timeParams = timeParams;
    }

    public SchEvent timeParams(SchConfigurationTimeParams timeParams) {
        this.timeParams = timeParams;
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

    public SchEvent network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public SchEvent channel(CorChannel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public CorDictionary getEventCategory() {
        return eventCategory;
    }

    public SchEvent eventCategory(CorDictionary eventCategory) {
        this.eventCategory = eventCategory;
        return this;
    }

    public void setEventCategory(CorDictionary eventCategory) {
        this.eventCategory = eventCategory;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchEvent block = (SchEvent) o;
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
        return "Event{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", eventType='" + getEventType() + "'" +
                "}";
    }


}
