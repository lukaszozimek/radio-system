package io.protone.scheduler.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Clock.
 */
@Entity
@Table(name = "sch_clock_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchClockTemplate extends SchClockBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @PodamExclude
    @OneToMany(cascade = CascadeType.ALL)
    @ElementCollection
    @JoinTable(
            name = "sch_clock_sch_emission",
            joinColumns = {@JoinColumn(name = "emission_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "clock_id", referencedColumnName = "id")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<SchEmissionTemplate> emissions = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "sch_clock_sch_event",
            joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "clock_id", referencedColumnName = "id")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    @ElementCollection
    @PodamExclude
    private List<SchEventTemplate> schClockTemplateEventTemplates = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public SchClockTemplate shortName(String shortName) {
        super.shortName(shortName);
        return this;
    }


    public SchClockTemplate name(String name) {
        super.name(name);
        return this;
    }

    public List<SchEmissionTemplate> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<SchEmissionTemplate> emissions) {
        this.emissions = emissions;
    }

    public SchClockTemplate emissions(List<SchEmissionTemplate> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchClockTemplate addEmission(SchEmissionTemplate emission) {
        this.emissions.add(emission);
        return this;
    }

    public SchClockTemplate removeEmission(SchEmissionTemplate emission) {
        this.emissions.remove(emission);
        return this;
    }


    public SchClockTemplate network(CorNetwork network) {
        super.network(network);
        return this;
    }

    public SchClockTemplate channel(CorChannel channel) {
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
        SchClockTemplate clock = (SchClockTemplate) o;
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


    public SchClockTemplate length(Long length) {
        this.length = length;
        return this;
    }

    public SchClockTemplate sequence(Long sequence) {
        super.setSequence(sequence);
        return this;
    }

    public SchClockTemplate clockCategory(CorDictionary corDictionary) {
        super.setClockCategory(corDictionary);
        return this;
    }


    public List<SchEventTemplate> getSchClockTemplateEventTemplates() {
        return schClockTemplateEventTemplates;
    }

    public void setSchClockTemplateEventTemplates(List<SchEventTemplate> schClockTemplateEventTemplates) {
        this.schClockTemplateEventTemplates = schClockTemplateEventTemplates;
    }

    public SchClockTemplate schEvents(List<SchEventTemplate> schEventTemplates) {
        this.schClockTemplateEventTemplates = schEventTemplates;
        return this;
    }

    public SchClockTemplate addSchEvent(SchEventTemplate schEventsTemplate) {
        this.schClockTemplateEventTemplates.add(schEventsTemplate);
        return this;
    }

}
