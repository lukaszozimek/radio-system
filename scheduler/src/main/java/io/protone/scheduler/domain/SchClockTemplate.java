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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Clock.
 */
@Entity
@Table(name = "sch_clock_template", uniqueConstraints = @UniqueConstraint(columnNames = {"channel_id", "short_name", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchClockTemplate extends SchClockBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @PodamExclude
    @OneToMany(mappedBy = "clock")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<SchEmissionTemplate> emissions = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sch_clock_template_sch_event_template",
            joinColumns = @JoinColumn(name = "sch_clock_template_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sch_event_template_id", referencedColumnName = "id"))
    @PodamExclude
    private List<SchEventTemplate> schEventTemplates = new ArrayList<>();

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


    public List<SchEventTemplate> getSchEventTemplates() {
        return schEventTemplates;
    }

    public void setSchEventTemplates(List<SchEventTemplate> schEventTemplates) {
        this.schEventTemplates = schEventTemplates;
    }

    public SchClockTemplate schEvents(List<SchEventTemplate> schEventTemplates) {
        this.schEventTemplates = schEventTemplates;
        return this;
    }

    public SchClockTemplate addSchEvent(SchEventTemplate schEventsTemplate) {
        this.schEventTemplates.add(schEventsTemplate);
        return this;
    }

}
