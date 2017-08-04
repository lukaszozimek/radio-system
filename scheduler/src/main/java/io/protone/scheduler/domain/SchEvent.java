package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Block.
 */
@Entity
@Table(name = "sch_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "block")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEmission> emissions = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventTypeEnum eventType;

    @OneToOne
    @JoinColumn(unique = true)
    private SchQueueParams queueParams;

    @OneToOne
    @JoinColumn(unique = true)
    private SchTimeParams timeParams;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<SchEmission> getEmissions() {
        return emissions;
    }

    public void setEmissions(Set<SchEmission> emissions) {
        this.emissions = emissions;
    }

    public SchEvent emissions(Set<SchEmission> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchEvent addEmission(SchEmission emission) {
        this.emissions.add(emission);
        return this;
    }

    public SchEvent removeEmission(SchEmission emission) {
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

    public SchTimeParams getTimeParams() {
        return timeParams;
    }

    public void setTimeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
    }

    public SchEvent timeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
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
