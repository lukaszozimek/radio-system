package io.protone.scheduler.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static io.protone.scheduler.domain.SchDiscriminators.CLOCK_TEMPLATE;

/**
 * A Clock.
 */
@Entity
@DiscriminatorValue(CLOCK_TEMPLATE)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SchClockTemplate extends SchEventTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER)
    @PodamExclude
    private CorDictionary clockCategory;

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
        return super.getEmissions();
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
        this.clockCategory = corDictionary;
        return this;
    }


    public CorDictionary getClockCategory() {
        return clockCategory;
    }

    public void setClockCategory(CorDictionary clockCategory) {
        this.clockCategory = clockCategory;
    }
}
